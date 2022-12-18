package com.wahabahmad.mula.repository

import com.wahabahmad.mula.model.QuestionSolutions
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface QuestionSolutionsRepository : JpaRepository<QuestionSolutions, Int> {

    fun findByQuestionId(questionId: Int): QuestionSolutions


    @Modifying
    @Transactional
    @Query("""
            UPDATE QuestionSolutions qs 
            SET qs.correctSolution = :correctSolution, 
                qs.explanation = :explanation, 
                qs.diagram = :diagram,
                qs.questionId = :id
            WHERE qs.id = :id
            """)
    fun updateQuestionSolutionById(
        @Param("id") id: Int,
        @Param("correctSolution") correctSolution: String,
        @Param("explanation") explanation: String,
        @Param("diagram") diagram: String,
    )

}