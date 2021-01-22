package com.haorui.gallerybackend.service

import com.haorui.gallerybackend.model.Picture
import com.haorui.gallerybackend.model.PictureRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

interface PictureService  {

//    fun upload(picture: Picture, file: FileInputStream): Picture

    fun delete(id: String)

    fun update(picture: Picture): Picture

    fun getAll(galleryId: String): List<Picture>

    fun getOne(id: String): Picture

}

@Service
class PictureServiceImpl(
    private val pictureRepository: PictureRepository
): PictureService {
//    override fun upload(file: FilePart): Picture {
//
//        file.
//
//        return pictureRepository.save(
//            Picture(
//                filename = file.filename(),
//
//            )
//        )
//    }

    override fun delete(id: String) {
        pictureRepository.findByIdOrNull(id) ?: throw RuntimeException("Not Found")
        pictureRepository.deleteById(id)
    }

    override fun update(picture: Picture): Picture {
        val picture1 = pictureRepository.findByIdOrNull(picture.id) ?: throw RuntimeException("Not Found")
        picture1.category = picture.category
        picture1.memo = picture.memo
        return pictureRepository.save(picture1)
    }

    override fun getAll(galleryId: String): List<Picture> {
        return pictureRepository.findByGalleryIdOrderByUploadTimeDesc(galleryId)
    }

    override fun getOne(id: String): Picture {
        return pictureRepository.findByIdOrNull(id)?: throw RuntimeException("Not Found")
    }

}
