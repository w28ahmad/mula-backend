package com.wahabahmad.mula.model

import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.OneToOne

@Entity
data class Question(

    val questionSnippet: String? = null,

    val diagram: String? = null,

    @OneToOne
    @JoinColumn(name="id")
    val options: QuestionOptions? = null

) : BaseEntity()
