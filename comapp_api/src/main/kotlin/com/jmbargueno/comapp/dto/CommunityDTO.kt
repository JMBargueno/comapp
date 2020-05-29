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
        var orders: MutableList<OrderDTO>? = mutableListOf(),
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
        var creationDate: LocalDateTime,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
        var lastModified: LocalDateTime,
        var creator: AppUserDTO? = null
)

fun Community.toCommunityDTO() = CommunityDTO(
        id = this.id,
        title = this.title,
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
        orders = mutableListOf<OrderEntity>()
)

fun transformMutableListOrders(raw: MutableList<OrderEntity>?): MutableList<OrderDTO>? {
    var result: MutableList<OrderDTO>? = mutableListOf()
    if (raw != null) {
        for (element in raw) {
            result?.add(element.toOrderDTO())
        }
    }
    return result
}
