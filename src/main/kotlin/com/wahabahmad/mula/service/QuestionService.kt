package com.wahabahmad.mula.service

import com.wahabahmad.mula.data.Details
import com.wahabahmad.mula.data.Hints
import com.wahabahmad.mula.data.Question
import com.wahabahmad.mula.data.QuestionData
import com.wahabahmad.mula.data.SolutionData
import com.wahabahmad.mula.exception.CommonExceptions
import com.wahabahmad.mula.repository.QuestionDetailsRepository
import com.wahabahmad.mula.repository.QuestionHintsRepository
import com.wahabahmad.mula.repository.QuestionOptionsRepository
import com.wahabahmad.mula.repository.QuestionRepository
import com.wahabahmad.mula.repository.QuestionSolutionsRepository
import org.springframework.stereotype.Service

@Service
class QuestionService(
    private val questionRepository: QuestionRepository,
    private val solutionsRepository: QuestionSolutionsRepository,
    private val detailsRepository: QuestionDetailsRepository,
    private val hintsRepository: QuestionHintsRepository,
    private val questionOptionsRepository: QuestionOptionsRepository
) {
    fun getQuestionCount(): Int = questionRepository.count().toInt()

    fun getQuestionById(id: Int): Question {
        if (id <= 0 || id > getQuestionCount())
            throw Exception(CommonExceptions.OUT_OF_RANGE)
        val question = questionRepository.findById(id).get()
        val solution = solutionsRepository.findById(id).get()
        val details = detailsRepository.findById(id).get()
        val hints = hintsRepository.findById(id).get()

        val questionData = QuestionData(
            question.questionSnippet!!,
            question.options!!.optionA!!,
            question.options!!.optionB!!,
            question.options!!.optionC!!,
            question.options!!.optionD!!,
            question.options!!.optionE!!,
            question.diagram!!
        )

        val solutionData = SolutionData(
            solution.correctSolution!!,
            solution.explanation!!,
            solution.diagram!!
        )

        val detailsData = Details(
            details.grade!!,
            details.partType!!,
            details.partNumber!!,
            details.partSize!!,
            details.subject!!,
            details.topic!!,
        )

        val hintsData = Hints(
            hints.hintOne!!,
            hints.hintTwo!!,
            hints.hintThree!!,
            hints.hintFour!!,
        )

        return Question(questionData, solutionData, detailsData, hintsData)
    }

    fun putQuestionById(id: Int, question: Question): Unit {
        if (id <= 0 || id > getQuestionCount())
            throw Exception(CommonExceptions.OUT_OF_RANGE)

        with(question.questionData) {
            questionRepository.updateQuestionById(
                id,
                this.question,
                diagram
            )
        }

        with(question.questionData) {
            questionOptionsRepository.updateQuestionOptionsById(
                id,
                optionA,
                optionB,
                optionC,
                optionD,
                optionE
            )
        }

        with(question.solutionData) {
            solutionsRepository.updateQuestionSolutionById(
                id,
                solution,
                explanation,
                diagram,
            )

            with(question.details) {
                detailsRepository.updateQuestionDetailsById(
                    id,
                    grade,
                    partType,
                    partNumber,
                    partSize,
                    subject,
                    topic,
                )
            }

            with(question.hints) {
                hintsRepository.updateQuestionHintsById(
                    id,
                    hintOne,
                    hintTwo,
                    hintThree,
                    hintFour
                )
            }
        }


    }

    fun createQuestion(question: Question) {
//        questionData.questions.add(question)
    }
}