package com.wahabahmad.mula.repository

import com.wahabahmad.mula.model.QuestionDetails
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface QuestionDetailsRepository : JpaRepository<QuestionDetails, Int> {

    @Modifying
    @Transactional
    @Query(
        """
            UPDATE QuestionDetails qd 
            SET qd.grade = :grade,
                qd.partType = :partType,
                qd.partNumber = :partNumber,
                qd.partSize = :partSize,
                qd.subject = :subject,
                qd.topic = :topic,
                qd.questionId = :id
            WHERE qd.id = :id
            """
    )
    fun updateQuestionDetailsById(
        @Param("id") id: Int,
        @Param("grade") grade: Int,
        @Param("partType") partType: String,
        @Param("partNumber") partNumber: Int,
        @Param("partSize") partSize: Int,
        @Param("subject") subject: String,
        @Param("topic") topic: String
    )


    @Query(
        """
        SELECT q.id FROM QuestionDetails q 
        WHERE q.subject = :subject 
        AND q.grade = :grade 
        AND q.partType IN (:difficulty) 
        AND q.topic IN (:topics)
        """
    )
    fun getFilteredQuestionIds(
        @Param("subject") subject: String,
        @Param("grade") grade: Int,
        @Param("difficulty") difficulty: List<String>,
        @Param("topics") topics: List<String>
    ): Set<Int>
}