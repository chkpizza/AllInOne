package com.wantique.firebase.model

data class DailyPromiseTitleDto(
    val title: String = "",
    val subTitle: String = "",
)

data class PromiseDto(
    val imageUrl: String = "",
    val body: String = "",
    val authorId: String = "",
    val documentId: String = ""
)

data class DailyLetterDto(
    val todayLetter: String = ""
)