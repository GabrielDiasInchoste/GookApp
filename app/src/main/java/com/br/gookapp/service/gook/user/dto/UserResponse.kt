package com.br.gookapp.service.gook.user.dto

import java.time.LocalDateTime

data class UserResponse(
    val id: Long,
    val name: String,
    var email: String,
    val pix: String,
    val permissions: List<UserPermission>,
    val createDate: LocalDateTime,
    val lasModifiedDate: LocalDateTime
)