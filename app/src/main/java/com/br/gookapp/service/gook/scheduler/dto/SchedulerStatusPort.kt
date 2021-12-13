package com.br.gookapp.service.gook.scheduler.dto

enum class SchedulerStatusPort {
    REQUESTED,
    CONFIRMED,
    CANCEL_REQUESTED,
    CANCELED
}

fun SchedulerStatusPort.toResponse() =
    when (this) {
        SchedulerStatusPort.REQUESTED -> "Em Analise"
        SchedulerStatusPort.CONFIRMED -> "Confirmado"
        SchedulerStatusPort.CANCEL_REQUESTED -> "Solicitado Cancelamento"
        SchedulerStatusPort.CANCELED -> "Cancelado"
    }