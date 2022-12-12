package com.wahabahmad.mula.data

data class QuestionsJson(
    val questions: MutableList<Question>
)

data class Question(
    val questionData: QuestionData,
    val solutionData: SolutionData,
    val details: Details,
    val hints: Hints
)

data class QuestionData(
    val question: String,
    val optionA: String,
    val optionB: String,
    val optionC: String,
    val optionD: String,
    val optionE: String,
    val diagram: String
)

data class SolutionData(
    val solution: String,
    val explanation: String,
    val diagram: String
)

data class Details(
    val grade: Int,
    val partType: String,
    val partNumber: Int,
    val partSize: Int,
    val subject: String,
    val topic: String
)

data class Hints(
    val hintOne: String,
    val hintTwo: String,
    val hintThree: String,
    val hintFour: String,
)
