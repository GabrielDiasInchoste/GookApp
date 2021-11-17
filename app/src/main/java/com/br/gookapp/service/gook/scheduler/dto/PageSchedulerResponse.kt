package com.br.gookapp.service.gook.scheduler.dto


data class PageSchedulerResponse(
    val number: Int,
    val numberOfElements: Int,
    val size: Int,
    val totalPages: Int,
    val totalElements: Long,
    val first: Boolean,
    val last: Boolean,
    val schedulers: List<SchedulerResponse>
)
