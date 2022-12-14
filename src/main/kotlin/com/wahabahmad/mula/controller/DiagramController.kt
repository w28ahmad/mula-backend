package com.wahabahmad.mula.controller

import com.wahabahmad.mula.service.DiagramService
import org.springframework.core.io.InputStreamResource
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
class DiagramController(
    private val diagramService : DiagramService
) {

    @GetMapping("/diagrams/{id}")
    fun getDiagram(@PathVariable id: String): ResponseEntity<InputStreamResource> =
        with(diagramService.get(id)) {
            ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(InputStreamResource(this))
        }

    @PostMapping("/diagrams")
    fun saveDiagram(@RequestParam("file") file: MultipartFile): String =
        diagramService.save(file)

    @DeleteMapping("/diagrams/{id}")
    fun deleteDiagram(@PathVariable id: String) =
        diagramService.delete(id)
}