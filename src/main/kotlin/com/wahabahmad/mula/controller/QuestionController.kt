package com.wahabahmad.mula.controller

import com.wahabahmad.mula.service.QuestionJsonService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class QuestionController(
    private val questionJsonService: QuestionJsonService,
) {

    @GetMapping("/questions")
    fun getQuestions(): String {
        return questionJsonService.getQuestionStore()
    }

}