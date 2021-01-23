package com.haorui.gallerybackend.model

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Lob

@Entity
data class Gallery(
    @Id
    var id: String = UUID.randomUUID().toString(),

    var username: String = "",
    var createTime: Long = 0,

    var title: String = "",
    var numberOfView: Long = 0,

    @Column(columnDefinition = "TEXT")
    var description: String = ""
)

interface GalleryRepository: JpaRepository<Gallery, String> {
    fun findByOrderByCreateTimeDesc(): List<Gallery>
}
