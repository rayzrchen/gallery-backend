package com.haorui.gallerybackend.model

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Gallery(
    @Id
    var id: String = UUID.randomUUID().toString(),

    var createUserId: String,
    var createTime: Long,

    var title: String,
    var numberOfView: Long,
    var previewPic: String
)

interface GalleryRepository: JpaRepository<Gallery, String> {
    fun findByOrderByCreateTimeDesc(): List<Gallery>
}
