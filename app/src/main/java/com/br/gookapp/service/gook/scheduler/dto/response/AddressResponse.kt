package com.br.gookapp.service.gook.scheduler.dto.response

import java.time.LocalDateTime

data class AddressResponse(
    val id: Long,
    val name: String,
    val number: Int,
    val description: String,
    val cep: String,
    val latitude: String,
    val longitude: String,
    val createDate: LocalDateTime,
    val lasModifiedDate: LocalDateTime
): java.io.Serializable