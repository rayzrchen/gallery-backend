package com.haorui.gallerybackend.controller

import com.haorui.gallerybackend.model.Picture
import com.haorui.gallerybackend.service.PictureService
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("picture")
class PictureController(
    private val pictureService: PictureService
) {
    @GetMapping("{galleryId}")
    fun getAll(@PathVariable galleryId: String): List<Picture> {
        return pictureService.getAll(galleryId)
    }

    @GetMapping("{galleryId}/{id}")
    fun getOne(@PathVariable galleryId: String, @PathVariable id: String): Picture {
        return pictureService.getOne(id)
    }

    @PostMapping("{galleryId}")
    fun create(@PathVariable galleryId: String, @RequestParam("file") file: MultipartFile): Picture {
        return pictureService.upload(galleryId, file)
    }

    @DeleteMapping("{id}")
    fun delete(@PathVariable id: String) {
        pictureService.delete(id)
    }

    @PutMapping
    fun update(@RequestBody gallery: Picture): Picture {
        return pictureService.update(gallery)
    }

}
