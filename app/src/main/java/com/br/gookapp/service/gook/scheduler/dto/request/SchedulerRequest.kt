package com.br.gookapp.service.gook.scheduler.dto.request

import java.time.LocalDateTime

data class SchedulerRequest(
    val customerEmail: String,
    val courtId: Long,
    val schedule: LocalDateTime
): java.io.Serializable
