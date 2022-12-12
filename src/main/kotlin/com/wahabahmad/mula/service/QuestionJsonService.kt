package com.wahabahmad.mula.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.wahabahmad.mula.data.Question
import com.wahabahmad.mula.data.QuestionsJson
import com.wahabahmad.mula.exception.CommonExceptions
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service
import java.io.FileOutputStream

@Service
class QuestionJsonService(
    @Value("classpath:questionStore.json")
    private val questionStore: Resource,
    private val objectMapper: ObjectMapper
) {
    private val mapper = jacksonObjectMapper()
    private val questionData = mapper.readValue(
        questionStore.file.readText(),
        QuestionsJson::class.java
    )

    fun getQuestionCount(): Int = questionData.questions.size

    fun getQuestionByIdx(idx: Int): Question {
        if(idx < 0 || idx >= getQuestionCount())
            throw Exception(CommonExceptions.OUT_OF_RANGE)
        return questionData.questions[idx]
    }

    fun putQuestionByIdx(idx: Int, question: Question): Unit {
        if(idx < 0 || idx >= getQuestionCount())
            throw Exception(CommonExceptions.OUT_OF_RANGE)
        questionData.questions[idx] = question
        saveToQuestionJsonFile()
    }

    fun createQuestion(question: Question) {
        questionData.questions.add(question)
        saveToQuestionJsonFile()
    }

    private fun saveToQuestionJsonFile() {
        val jsonString = objectMapper.writeValueAsString(questionData)
        val file = questionStore.file
        val out = FileOutputStream(file)
        out.write(jsonString.toByteArray())
        out.close()
    }
}

