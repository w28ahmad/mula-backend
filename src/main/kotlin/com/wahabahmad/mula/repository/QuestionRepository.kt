package com.wahabahmad.mula.repository

import com.wahabahmad.mula.model.Question
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface QuestionRepository : JpaRepository<Question, Int> {

    fun findByIdIn(ids: List<Int>): List<Question>

    @Modifying
    @Transactional
    @Query("UPDATE Question q SET q.questionSnippet = :question, q.diagram = :diagram WHERE q.id = :id")
    fun updateQuestionById(
        @Param("id") id: Int,
        @Param("question") question: String,
        @Param("diagram") diagram: String
    )
}