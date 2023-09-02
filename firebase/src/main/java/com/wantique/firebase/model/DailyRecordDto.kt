package com.wantique.firebase.model


data class DailyRecordDto(
    val recordHeader: RecordHeaderDto = RecordHeaderDto(),
    val record: List<RecordDto> = emptyList()
)

data class RecordHeaderDto(
    val title: String = "",
    val subTitle: String = ""
)

data class RecordDto(
    val authorUid: String = "",
    val documentId: String = "",
    val date: String = "",
    val enable: Boolean = false,
    val imageUrl: String = "",
    val body: String = ""
)