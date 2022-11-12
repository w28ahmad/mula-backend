package com.wahabahmad.mula.repository

import com.wahabahmad.mula.model.Question
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface QuestionRepository : JpaRepository<Question, Int>