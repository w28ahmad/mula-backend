package com.wahabahmad.mula.model

import com.wahabahmad.mula.service.DiagramService
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.OneToOne

@Entity
data class Question(

    val questionSnippet: String? = null,

    var diagram: String? = null,

    @OneToOne
    @JoinColumn(name = "id")
    val options: QuestionOptions? = null,

    ) : BaseEntity() {
    fun getDiagramUrl(diagramService: DiagramService): String? =
        diagram?.takeIf { it.isNotEmpty() }?.let { diagramService.get(it).toString() }
}
