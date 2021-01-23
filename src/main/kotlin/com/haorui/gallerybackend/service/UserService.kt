package com.haorui.gallerybackend.service

import com.haorui.gallerybackend.model.User
import com.haorui.gallerybackend.model.UserRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletRequest


interface UserService {
    fun register(user: User): String
    fun delete(username: String)
    fun login(username: String, password: String): String
    fun whoami(req: HttpServletRequest): User
    fun refresh(username: String): String
    fun getAll(): List<User>
}

@Service
class UserServiceImpl(
    private val passwordEncoder: PasswordEncoder,
    private val userRepository: UserRepository,
    private val jwtTokenProvider: JwtTokenProvider,
    private val authenticationManager: AuthenticationManager,

) : UserService {
    override fun login(username: String, password: String): String {
        return try {
            authenticationManager.authenticate(UsernamePasswordAuthenticationToken(username, password))
            jwtTokenProvider.createToken(username, userRepository.findByUsername(username)!!.roles)
        } catch (e: Exception) {
            throw RuntimeException("Invalid username/password supplied")
        }
    }

    override fun register(user: User): String {
        return if (!userRepository.existsByUsername(user.username)) {
            user.password = passwordEncoder.encode(user.password)
            userRepository.save(user)
            jwtTokenProvider.createToken(user.username, user.roles)
        } else {
            throw RuntimeException("Username is already in use")
        }
    }

    override fun delete(username: String) {
        val user = userRepository.findByUsername(username) ?: throw RuntimeException("user not found")
        userRepository.delete(user)
    }

    override fun whoami(req: HttpServletRequest): User {
        return userRepository.findByUsername(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req))) ?:
        throw RuntimeException("Invalid user")
    }

    override fun refresh(username: String): String {
        return jwtTokenProvider.createToken(username, userRepository.findByUsername(username)!!.roles)
    }

    override fun getAll(): List<User> {
        return userRepository.findAll()
    }

}
