package com.jmbargueno.comapp.repository

import com.jmbargueno.comapp.model.AppUser
import com.jmbargueno.comapp.model.Community
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.web.bind.annotation.PathVariable
import java.util.*

@Repository
interface CommunityRepository : JpaRepository<Community, UUID> {
    fun findById(id: Long): Optional<Community>
    fun findAllByCreator(creator: AppUser): List<Community>


}