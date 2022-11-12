package com.wahabahmad.mula.controller

import com.wahabahmad.mula.model.*
import com.wahabahmad.mula.repository.*
import com.wahabahmad.mula.request.Message
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*


@RestController
class TestController(
    private val questionRepository: QuestionRepository,
    private val questionDetailsRepository: QuestionDetailsRepository,
    private val questionHintsRepository: QuestionHintsRepository,
    private val questionOptionsRepository: QuestionOptionsRepository,
    private val questionSolutionsRepository: QuestionSolutionsRepository
) {
    @GetMapping("/test1")
    fun test1(): String {
        val q : Optional<Question> = questionRepository.findById(1)
        println(q.get().solution.toString())
        println(q.get().hints.toString())
        println(q.get().details.toString())
        println(q.get().options.toString())
        return "questionRepository.findById(1).toString()"
    }

    @GetMapping("/test2")
    fun test2(): List<QuestionDetails> {
        return questionDetailsRepository.findAll()
    }

    @GetMapping("/test3")
    fun test3(): List<QuestionHints> {
        return questionHintsRepository.findAll()
    }

    @GetMapping("/test4")
    fun test4(): List<QuestionOptions> {
        return questionOptionsRepository.findAll()
    }

    @GetMapping("/test5")
    fun test5(): List<QuestionSolutions> {
        return questionSolutionsRepository.findAll()
    }

    @MessageMapping("/all")
    @SendTo("/topic/all")
    fun post(message: Message) : Message {
        println("HERE111")
        return message
    }
}