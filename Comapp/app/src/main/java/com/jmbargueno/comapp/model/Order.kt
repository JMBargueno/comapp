package com.jmbargueno.comapp.model

import com.jmbargueno.comapp.model.AppUser
import java.util.*

data class Order(
    val comment: String,
    val creationDate: String,
    val creator: AppUser,
    val finished: Boolean,
    val id: UUID,
    val lastModified: String,
    val madeBy: AppUser,
    val title: String
)