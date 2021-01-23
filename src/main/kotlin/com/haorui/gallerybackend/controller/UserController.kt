package com.haorui.gallerybackend.controller

import com.haorui.gallerybackend.model.User
import com.haorui.gallerybackend.service.UserService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest
import org.apache.tomcat.util.http.parser.Authorization

import org.springframework.web.bind.annotation.GetMapping





@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService
) {
    @PostMapping("login")
    fun login(@RequestParam username: String, @RequestParam password: String): String {
        return userService.login(username, password)
    }

    @PostMapping("register")
    fun register(@RequestBody user: User): String {
        return userService.register(user)
    }

    @DeleteMapping("{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    fun delete(@PathVariable username: String) {
        userService.delete(username)
    }

    @GetMapping("")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    fun getAll(): List<User> {
        return userService.getAll()
    }

    @GetMapping("refresh")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    fun refresh(req: HttpServletRequest): String {
        return userService.refresh(req.remoteUser)
    }

    @GetMapping("me")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    fun whoami(req: HttpServletRequest): User {
        return userService.whoami(req)
    }

}
