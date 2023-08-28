package com.wantique.firebase.model

data class BannerDto(
    val notice: String = "",
    val item: List<BannerItemDto> = emptyList()
)

data class BannerItemDto(
    val id: String = "",
    val url: String = ""
)

