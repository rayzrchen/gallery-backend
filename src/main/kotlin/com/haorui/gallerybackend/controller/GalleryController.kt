package com.haorui.gallerybackend.controller

import com.haorui.gallerybackend.model.Gallery
import com.haorui.gallerybackend.service.GalleryService
import com.haorui.gallerybackend.service.UserService
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("gallery")
class GalleryController(
    private val galleryService: GalleryService,
    private val userService: UserService
) {
    @GetMapping("")
    fun getAll(): List<Gallery> {
        return galleryService.getAll()
    }

    @GetMapping("{id}")
    fun getOne(@PathVariable id: String): Gallery {
        return galleryService.getOne(id)
    }

    @PostMapping
    fun create(@RequestBody gallery: Gallery, req: HttpServletRequest): Gallery {
        val user = userService.whoami(req)
        return galleryService.create(gallery, user)
    }

    @DeleteMapping("{id}")
    fun delete(@PathVariable id: String) {
        galleryService.delete(id)
    }

    @PutMapping
    fun update(@RequestBody gallery: Gallery): Gallery {
        return galleryService.update(gallery)
    }

}
