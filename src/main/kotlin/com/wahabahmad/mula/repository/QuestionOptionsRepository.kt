package com.wahabahmad.mula.repository

import com.wahabahmad.mula.model.QuestionOptions
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface QuestionOptionsRepository : JpaRepository<QuestionOptions, Int> {

    @Modifying
    @Transactional
    @Query("""
            UPDATE QuestionOptions qo 
            SET qo.optionA = :optionA, 
                qo.optionB = :optionB, 
                qo.optionC = :optionC, 
                qo.optionD = :optionD, 
                qo.optionE = :optionE, 
                qo.questionId = :id
            WHERE qo.id = :id
            """)
    fun updateQuestionOptionsById(
        @Param("id") id: Int,
        @Param("optionA") optionA: String,
        @Param("optionB") optionB: String,
        @Param("optionC") optionC: String,
        @Param("optionD") optionD: String,
        @Param("optionE") optionE: String,
    )
}