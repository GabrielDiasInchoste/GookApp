package com.br.gookapp.service.gook.scheduler.dto

import java.time.LocalDateTime

data class SchedulerResponse(
    val id: Long,
    val customerEmail: String,
    val status: SchedulerStatusPort,
    val court: CourtResponse,
    val cancel: CancelResponse?,
    val schedule: LocalDateTime?,
    val confirmDate: LocalDateTime?,
    val createDate: LocalDateTime,
    val lasModifiedDate: LocalDateTime
) : java.io.Serializable
