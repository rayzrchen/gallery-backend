package com.haorui.gallerybackend.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloController {
    @GetMapping("/")
    fun sayHi(): String {
        return "Hello world"
    }
}