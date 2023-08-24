package com.wantique.firebase.model

data class ProfessorDto(
    val item: List<ProfessorItemDto> = emptyList()
)

data class ProfessorItemDto(
    val id: String = "",
    val name: String = "",
    val belong: String = "",
    val update : Boolean = false
)