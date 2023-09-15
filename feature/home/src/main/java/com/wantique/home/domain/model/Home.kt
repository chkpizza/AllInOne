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
        val professors: List<ProfessorItem>
    ) : Home()

    data class YearlyExamPlan(
        val title: String,
        val exam: List<ExamPlanItem>
    ) : Home()

    data class Notice(
        val header: String,
        val notice: List<NoticeItem>
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

data class ExamPlanItem(
    val regDate: String,
    val examDate: String,
    val name: String,
    val complete: Boolean
)

data class NoticeItem(
    val title: String,
    val body: String,
    val url: String,
    val name: String,
    val documentId: String
)