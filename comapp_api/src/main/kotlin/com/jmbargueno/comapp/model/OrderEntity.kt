package com.jmbargueno.comapp.model

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonManagedReference
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*


@EntityListeners(AuditingEntityListener::class)
@Entity
data class OrderEntity(
        var title: String,
        var comment: String,
        var finished: Boolean? = false,
        @CreatedDate
        var creationDate: LocalDateTime? = null,
        @LastModifiedDate
        var lastModified: LocalDateTime? = null,
        @JsonManagedReference @ManyToOne
        var madeBy: AppUser? = null,
        @JsonManagedReference @ManyToOne
        var creator: AppUser,
        @Id @GeneratedValue
        val id: UUID? = null

){
        override fun toString(): String {
                return "$id,$title"
        }
}