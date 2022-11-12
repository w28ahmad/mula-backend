package com.wahabahmad.mula.repository

import com.wahabahmad.mula.model.QuestionDetails
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface QuestionDetailsRepository : JpaRepository<QuestionDetails, Int>