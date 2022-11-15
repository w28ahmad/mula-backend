package com.wahabahmad.mula.model

import javax.persistence.Entity

@Entity
data class QuestionHints(
    val hintOne: String? = null,

    val hintTwo: String? = null,

    val hintThree: String? = null,

    val hintFour: String? = null,

    val questionId : Int? = null,
) : BaseEntity()
