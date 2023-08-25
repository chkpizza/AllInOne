package com.wantique.home.data.mapper

import android.util.Log
import com.wantique.firebase.model.BannerDto
import com.wantique.firebase.model.CategoryDto
import com.wantique.firebase.model.ExamDto
import com.wantique.firebase.model.ProfessorDto
import com.wantique.home.domain.model.BannerItem
import com.wantique.home.domain.model.ExamItem
import com.wantique.home.domain.model.Home
import com.wantique.home.domain.model.ProfessorItem

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

    fun mapperToDomain(dto: List<ProfessorDto>): List<Home.Professor> {
        return dto.map {
            val professorItemList = mutableListOf<ProfessorItem>()
            it.item.forEach { item ->
                professorItemList.add(ProfessorItem(item.id, item.name, item.belong, item.update))
            }
            Home.Professor(professorItemList)
        }
    }

    fun mapperToDomain(dto: ExamDto): Home.Exam {
        val examItemList = mutableListOf<ExamItem>().apply {
            dto.exam.forEach {
                add(ExamItem(it.regDate, it.examDate, it.name, it.complete))
            }
        }

        return Home.Exam(dto.title, examItemList)
    }
}