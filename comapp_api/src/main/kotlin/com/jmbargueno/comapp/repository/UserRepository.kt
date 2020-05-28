package com.jmbargueno.comapp.repository

import com.jmbargueno.comapp.model.AppUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<AppUser, UUID> {

    fun findByUsername(username: String): Optional<AppUser>
    fun findById(id: Long): Optional<AppUser>


}