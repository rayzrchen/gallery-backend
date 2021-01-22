package com.haorui.gallerybackend.service

import com.haorui.gallerybackend.config.AppNotFoundException
import com.haorui.gallerybackend.model.Gallery
import com.haorui.gallerybackend.model.GalleryRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

interface GalleryService {

    fun create(gallery: Gallery): Gallery

    fun delete(id: String)

    fun update(gallery: Gallery): Gallery

    fun getAll(): List<Gallery>

    fun getOne(id: String): Gallery

}

@Service
class GalleryServiceImpl(
    private val galleryRepository: GalleryRepository
): GalleryService {
    override fun create(gallery: Gallery): Gallery {
        gallery.createTime = System.currentTimeMillis()
        gallery.numberOfView = 0
        if (gallery.title.isBlank()) {
            throw RuntimeException("title should not be blank")
        }
        return galleryRepository.save(gallery)
    }

    override fun delete(id: String) {
        galleryRepository.findByIdOrNull(id) ?: throw AppNotFoundException(id)
        galleryRepository.deleteById(id)
    }

    override fun update(gallery: Gallery): Gallery {
        val gallery1 = galleryRepository.findByIdOrNull(gallery.id) ?: throw AppNotFoundException("")
        gallery1.title = gallery.title
        gallery1.numberOfView = gallery.numberOfView
        return galleryRepository.save(gallery1)
    }

    override fun getAll(): List<Gallery> {
       return galleryRepository.findByOrderByCreateTimeDesc()
    }

    override fun getOne(id: String): Gallery {
        return galleryRepository.findByIdOrNull(id) ?: throw AppNotFoundException("")
    }

}


