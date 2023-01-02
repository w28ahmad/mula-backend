package com.wahabahmad.mula.service

import com.wahabahmad.mula.model.Question
import com.wahabahmad.mula.model.User
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
    private val diagramService: DiagramService,
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
    ): Pair<User, Question?> {
        val sessionBackupSize = sessionUtil.getSessionBackupSize(sessionId)
        val actualSolution = questionSolutionsRepository.findByQuestionId(questionId)
        val isCorrect = actualSolution.correctSolution == solution
        val updatedUser =
            if (isCorrect) sessionUtil.incrementPlayerScore(sessionId, user)
            else sessionUtil.decrementPlayerScore(sessionId, user)

        val backupQuestion =
            if (sessionBackupSize < updatedUser.numberIncorrect) {
                sessionUtil.incrementSessionBackupSize(sessionId)
                with(sessionUtil.getBackupQuestion(sessionId)) {
                    diagram = getDiagramUrl(diagramService)
                    this
                }
            } else null

        /* Game finished */
        if (updatedUser.score == QUESTION_SET_SIZE) sessionService.disconnect(sessionId, listOf(updatedUser))
        return Pair(updatedUser, backupQuestion)
    }
}