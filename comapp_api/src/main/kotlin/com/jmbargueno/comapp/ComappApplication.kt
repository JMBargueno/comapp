package com.jmbargueno.comapp

import com.jmbargueno.comapp.dto.*
import com.jmbargueno.comapp.model.AppUser
import com.jmbargueno.comapp.model.Community
import com.jmbargueno.comapp.model.OrderEntity
import com.jmbargueno.comapp.repository.CommunityRepository
import com.jmbargueno.comapp.repository.OrderEntityRepository
import com.jmbargueno.comapp.repository.UserRepository
import com.jmbargueno.comapp.service.UserService
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@SpringBootApplication
class ComappApplication

fun main(args: Array<String>) {
    runApplication<ComappApplication>(*args)

}

@Component
class InitDataComponent(val userRepository: UserRepository,
                        val communityRepository: CommunityRepository,
                        val orderEntityRepository: OrderEntityRepository,
                        val passwordEncoder: PasswordEncoder = BCryptPasswordEncoder()) {
    @PostConstruct
    fun initData() {
        var admin: AppUser = UserSignUp("admin", passwordEncoder.encode("1234")).toAppUser()
        userRepository.save(admin)
        var community: Community = NewCommunityDTO("DoctoFedriani").toCommunity(admin)
        communityRepository.save(community)

        var user: AppUser = UserSignUp("user", passwordEncoder.encode("1234")).toAppUser()
        user.memberOf = community
        userRepository.save(user)
        communityRepository.save(community)

        var order: OrderEntity = NewOrderDTO("Necesito patatas", "Necesito dos kilos de patatas").toOrderEntity(user)
        orderEntityRepository.save(order)
    }
}