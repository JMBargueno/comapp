package com.jmbargueno.comapp.dto

import com.jmbargueno.comapp.model.AppUser
import com.jmbargueno.comapp.model.Community
import java.util.*

data class AppUserDTO(
        val id: UUID?,
        var username: String,
        var rol: String
);

fun AppUser.toAppUserDTO() = AppUserDTO(
        id, username, if (roles.size == 1) roles.first() else roles.elementAt(1)
);

data class UserSignUp(
        var username: String,
        var password: String,
        var community: String?
);

fun UserSignUp.toAppUser(community: Community?) = AppUser(
        username, password, ArrayList(), community, "USER"
);