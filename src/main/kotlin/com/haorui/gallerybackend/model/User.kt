package com.haorui.gallerybackend.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.security.core.GrantedAuthority
import java.util.*
import javax.persistence.*


@Entity
data class User(
    @Id
    var id: String = UUID.randomUUID().toString(),

    @Column(unique = true, nullable = false)
    var username: String, // phone number

    var nickName: String = "",

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    var password: String,

    @ElementCollection(fetch = FetchType.EAGER)
    var roles: MutableList<Role> = mutableListOf()
)


enum class Role : GrantedAuthority {
    ROLE_ADMIN, ROLE_USER;

    override fun getAuthority(): String {
        return name
    }

    override fun toString(): String {
        return name
    }

}

interface UserRepository : JpaRepository<User, String> {
    fun existsByUsername(username: String): Boolean
    fun findByUsername(username: String): User?
}
