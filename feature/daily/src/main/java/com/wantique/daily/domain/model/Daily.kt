package com.wantique.daily.domain.model

sealed class Daily {
    data class DailyLetter(
        val letter: String
    ) : Daily()

    data class DailyPromise(
        val title: String,
        val subTitle: String,
        val promise: List<Promise>
    ) : Daily()

    data class DailyPastExam(
        val title: String,
        val subTitle: String,
        val pastExam: List<PastExam>
    ) : Daily()
}

data class Promise(
    val imageUrl: String,
    val body: String,
    val authorId: String,
    val documentId: String
)

data class PastExam(
    val type: String,
    val question: String,
    val description: List<Description>,
    val choice: List<Choice>,
    val answer: Int,
    val commentary: String,
    val source: String
)

data class Description(
    val number: String,
    val description: String
)

data class Choice(
    val number: String,
    val choice: String
)