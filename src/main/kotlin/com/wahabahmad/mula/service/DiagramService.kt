package com.wahabahmad.mula.service

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.S3ObjectInputStream
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.*


@Service
class DiagramService(
    private val s3Client: AmazonS3,
) {
    companion object {
        const val BUCKET_NAME = "mula-diagram-store"
    }

    fun get(id: String): S3ObjectInputStream =
        s3Client.getObject(BUCKET_NAME, id).objectContent

    fun save(image: MultipartFile): String =
        with(UUID.randomUUID().toString()){
            s3Client.putObject(BUCKET_NAME, this, image.inputStream, ObjectMetadata())
            this
        }

    fun delete(id: String) =
        s3Client.deleteObject(BUCKET_NAME, id)
}