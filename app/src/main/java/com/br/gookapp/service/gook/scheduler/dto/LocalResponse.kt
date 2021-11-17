package com.br.gookapp.service.gook.scheduler.dto

import java.time.LocalDateTime

data class LocalResponse(
    val id: Long,
    val name: String,
    val address: AddressResponse,
    val courts: List<CourtResponse>,
    val createDate: LocalDateTime,
    val lasModifiedDate: LocalDateTime
)