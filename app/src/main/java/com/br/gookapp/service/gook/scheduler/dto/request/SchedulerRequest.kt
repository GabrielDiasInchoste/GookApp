package com.br.gookapp.service.gook.scheduler.dto.request

import java.time.LocalDateTime

data class SchedulerRequest(
    val tokenSendPush :String,
    val customerEmail: String,
    val courtId: Long,
    val schedule: LocalDateTime
): java.io.Serializable
