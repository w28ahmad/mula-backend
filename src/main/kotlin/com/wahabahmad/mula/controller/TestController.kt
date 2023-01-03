package com.wahabahmad.mula.controller

import com.wahabahmad.mula.model.Question
import com.wahabahmad.mula.model.QuestionDetails
import com.wahabahmad.mula.model.QuestionHints
import com.wahabahmad.mula.model.QuestionOptions
import com.wahabahmad.mula.model.QuestionSolutions
import com.wahabahmad.mula.repository.QuestionDetailsRepository
import com.wahabahmad.mula.repository.QuestionHintsRepository
import com.wahabahmad.mula.repository.QuestionOptionsRepository
import com.wahabahmad.mula.repository.QuestionRepository
import com.wahabahmad.mula.repository.QuestionSolutionsRepository
import com.wahabahmad.mula.request.Message
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import redis.clients.jedis.Jedis


@RestController
class TestController(
    private val questionRepository: QuestionRepository,
    private val questionDetailsRepository: QuestionDetailsRepository,
    private val questionHintsRepository: QuestionHintsRepository,
    private val questionOptionsRepository: QuestionOptionsRepository,
    private val questionSolutionsRepository: QuestionSolutionsRepository,
    private val jedis: Jedis
) {
    @GetMapping("/test1")
    fun test1(): List<Question> {
        return questionRepository.findAll()
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
    fun post(message: Message): Message {
        return message
    }

    @GetMapping("/test6")
    fun test6(): String = jedis.ping()
}