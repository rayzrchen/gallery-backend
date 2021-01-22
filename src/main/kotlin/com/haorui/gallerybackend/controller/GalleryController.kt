package com.haorui.gallerybackend.controller

import com.haorui.gallerybackend.model.Gallery
import com.haorui.gallerybackend.service.GalleryService
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("gallery")
class GalleryController(
    private val galleryService: GalleryService
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
    fun create(@RequestBody gallery: Gallery): Gallery {
        return galleryService.create(gallery)
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
