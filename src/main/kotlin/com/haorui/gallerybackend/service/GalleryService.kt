package com.haorui.gallerybackend.service

import com.haorui.gallerybackend.config.AppNotFoundException
import com.haorui.gallerybackend.model.Gallery
import com.haorui.gallerybackend.model.GalleryRepository
import com.haorui.gallerybackend.model.User
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

interface GalleryService {

    fun create(gallery: Gallery, user: User): Gallery

    fun delete(id: String)

    fun update(gallery: Gallery): Gallery

    fun getAll(): List<Gallery>

    fun getOne(id: String): Gallery

}

@Service
class GalleryServiceImpl(
    private val galleryRepository: GalleryRepository
) : GalleryService {

    override fun create(gallery: Gallery, user: User): Gallery {
        gallery.createTime = System.currentTimeMillis()
        gallery.numberOfView = 0
        if (gallery.title.isBlank()) {
            throw RuntimeException("title should not be blank")
        }
        gallery.username = user.username
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
        val username = (SecurityContextHolder.getContext().authentication.principal as UserDetails).username
        return galleryRepository.findByUsernameOrderByCreateTimeDesc(username)
    }

    override fun getOne(id: String): Gallery {
        return galleryRepository.findByIdOrNull(id) ?: throw AppNotFoundException("")
    }

}


