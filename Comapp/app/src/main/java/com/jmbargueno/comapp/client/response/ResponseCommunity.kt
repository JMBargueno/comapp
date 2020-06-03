package com.jmbargueno.comapp.client.response

import com.jmbargueno.comapp.model.AppUser
import com.jmbargueno.comapp.model.Order
import java.util.*

data class ResponseCommunity(
    val creationDate: String,
    val creator: AppUser,
    val id: UUID,
    val lastModified: String,
    val orders: List<Order>,
    val title: String
)