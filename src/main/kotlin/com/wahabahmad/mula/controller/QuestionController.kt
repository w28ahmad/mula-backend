package com.wahabahmad.mula.controller

import com.wahabahmad.mula.data.Question
import com.wahabahmad.mula.service.DiagramService
import com.wahabahmad.mula.service.QuestionJsonService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class QuestionController(
    private val questionJsonService: QuestionJsonService,
) {

    @GetMapping("/questionCount")
    fun getQuestionCount(): Int {
        return questionJsonService.getQuestionCount()
    }

    @GetMapping("/getQuestion")
    fun getQuestionByIdx(@RequestParam("idx") idx: Int): Question =
        questionJsonService.getQuestionByIdx(idx)

    @PostMapping("/putQuestion")
    fun putQuestionByIdx(@RequestParam("idx") idx: Int, @RequestBody question: Question): Unit =
        questionJsonService.putQuestionByIdx(idx, question)

    @PostMapping("createQuestion")
    fun createQuestionByIdx(@RequestBody question: Question): Unit =
        questionJsonService.createQuestion(question)
}