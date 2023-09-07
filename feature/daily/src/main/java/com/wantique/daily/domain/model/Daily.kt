package com.wantique.daily.domain.model

import android.os.Parcelable
import com.wantique.firebase.model.PastExamHeaderDto
import kotlinx.parcelize.Parcelize

sealed class Daily {
    data class DailyLetter(
        val letter: String
    ): Daily()

    data class DailyRecord(
        val header: RecordHeader,
        val records: List<Record>
    ) : Daily()

    data class DailyPastExam(
        val header: PastExamHeader,
        val pastExam: TodayPastExam
    ) : Daily()
}

data class RecordHeader(
    val title: String,
    val subTitle: String
)

@Parcelize
data class Record(
    val authorUid: String,
    val documentId: String,
    val date: String,
    val imageUrl: String,
    val body: String,
    val nickName: String,
    val profileImageUrl: String
): Parcelable


data class PastExamHeader(
    val title: String,
    val subTitle: String
)

data class TodayPastExam(
    val pastExam: List<PastExam>
)

@Parcelize
data class PastExam(
    val type: String,
    val question: String,
    val description: List<Description>,
    val choice: List<Choice>,
    val answer: Int,
    val commentary: String,
    val source: String
): Parcelable

@Parcelize
data class Description(
    val number: String,
    val description: String
): Parcelable

@Parcelize
data class Choice(
    val number: String,
    val choice: String
): Parcelable