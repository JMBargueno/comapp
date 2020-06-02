package com.jmbargueno.comapp.client.response

import com.jmbargueno.comapp.model.AppUser

data class ResponseLogin(
    val token: String,
    val user: AppUser
)