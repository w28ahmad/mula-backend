package com.wahabahmad.mula.model

import javax.persistence.Entity
import javax.persistence.OneToOne

@Entity
data class Question(

    val questionSnippet: String? = null,

    val diagram: String? = null,

    @OneToOne
    val options: QuestionOptions? = null,

    @OneToOne
    val solution: QuestionSolutions? = null,

    @OneToOne
    val details: QuestionDetails? = null,

    @OneToOne
    val hints: QuestionHints? = null,

) : BaseEntity()
