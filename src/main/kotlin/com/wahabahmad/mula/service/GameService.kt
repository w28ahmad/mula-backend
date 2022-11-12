package com.wahabahmad.mula.service

import com.wahabahmad.mula.model.Question
import com.wahabahmad.mula.repository.QuestionRepository
import org.springframework.stereotype.Service

@Service
class GameService(
    private val questionRepository: QuestionRepository
) {
    fun getQuestion(): Question {
        return questionRepository.findById(1).get()
    }
}