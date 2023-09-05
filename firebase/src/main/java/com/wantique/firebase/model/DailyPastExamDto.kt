package com.wantique.firebase.model

data class DailyPastExamDto(
    val pastExamHeader: PastExamHeaderDto = PastExamHeaderDto(),
    val todayPastExam: TodayPastExamDto = TodayPastExamDto()
)

data class PastExamHeaderDto(
    val title: String = "",
    val subTitle: String = ""
)

data class TodayPastExamDto(
    val pastExam: List<PastExamDto> = emptyList()
)

data class PastExamDto(
    val type: String = "",
    val question: String = "",
    val description: List<DescriptionDto> = emptyList(),
    val choice: List<ChoiceDto> = emptyList(),
    val answer: Int = -1,
    val commentary: String = "",
    val source: String = ""
)

data class DescriptionDto(
    val number: String = "",
    val description: String = ""
)

data class ChoiceDto(
    val number: String = "",
    val choice: String = ""
)
