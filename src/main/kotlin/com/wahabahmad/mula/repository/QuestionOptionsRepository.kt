package com.wahabahmad.mula.repository

import com.wahabahmad.mula.model.QuestionOptions
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface QuestionOptionsRepository : JpaRepository<QuestionOptions, Int>