package com.wahabahmad.mula.model

import java.time.LocalDateTime
import javax.persistence.Id
import javax.persistence.MappedSuperclass
import javax.persistence.PrePersist

@MappedSuperclass
open class BaseEntity (
    @Id
    open val id: Int? = 0,

    open var createdAt : LocalDateTime? = null,

    open val deletedAt: LocalDateTime? = null,
) {
    @PrePersist
    fun prePersist() {
        createdAt = LocalDateTime.now()
    }
}