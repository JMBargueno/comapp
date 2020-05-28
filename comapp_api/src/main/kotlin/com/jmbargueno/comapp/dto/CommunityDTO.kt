package com.jmbargueno.comapp.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.jmbargueno.comapp.model.AppUser
import com.jmbargueno.comapp.model.Community
import com.jmbargueno.comapp.model.OrderEntity
import java.time.LocalDateTime
import java.util.*

data class CommunityDTO(
        val id: UUID? = null,
        var title: String,
        var members: MutableList<AppUserDTO>? = null,
        var orders: MutableList<OrderDTO>? = null,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
        var creationDate: LocalDateTime,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
        var lastModified: LocalDateTime,
        var creator: AppUserDTO? = null
)

fun Community.toCommunityDTO() = CommunityDTO(
        id = this.id,
        title = this.title,
        members = transformMutableListMembers(this.members),
        orders = transformMutableListOrders(this.orders),
        creationDate = this.creationDate!!,
        lastModified = this.lastModified!!,
        creator = this.creator?.toAppUserDTO()
)

data class NewCommunityDTO(
        var title: String
)

fun NewCommunityDTO.toCommunity(user: AppUser) = Community(
        title,
        creator = user,
        orders = mutableListOf<OrderEntity>(),
        members = mutableListOf<AppUser>()
)

fun transformMutableListOrders(raw: MutableList<OrderEntity>?): MutableList<OrderDTO>? {
    var result: MutableList<OrderDTO>? = null
    if (raw != null) {
        for (element in raw) {
            result?.add(element.toOrderDTO())
        }
    }
    return result
}

fun transformMutableListMembers(raw: MutableList<AppUser>?): MutableList<AppUserDTO>? {
    var result: MutableList<AppUserDTO>? = null
    if (raw != null) {
        for (element in raw) {
            result?.add(element.toAppUserDTO())
        }
    }
    return result
}