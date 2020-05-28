package com.jmbargueno.comapp.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.jmbargueno.comapp.model.AppUser
import com.jmbargueno.comapp.model.Community
import com.jmbargueno.comapp.model.OrderEntity
import java.time.LocalDateTime
import java.util.*

data class OrderDTO(
        val id: UUID? = null,
        var title: String,
        var comment: String,
        var finished: Boolean,
        var community: Community,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
        var creationDate: LocalDateTime,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
        var lastModified: LocalDateTime,
        var madeBy: AppUserDTO? = null,
        var creator: AppUserDTO? = null
)

fun OrderEntity.toOrderDTO() = OrderDTO(
        id,
        title = this.title,
        comment = comment,
        finished = this.finished!!,
        community = this.community!!,
        creationDate = this.creationDate!!,
        lastModified = this.lastModified!!,
        madeBy = madeBy?.toAppUserDTO(),
        creator = creator.toAppUserDTO()
);


data class NewOrderDTO(
        var title: String,
        var comment: String
)

fun NewOrderDTO.toOrderEntity(creator: AppUser, community: Community) = OrderEntity(
        title,
        comment,
        creator = creator,
        community = community
)



