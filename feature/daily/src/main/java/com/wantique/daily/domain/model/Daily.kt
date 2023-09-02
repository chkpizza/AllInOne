package com.wantique.daily.domain.model

sealed class Daily {
    data class DailyLetter(
        val letter: String
    ): Daily()

    data class DailyRecord(
        val header: RecordHeader,
        val records: List<Record>
    ) : Daily()
}

data class RecordHeader(
    val title: String,
    val subTitle: String
)

data class Record(
    val authorUid: String,
    val documentId: String,
    val date: String,
    val imageUrl: String,
    val body: String
)