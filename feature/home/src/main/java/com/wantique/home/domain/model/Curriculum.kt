package com.wantique.home.domain.model

data class YearlyCurriculum(
    val id: String,
    val year: String,
    val url: String,
    val curriculum: List<Curriculum>
)

data class Curriculum(
    val tag: String,
    val detailCurriculum: List<DetailCurriculum>
)

data class DetailCurriculum(
    val lecture: String,
    val description: String,
    val start: String
)