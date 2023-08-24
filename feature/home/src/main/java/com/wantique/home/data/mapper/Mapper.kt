package com.wantique.home.data.mapper

import android.util.Log
import com.wantique.firebase.model.BannerDto
import com.wantique.firebase.model.BannerItemDto
import com.wantique.firebase.model.CategoryDto
import com.wantique.firebase.model.ProfessorsDto
import com.wantique.home.domain.model.BannerItem
import com.wantique.home.domain.model.Home
import com.wantique.home.domain.model.ProfessorItem
import com.wantique.home.domain.model.Professors

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

    fun mapperToDomain(dto: List<ProfessorsDto>): List<Professors> {

        val domain = mutableListOf<Professors>()

        dto.forEach {
            it.item.map { dto ->
                Log.d("ProfessorsMapping", "${dto.toString()}")
                ProfessorItem(dto.id, dto.name, dto.belong, dto.update)
            }.apply {
                domain.add(Professors(this))
            }
        }

        return domain
    }
}