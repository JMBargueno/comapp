package com.jmbargueno.comapp.controller

import com.jmbargueno.comapp.dto.*
import com.jmbargueno.comapp.model.AppUser
import com.jmbargueno.comapp.model.Community
import com.jmbargueno.comapp.model.OrderEntity
import com.jmbargueno.comapp.service.CommunityService
import com.jmbargueno.comapp.service.OrderEntityService
import com.jmbargueno.comapp.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.*
import kotlin.collections.ArrayList

@RestController
@RequestMapping("/order")
class OrderEntityController(private val orderEntityService: OrderEntityService, private val communityService: CommunityService, private val userService: UserService) {

    @PostMapping("/{id}")
    fun newOrder(@PathVariable id: UUID, @RequestBody orderDTO: NewOrderDTO, @AuthenticationPrincipal user: AppUser): ResponseEntity<*> {
        val community: Optional<Community> = communityService.findById(id)
        if (community.isEmpty) throw ResponseStatusException(HttpStatus.BAD_REQUEST, "No se ha encontrado esa comunidad")
        var order = orderDTO.toOrderEntity(user)
        val result = orderEntityService.save(order)
        community.get().orders.add(order)
        communityService.save(community.get())
        if (result != null) return ResponseEntity<OrderDTO>(result.toOrderDTO(), HttpStatus.CREATED)
        else throw ResponseStatusException(
                HttpStatus.BAD_REQUEST, "${orderDTO.title} no se ha podido crear con éxito")
    }

    @GetMapping("/{id}")
    fun getOrder(@PathVariable id: UUID): OrderDTO {
        var result = orderEntityService.findById(id)
        if (result.isPresent) return result.get().toOrderDTO()
        else throw ResponseStatusException(HttpStatus.NOT_FOUND, "No se ha encontrado la orden con id: $id")
    }

    @GetMapping("/myorders")
    fun getOrdersByCreatorAndUnfinished(@AuthenticationPrincipal user: AppUser): List<OrderDTO> {
        val orders: List<OrderEntity> = orderEntityService.findAllByCreator(user)
        var result: MutableList<OrderEntity> = ArrayList()
        if (orders.isNotEmpty()) {


            for (order in orders) {
                if (order.finished == false) {
                    result.add(order)
                }
            }
            return result.map { it.toOrderDTO() }
        } else throw ResponseStatusException(HttpStatus.NO_CONTENT, "Sin ordenes")
    }

    @GetMapping("/myorders/historic")
    fun getOrdersByCreatorAndFinished(@AuthenticationPrincipal user: AppUser): List<OrderDTO> {
        val orders: List<OrderEntity> = orderEntityService.findAllByCreator(user)
        var result: MutableList<OrderEntity> = ArrayList()
        if (orders.isNotEmpty()) {


            for (order in orders) {
                if (order.finished == true) {
                    result.add(order)
                }
            }
            return result.map { it.toOrderDTO() }
        } else throw ResponseStatusException(HttpStatus.NO_CONTENT, "Sin ordenes")
    }

    @GetMapping("/")
    fun findAll(): List<OrderDTO> {
        var orders = orderEntityService.findAll()
        if (orders != null) {
            var result = ArrayList<OrderDTO>()
            for (order in orders) {
                result.add(order.toOrderDTO())
            }
            return result
        } else throw ResponseStatusException(HttpStatus.NOT_FOUND, "No se han encontrado ordenes")
    }

    @DeleteMapping("/{community}/{id}")
    fun deleteOrder(@PathVariable community: UUID, @PathVariable id: UUID): ResponseEntity<Void> {
        var communityEntity = communityService.findById(community)

        var result = orderEntityService.findById(id)
        communityEntity.get().orders.remove(result.get())
        communityService.save(communityEntity.get())
        if (result.isPresent) {
            orderEntityService.deleteById(id)
            return ResponseEntity.noContent().build()
        } else throw ResponseStatusException(HttpStatus.NOT_FOUND, "No se ha encontrado la orden con id: $id")
    }

    @PutMapping("/{id}")
    fun setFinished(@PathVariable id: UUID, @AuthenticationPrincipal user: AppUser): OrderDTO {
        var searchUser = user.id?.let { userService.findById(it) }
        var order = orderEntityService.findById(id)
        if (order.isPresent) {
            if (searchUser != null) {
                order.get().madeBy = searchUser.get()
            }
            order.get().finished = true
            orderEntityService.save(order.get())
            return order.get().toOrderDTO()
        } else throw ResponseStatusException(HttpStatus.NOT_FOUND, "No se ha encontrado la orden con id: $id")
    }
}