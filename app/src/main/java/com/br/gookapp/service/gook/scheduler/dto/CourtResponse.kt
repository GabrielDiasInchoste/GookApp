package com.br.gookapp.service.gook.scheduler.dto

import java.time.LocalDateTime

data class CourtResponse(
    val id: Long,
    val name: String,
    val type: String,
    val localId: Long,
    val description: String,
    val createDate: LocalDateTime,
    val lasModifiedDate: LocalDateTime
): java.io.Serializable