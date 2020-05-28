package com.jmbargueno.comapp.service


import com.jmbargueno.comapp.model.AppUser
import com.jmbargueno.comapp.model.Community
import com.jmbargueno.comapp.repository.CommunityRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class CommunityService() : BaseService<Community, UUID, CommunityRepository>() {
    fun findAllByCreator(creator: AppUser): List<Community>{
        return this.repository.findAllByCreator(creator)
    }
    /*fun findAllByMember(member: AppUser): List<Community>{
        return this.repository.findAllByMember(member)
    }*/
}