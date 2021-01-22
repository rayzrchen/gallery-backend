package com.haorui.gallerybackend.service

import com.haorui.gallerybackend.config.AppNotFoundException
import com.haorui.gallerybackend.model.Picture
import com.haorui.gallerybackend.model.PictureRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

interface PictureService  {

    fun upload(galleryId: String, file: MultipartFile): Picture

    fun delete(id: String)

    fun update(picture: Picture): Picture

    fun getAll(galleryId: String): List<Picture>

    fun getOne(id: String): Picture

}

@Service
class PictureServiceImpl(
    private val pictureRepository: PictureRepository
): PictureService {

    override fun upload(galleryId: String, file: MultipartFile): Picture {
        return pictureRepository.save(
            Picture(
                pictureBinary = file.bytes,
                filename = file.originalFilename ?: file.name,
                fileSize = file.size,
                galleryId = galleryId,
                memo = "",
                numberOfView = 0,
//                pictureType = file.contentType ?: "",
                uploadTime = System.currentTimeMillis()
            )
        )
    }


    override fun delete(id: String) {
        pictureRepository.findByIdOrNull(id) ?: throw AppNotFoundException("")
        pictureRepository.deleteById(id)
    }

    override fun update(picture: Picture): Picture {
        val picture1 = pictureRepository.findByIdOrNull(picture.id) ?: throw AppNotFoundException("")
        picture1.memo = picture.memo
        return pictureRepository.save(picture1)
    }

    override fun getAll(galleryId: String): List<Picture> {
        return pictureRepository.findByGalleryIdOrderByUploadTimeDesc(galleryId)
    }

    override fun getOne(id: String): Picture {
        return pictureRepository.findByIdOrNull(id)?: throw AppNotFoundException("")
    }

}
