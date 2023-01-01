package com.wahabahmad.mula.response

import com.wahabahmad.mula.data.SocketMessageTypes
import com.wahabahmad.mula.model.Question

data class BackupQuestionResponse(
    val type: SocketMessageTypes = SocketMessageTypes.BACKUP_QUESTION,
    val backupQuestion: Question
)
