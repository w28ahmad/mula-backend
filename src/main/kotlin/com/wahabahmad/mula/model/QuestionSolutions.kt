package com.wahabahmad.mula.model

import javax.persistence.Entity

@Entity
data class QuestionSolutions (
    val correctSolution : String? = null,

    val diagram : String? = null,

    val explanation : String? = null
) : BaseEntity()

