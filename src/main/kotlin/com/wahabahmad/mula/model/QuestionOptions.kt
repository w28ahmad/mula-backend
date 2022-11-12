package com.wahabahmad.mula.model

import javax.persistence.Column
import javax.persistence.Entity

@Entity
data class QuestionOptions(
    @Column(name="option_a")
    val optionA : String? = null,

    @Column(name="option_b")
    val optionB : String? = null,

    @Column(name="option_c")
    val optionC : String? = null,

    @Column(name="option_d")
    val optionD : String? = null,
) : BaseEntity()
