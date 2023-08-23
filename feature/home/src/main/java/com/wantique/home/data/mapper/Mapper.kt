package com.wantique.home.data.mapper

import com.wantique.firebase.model.BannerDto
import com.wantique.firebase.model.BannerItemDto
import com.wantique.firebase.model.CategoryDto
import com.wantique.home.domain.model.BannerItem
import com.wantique.home.domain.model.Home

object Mapper {
    fun mapperToDomain(dto: BannerDto): Home.Banner {
        val bannerItems = mutableListOf<BannerItem>().apply {
            dto.item.forEach {
                add(BannerItem(it.id, it.url))
            }
        }

        return Home.Banner(dto.notice, bannerItems)
    }

    fun mapperToDomain(dto: CategoryDto): Home.Category {
        return Home.Category(dto.title, dto.item)
    }
}