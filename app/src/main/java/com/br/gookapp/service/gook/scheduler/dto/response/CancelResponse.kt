package com.br.gookapp.service.gook.scheduler.dto.response

import java.time.LocalDateTime

data class CancelResponse(
    val id: Long,
    val description: String,
    val cancelRequestedDate: LocalDateTime,
    val cancelConfirmedDate: LocalDateTime?
): java.io.Serializable