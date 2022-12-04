package com.wahabahmad.mula.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service

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

@Service
class QuestionJsonService(
    @Value("classpath:questionStore.json")
    private val questionStore: Resource,
) {

    fun getQuestionStore(): String {
        return questionStore.file.readText()
    }

}