package com.jmbargueno.comapp.model

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity
@EntityListeners(AuditingEntityListener::class)
data class Community(
        var title: String,
        @JsonBackReference
        @OneToMany(fetch = FetchType.EAGER)
        var orders: MutableList<OrderEntity> = mutableListOf(),
        @CreatedDate
        var creationDate: LocalDateTime? = null,
        @LastModifiedDate
        var lastModified: LocalDateTime? = null,
        @JsonManagedReference @ManyToOne
        var creator: AppUser,
        @Id @GeneratedValue
        val id: UUID? = null
){
        override fun toString(): String {
                return "$id,$title"
        }
}