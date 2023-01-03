package com.wahabahmad.mula.model

data class User(
    var id: String? = null,
    val name: String,
    var score: Int = 0,
    var numberCorrect: Int = 0,
    var numberIncorrect: Int = 0,
    var isCorrect: Boolean,
)