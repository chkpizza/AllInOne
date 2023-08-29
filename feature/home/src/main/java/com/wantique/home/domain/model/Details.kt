package com.wantique.home.domain.model

sealed class Details {
    data class YearlyCurriculumTest(
        val id: String,
        val year: String,
        val url: String,
        val curriculum: List<CurriculumTest>
    ) : Details()

    data class ProfessorInfoTest(
        val name: String,
        val slogan: String,
        val url: String
    ) : Details()
}

/*
data class YearlyCurriculumTest(
    val id: String,
    val year: String,
    val url: String,
    val curriculum: List<CurriculumTest>
)


 */
data class CurriculumTest(
    val tag: String,
    val detailCurriculum: List<DetailCurriculumTest>
)

data class DetailCurriculumTest(
    val lecture: String,
    val description: String,
    val start: String
)