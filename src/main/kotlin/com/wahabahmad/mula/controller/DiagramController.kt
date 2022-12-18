package com.wahabahmad.mula.controller

import com.wahabahmad.mula.response.DiagramIDResponse
import com.wahabahmad.mula.response.DiagramURLResponse
import com.wahabahmad.mula.service.DiagramService
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
    fun getDiagram(@PathVariable id: String): DiagramURLResponse =
        DiagramURLResponse(diagramService.get(id).toString())

    @PostMapping("/diagrams")
    fun saveDiagram(@RequestParam("file") file: MultipartFile): DiagramIDResponse =
        DiagramIDResponse(diagramService.save(file))

    @DeleteMapping("/diagrams/{id}")
    fun deleteDiagram(@PathVariable id: String) =
        diagramService.delete(id)
}