package com.wahabahmad.mula.controller

import com.wahabahmad.mula.data.Question
import com.wahabahmad.mula.service.QuestionJsonService
import com.wahabahmad.mula.service.QuestionService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class QuestionController(
    private val questionJsonService: QuestionJsonService,
    private val questionService: QuestionService
) {

    @GetMapping("/questionCount")
    fun getQuestionCount(): Int =
        questionService.getQuestionCount()

    @GetMapping("/getQuestion")
    fun getQuestionByIdx(@RequestParam("id") id: Int): Question =
        questionService.getQuestionById(id)

    @PostMapping("/putQuestion")
    fun putQuestionByIdx(@RequestParam("id") id: Int, @RequestBody question: Question): Unit {
//        questionJsonService.putQuestionByIdx(id, question)
        questionService.putQuestionById(id, question)
    }

    @PostMapping("createQuestion")
    fun createQuestionByIdx(@RequestBody question: Question): Unit =
//        questionJsonService.createQuestion(question)
        questionService.createQuestion(question)

}