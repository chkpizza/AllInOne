package com.wantique.home.domain.model

sealed class Home {
    data class Banner(
        val notice: String = "",
        val banners: List<BannerItem> = emptyList()
    ) : Home()

    data class Category(
        val title: String = "",
        val categories: List<String> = emptyList()
    ) : Home()
}

data class BannerItem(
    val id: String = "",
    val url: String = ""
)