package com.jmbargueno.comapp.response

import com.jmbargueno.comapp.dto.AppUserDTO

data class JwtUserResponse(
        val token: String,
        val user: AppUserDTO
)