package com.br.gookapp.service.gook.scheduler.dto

import java.time.LocalDateTime

data class SchedulerRequest(
    val customerEmail: String,
    val courtId: Long? = null,
    val schedule: LocalDateTime? = null
)
