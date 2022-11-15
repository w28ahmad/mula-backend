package com.wahabahmad.mula.model

import javax.persistence.Entity

@Entity
data class QuestionDetails(
    val grade : Int? = null,

    val partType : String? = null,

    val partNumber : Int? = null,

    val partSize : Int? = null,

    val questionId : Int? = null,
) : BaseEntity()
