package com.wahabahmad.mula.service

import com.wahabahmad.mula.data.User
import com.wahabahmad.mula.model.Question
import com.wahabahmad.mula.repository.QuestionRepository
import com.wahabahmad.mula.repository.QuestionSolutionsRepository
import com.wahabahmad.mula.util.RandomUtil
import com.wahabahmad.mula.util.SessionUtil
import org.springframework.stereotype.Service

@Service
class GameService(
    private val questionRepository: QuestionRepository,
    private val questionSolutionsRepository: QuestionSolutionsRepository,
    private val sessionService: SessionService,
    private val sessionUtil: SessionUtil,
    private val randomUtil: RandomUtil
) {
    companion object {
        const val QUESTION_SET_SIZE = 5
    }

    fun checkQuestionSolution(
        sessionId: String,
        user: User,
        questionId: Int,
        solution: String
    ): Pair<User, Boolean> {
        val actualSolution = questionSolutionsRepository.findByQuestionId(questionId)
        val isCorrect = actualSolution.correctSolution == solution
        val updatedUser = if (isCorrect) sessionUtil.incrementPlayerScore(sessionId, user) else user
        /* Game finished */
        if (updatedUser.score == QUESTION_SET_SIZE) sessionService.disconnect(sessionId, listOf(updatedUser))
        return Pair(updatedUser, isCorrect)
    }


    fun backupQuestion(): Question {
        val count = questionRepository.count().toInt()
        val id = randomUtil.getRandomInt(min = 1, max = count)
        return questionRepository.findById(id).get()
    }

}