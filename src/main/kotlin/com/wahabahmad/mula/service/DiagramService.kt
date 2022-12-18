package com.wahabahmad.mula.service

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.ObjectMetadata
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.net.URL
import java.util.*


@Service
class DiagramService(
    private val s3Client: AmazonS3,
) {
    companion object {
        const val BUCKET_NAME = "mula-diagram-store"
        const val EXPIRATION_OFFSET_MILLISECONDS = 1000 * 60 * 60 // ONE HOUR
    }

    fun get(objectId: String): URL =
        s3Client.generatePresignedUrl(
            BUCKET_NAME,
            objectId,
            Date(Date().time + EXPIRATION_OFFSET_MILLISECONDS)
        )

    fun save(image: MultipartFile): String =
        with(UUID.randomUUID().toString()){
            s3Client.putObject(BUCKET_NAME, this, image.inputStream, ObjectMetadata())
            this
        }

    fun delete(id: String) =
        s3Client.deleteObject(BUCKET_NAME, id)
}