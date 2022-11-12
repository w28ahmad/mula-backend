package com.wahabahmad.mula.repository

import com.wahabahmad.mula.model.QuestionSolutions
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface QuestionSolutionsRepository : JpaRepository<QuestionSolutions, Int>