package com.jmbargueno.comapp.repository

import com.jmbargueno.comapp.model.AppUser
import com.jmbargueno.comapp.model.Community
import com.jmbargueno.comapp.model.OrderEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface OrderEntityRepository : JpaRepository<OrderEntity, UUID> {
    fun findById(id: Long): Optional<OrderEntity>
    fun findAllByCreator(creator: AppUser): List<OrderEntity>

}