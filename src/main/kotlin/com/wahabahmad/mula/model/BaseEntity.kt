package com.wahabahmad.mula.model

import java.time.LocalDateTime
import javax.persistence.Id
import javax.persistence.MappedSuperclass

@MappedSuperclass
open class BaseEntity (
    @Id
    open val id: Int? = 0,

    open val createdAt : LocalDateTime? = null,

    open val deletedAt: LocalDateTime? = null,
)