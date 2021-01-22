package com.haorui.gallerybackend.service

import com.haorui.gallerybackend.model.Gallery
import com.haorui.gallerybackend.model.GalleryRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*

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
        return galleryRepository.save(gallery)
    }

    override fun delete(id: String) {
        galleryRepository.findByIdOrNull(id) ?: throw RuntimeException("Not Found")
        galleryRepository.deleteById(id)
    }

    override fun update(gallery: Gallery): Gallery {
        val gallery1 = galleryRepository.findByIdOrNull(gallery.id) ?: throw RuntimeException("Not Found")
        gallery1.previewPic = gallery.previewPic
        gallery1.title = gallery.title
        gallery1.numberOfView = gallery.numberOfView
        return galleryRepository.save(gallery1)
    }

    override fun getAll(): List<Gallery> {
       return galleryRepository.findByOrderByCreateTimeDesc()
    }

    override fun getOne(id: String): Gallery {
        return galleryRepository.findByIdOrNull(id) ?: throw RuntimeException("Not Found")
    }

}


