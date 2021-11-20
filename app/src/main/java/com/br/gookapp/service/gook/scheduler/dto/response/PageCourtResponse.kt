package com.br.gookapp.service.gook.scheduler.dto.response

data class PageCourtResponse(
    val number: Int,
    val numberOfElements: Int,
    val size: Int,
    val totalPages: Int,
    val totalElements: Long,
    val first: Boolean,
    val last: Boolean,
    val courts: List<CourtResponse>
): java.io.Serializable
