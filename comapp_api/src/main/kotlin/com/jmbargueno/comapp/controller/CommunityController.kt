package com.jmbargueno.comapp.controller

import com.jmbargueno.comapp.dto.CommunityDTO
import com.jmbargueno.comapp.dto.NewCommunityDTO
import com.jmbargueno.comapp.dto.toCommunity
import com.jmbargueno.comapp.dto.toCommunityDTO
import com.jmbargueno.comapp.model.AppUser
import com.jmbargueno.comapp.model.Community
import com.jmbargueno.comapp.service.CommunityService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.*
import kotlin.collections.ArrayList

@RestController
@RequestMapping("/community")
class CommunityController(val communityService: CommunityService) {

    @PostMapping("/")
    fun newCommunity(@RequestBody communityDTO: NewCommunityDTO, @AuthenticationPrincipal user: AppUser): ResponseEntity<*> {
        val result = communityService.save(communityDTO.toCommunity(user))
        if (result != null) return ResponseEntity<CommunityDTO>(result.toCommunityDTO(), HttpStatus.CREATED)
        else throw ResponseStatusException(
                HttpStatus.BAD_REQUEST, "${communityDTO.title} no se ha podido crear con éxito")
    }

    @GetMapping("/{id}")
    fun getCommunity(@PathVariable id: UUID): CommunityDTO {
        var result = communityService.findById(id)
        if (result.isPresent) return result.get().toCommunityDTO()
        else throw ResponseStatusException(HttpStatus.NOT_FOUND, "No se ha encontrado la comunidad con id: $id")
    }

    @GetMapping("/")
    fun findAll(): List<CommunityDTO> {
        var communities = communityService.findAll()
        if (communities != null) {
            var result = ArrayList<CommunityDTO>()
            for (community in communities) {
                result.add(community.toCommunityDTO())
            }
            return result
        } else throw ResponseStatusException(HttpStatus.NOT_FOUND, "No se han encontrado comunidades")
    }


    @DeleteMapping("/{id}")
    fun deleteCommunity(@PathVariable id: UUID): ResponseEntity<Void> {
        var result = communityService.findById(id)
        if (result.isPresent) {
            communityService.deleteById(id)
            return ResponseEntity.noContent().build()
        } else throw ResponseStatusException(HttpStatus.NOT_FOUND, "No se ha encontrado la comunidad con id: $id")
    }

    @GetMapping("/mycommunity")
    fun getCommunityByLogedUser(@AuthenticationPrincipal user: AppUser): CommunityDTO {
        var id : UUID? = user.memberOf?.id
        var result = id?.let { communityService.findById(it) }
        if (result != null) {
            if (result.isPresent) return result.get().toCommunityDTO()
            else throw ResponseStatusException(HttpStatus.NOT_FOUND, "No se ha encontrado la comunidad con id: $id")
        }else throw ResponseStatusException(HttpStatus.NOT_FOUND, "No se ha encontrado la comunidad con id: $id")
    }
}