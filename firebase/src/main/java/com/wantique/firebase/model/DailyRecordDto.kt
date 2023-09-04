package com.wantique.firebase.model


data class DailyRecordDto(
    val recordHeader: RecordHeaderDto = RecordHeaderDto(),
    val record: List<TodayRecordDto> = emptyList()
)

data class RecordHeaderDto(
    val title: String = "",
    val subTitle: String = ""
)

data class TodayRecordDto(
    val authorUid: String,
    val documentId: String,
    val date: String,
    val imageUrl: String,
    val body: String,
    val nickName: String,
    val profileImageUrl: String
)

data class RecordDto(
    val authorUid: String = "",
    val documentId: String = "",
    val date: String = "",
    val enable: Boolean = false,
    val imageUrl: String = "",
    val body: String = ""
)