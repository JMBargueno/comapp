package com.jmbargueno.comapp.service

import com.jmbargueno.comapp.model.AppUser
import com.jmbargueno.comapp.model.Community
import com.jmbargueno.comapp.model.OrderEntity
import com.jmbargueno.comapp.repository.OrderEntityRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class OrderEntityService : BaseService<OrderEntity, UUID, OrderEntityRepository>() {
    fun findAllByCreator(creator: AppUser): List<OrderEntity> {
        return this.repository.findAllByCreator(creator)
    }
}