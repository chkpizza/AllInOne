package com.wantique.firebase.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.ServerTimestamp

data class NoticeDto(
    val header: NoticeHeaderDto = NoticeHeaderDto(),
    val notice: List<NoticeItemDto> = emptyList()
)

data class NoticeHeaderDto(
    val header: String = ""
)

data class NoticeItemDto(
    val title: String = "",
    val body: String = "",
    val url: String = "",
    val name: String = "",
    val documentId: String = "",
    @ServerTimestamp
    val timestamp: Timestamp? = null
)