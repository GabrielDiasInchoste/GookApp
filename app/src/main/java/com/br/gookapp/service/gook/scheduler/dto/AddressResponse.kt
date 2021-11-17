package com.br.gookapp.service.gook.scheduler.dto

import java.time.LocalDateTime

data class AddressResponse(
    val id: Long,
    val name: String,
    val number: Int,
    val description: String,
    val cep: String,
    val createDate: LocalDateTime,
    val lasModifiedDate: LocalDateTime
)