package com.wantique.home.domain.model

import com.wantique.firebase.model.ExamItemDto

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
        val professors: List<ProfessorItem>
    ) : Home()

    data class Exam(
        val title: String,
        val exam: List<ExamItem>
    ) : Home()
}

data class BannerItem(
    val id: String,
    val url: String
)

data class ProfessorItem(
    val id: String,
    val name: String,
    val belong: String,
    val update : Boolean
)

data class ExamItem(
    val regDate: String,
    val examDate: String,
    val name: String,
    val complete: Boolean
)