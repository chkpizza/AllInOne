package com.wantique.firebase.model

data class YearlyExamPlanDto(
    val title: String = "",
    val exam: List<ExamPlanDto> = emptyList()
)

data class ExamPlanDto(
    val regDate: String = "",
    val examDate: String = "",
    val name: String = "",
    val complete: Boolean = false
)
