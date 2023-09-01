package com.wantique.firebase.model

data class RecordDto(
    val authorUid: String = "",
    val documentId: String = "",
    val date: String = "",
    val enable: Boolean = false,
    val imageUrl: String = "",
    val body: String = ""
)