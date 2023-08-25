package com.wantique.firebase.model

data class ExamDto(
    val title: String = "",
    val exam: List<ExamItemDto> = emptyList()
)

data class ExamItemDto(
    val regDate: String = "",
    val examDate: String = "",
    val name: String = "",
    val complete: Boolean = false
)