package com.wantique.firebase.model

data class ProfessorsDto(
    val item: List<ProfessorPreviewDto> = emptyList()
)

data class ProfessorPreviewDto(
    val id: String = "",
    val name: String = "",
    val belong: String = "",
    val update : Boolean = false
)