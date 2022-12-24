package com.wahabahmad.mula.service

import com.wahabahmad.mula.data.Details
import com.wahabahmad.mula.data.Hints
import com.wahabahmad.mula.data.Question
import com.wahabahmad.mula.data.QuestionData
import com.wahabahmad.mula.data.SolutionData
import com.wahabahmad.mula.exception.CommonExceptions
import com.wahabahmad.mula.model.QuestionDetails
import com.wahabahmad.mula.model.QuestionHints
import com.wahabahmad.mula.model.QuestionOptions
import com.wahabahmad.mula.model.QuestionSolutions
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
    private val questionOptionsRepository: QuestionOptionsRepository,
    private val diagramService: DiagramService
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
            question.diagram?.takeIf { it.isNotEmpty() }?.let { diagramService.get(it).toString() }.orEmpty()
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

    fun putQuestionById(id: Int, question: Question) {
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
        val newQuestionId = getQuestionCount() + 1

        with(question.questionData) {
            questionRepository.save(
                com.wahabahmad.mula.model.Question(
                    questionSnippet = this.question,
                    diagram = diagram
                )
            )
        }

        with(question.questionData) {
            questionOptionsRepository.save(
                QuestionOptions(
                    optionA = optionA,
                    optionB = optionB,
                    optionC = optionC,
                    optionD = optionD,
                    optionE = optionE,
                    questionId = newQuestionId
                )
            )
        }

        with(question.solutionData) {
            solutionsRepository.save(
                QuestionSolutions(
                    correctSolution = solution,
                    diagram = diagram,
                    explanation = explanation,
                    questionId = newQuestionId
                )
            )
        }

        with(question.details) {
            detailsRepository.save(
                QuestionDetails(
                    grade = grade,
                    partType = partType,
                    partNumber = partNumber,
                    partSize = partSize,
                    subject = subject,
                    topic = topic,
                    questionId = newQuestionId
                )
            )
        }

        with(question.hints) {
            hintsRepository.save(
                QuestionHints(
                    hintOne = hintOne,
                    hintTwo = hintTwo,
                    hintThree = hintThree,
                    hintFour = hintFour,
                    questionId = newQuestionId
                )
            )
        }
    }
}