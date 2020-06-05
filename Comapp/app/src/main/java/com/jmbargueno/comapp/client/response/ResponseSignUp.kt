package com.jmbargueno.comapp.client.response

import com.jmbargueno.comapp.model.AppUser

data class ResponseSignUp (
    val id: String,
    val username: String,
    val rol : String
)