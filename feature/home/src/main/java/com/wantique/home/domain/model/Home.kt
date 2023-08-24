package com.wantique.home.domain.model

sealed class Home {
    data class Banner(
        val notice: String,
        val banners: List<BannerItem>
    ) : Home()

    data class Category(
        val title: String,
        val categories: List<String>
    ) : Home()

    data class Professor(
        val professor: List<ProfessorItem>
    ) : Home()
}

data class BannerItem(
    val id: String,
    val url: String
)

data class Professors(
    val professor: List<ProfessorItem>
)

data class ProfessorItem(
    val id: String,
    val name: String,
    val belong: String,
    val update : Boolean
)