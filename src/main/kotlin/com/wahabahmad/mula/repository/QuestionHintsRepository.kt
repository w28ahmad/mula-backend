package com.wahabahmad.mula.repository

import com.wahabahmad.mula.model.QuestionHints
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface QuestionHintsRepository : JpaRepository<QuestionHints, Int> {

    @Modifying
    @Transactional
    @Query("""
            UPDATE QuestionHints qh 
            SET qh.hintOne = :hintOne, 
                qh.hintTwo = :hintTwo, 
                qh.hintThree = :hintThree,
                qh.hintFour = :hintFour,
                qh.questionId = :id
            WHERE qh.id = :id
            """)
    fun updateQuestionHintsById(
        @Param("id") id: Int,
        @Param("hintOne") hintOne: String,
        @Param("hintTwo") hintTwo: String,
        @Param("hintThree") hintThree: String,
        @Param("hintFour") hintFour: String,
    )
}