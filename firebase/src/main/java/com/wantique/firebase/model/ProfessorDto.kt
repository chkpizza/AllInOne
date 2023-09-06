package com.wantique.firebase.model

data class ProfessorPreviewDto(
    val item: List<ProfessorPreviewItemDto> = emptyList()
)

data class ProfessorPreviewItemDto(
    val id: String = "",
    val name: String = "",
    val belong: String = "",
    val update : Boolean = false
)