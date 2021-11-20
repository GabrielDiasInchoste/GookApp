package com.br.gookapp.service.gook.scheduler.dto.response

import java.time.LocalDateTime

data class LocalResponse(
    val id: Long,
    val name: String,
    val address: AddressResponse,
    val courts: ArrayList<CourtResponse>,
    val createDate: LocalDateTime,
    val lasModifiedDate: LocalDateTime
): java.io.Serializable