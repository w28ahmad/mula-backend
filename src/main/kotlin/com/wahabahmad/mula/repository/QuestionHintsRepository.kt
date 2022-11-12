package com.wahabahmad.mula.repository

import com.wahabahmad.mula.model.QuestionHints
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface QuestionHintsRepository : JpaRepository<QuestionHints, Int>