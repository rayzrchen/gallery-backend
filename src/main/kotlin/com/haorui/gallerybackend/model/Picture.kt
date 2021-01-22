package com.haorui.gallerybackend.model

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Picture(
    @Id
    var id: String = UUID.randomUUID().toString(),

    var pictureType: String,
    var numberOfView: Long,
    var galleryId: String,

    var filename: String,
    var fileSize: String,
    var dimension: String,
    var exif: String,
    var category: String,
    var photographer: String,
    var memo: String,

    var pictureBinary: ByteArray,

    var uploadTime: Long,

    ) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Picture

        if (id != other.id) return false
        if (pictureType != other.pictureType) return false
        if (numberOfView != other.numberOfView) return false
        if (galleryId != other.galleryId) return false
        if (filename != other.filename) return false
        if (fileSize != other.fileSize) return false
        if (dimension != other.dimension) return false
        if (exif != other.exif) return false
        if (category != other.category) return false
        if (photographer != other.photographer) return false
        if (memo != other.memo) return false
        if (uploadTime != other.uploadTime) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + pictureType.hashCode()
        result = 31 * result + numberOfView.hashCode()
        result = 31 * result + galleryId.hashCode()
        result = 31 * result + filename.hashCode()
        result = 31 * result + fileSize.hashCode()
        result = 31 * result + dimension.hashCode()
        result = 31 * result + exif.hashCode()
        result = 31 * result + category.hashCode()
        result = 31 * result + photographer.hashCode()
        result = 31 * result + memo.hashCode()
        result = 31 * result + uploadTime.hashCode()
        return result
    }
}


interface PictureRepository : JpaRepository<Picture, String> {
    fun findByGalleryIdOrderByUploadTimeDesc(galleryId: String): List<Picture>
}
