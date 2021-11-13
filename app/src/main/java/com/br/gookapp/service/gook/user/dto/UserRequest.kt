package com.br.gookapp.service.gook.user.dto

data class UserRequest(
    val name: String,
    var email: String,
    val pix: String
)