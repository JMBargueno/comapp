package com.jmbargueno.comapp.controller

import com.jmbargueno.comapp.dto.*
import com.jmbargueno.comapp.model.AppUser
import com.jmbargueno.comapp.model.Community
import com.jmbargueno.comapp.model.OrderEntity
import com.jmbargueno.comapp.service.CommunityService
import com.jmbargueno.comapp.service.OrderEntityService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.*

@RestController
@RequestMapping("/order")
class OrderEntityController(private val orderEntityService: OrderEntityService, private val communityService: CommunityService) {

    @PostMapping("/{id}")
    fun newOrder(@PathVariable id: UUID, @RequestBody orderDTO: NewOrderDTO, @AuthenticationPrincipal user: AppUser): ResponseEntity<*> {
        val community: Optional<Community> = communityService.findById(id)
        if (community.isEmpty) throw ResponseStatusException(HttpStatus.BAD_REQUEST, "No se ha encontrado esa comunidad")
        val result = orderEntityService.save(orderDTO.toOrderEntity(user, community.get()))
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
    fun getOrdersByCreator(@AuthenticationPrincipal user: AppUser): List<OrderDTO> {
        val result: List<OrderEntity> = orderEntityService.findAllByCreator(user)
        if (result.isNotEmpty()) return result.map { it.toOrderDTO() }
        else throw ResponseStatusException(HttpStatus.NO_CONTENT, "Sin ordenes")
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

    @DeleteMapping("/{id}")
    fun deleteOrder(@PathVariable id: UUID): ResponseEntity<Void> {
        var result = orderEntityService.findById(id)
        if (result.isPresent) {
            orderEntityService.deleteById(id)
            return ResponseEntity.noContent().build()
        } else throw ResponseStatusException(HttpStatus.NOT_FOUND, "No se ha encontrado la orden con id: $id")
    }
}