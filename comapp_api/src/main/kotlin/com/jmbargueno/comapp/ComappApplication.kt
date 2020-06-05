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
        var admin: AppUser = UserSignUp("admin@admin.com", passwordEncoder.encode("1234"), null).toAppUser(null)
        userRepository.save(admin)
        var community: Community = NewCommunityDTO("DoctorFedriani33").toCommunity(admin)
        var community2: Community = NewCommunityDTO("DoctorFedriani34").toCommunity(admin)
        var community3: Community = NewCommunityDTO("DoctorFedriani35").toCommunity(admin)
        var community4: Community = NewCommunityDTO("DoctorFedriani36").toCommunity(admin)
        var community5: Community = NewCommunityDTO("DoctorFedriani37").toCommunity(admin)
        var community6: Community = NewCommunityDTO("DoctorFedriani38").toCommunity(admin)

        communityRepository.save(community)
        communityRepository.save(community2)
        communityRepository.save(community3)
        communityRepository.save(community4)
        communityRepository.save(community5)
        communityRepository.save(community6)



        admin.memberOf = community
        userRepository.save(admin)

        var user: AppUser = UserSignUp("user@user.com", passwordEncoder.encode("1234"), null).toAppUser(community)
        user.memberOf = community
        userRepository.save(user)
        communityRepository.save(community)

        var order: OrderEntity = NewOrderDTO("Necesito patatas", "Necesito dos kilos de patatas").toOrderEntity(user)
        var ordertwo: OrderEntity = NewOrderDTO("Necesito lentejas", "Necesito dos paquetes de lentejas").toOrderEntity(admin)
        orderEntityRepository.save(order)
        orderEntityRepository.save(ordertwo)
        community.orders.add(order)
        community.orders.add(ordertwo)
        communityRepository.save(community)
    }
}