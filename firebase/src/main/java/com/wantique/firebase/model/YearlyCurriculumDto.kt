package com.wantique.firebase.model

data class YearlyCurriculumDto(
    val id: String = "",
    val year: String = "",
    val url: String = "",
    val curriculum: List<CurriculumDto> = emptyList(),
)

data class CurriculumDto(
    val tag: String = "",
    val detailCurriculum: List<DetailCurriculumDto> = emptyList()
)

data class DetailCurriculumDto(
    val lecture: String = "",
    val description: String = "",
    val start: String = ""
)
