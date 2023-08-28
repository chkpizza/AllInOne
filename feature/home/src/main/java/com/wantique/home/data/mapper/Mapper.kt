package com.wantique.home.data.mapper

import com.wantique.firebase.model.BannerDto
import com.wantique.firebase.model.CategoryDto
import com.wantique.firebase.model.ExamDto
import com.wantique.firebase.model.ProfessorDto
import com.wantique.firebase.model.ProfessorInfoDto
import com.wantique.firebase.model.YearlyCurriculumDto
import com.wantique.home.domain.model.BannerItem
import com.wantique.home.domain.model.Curriculum
import com.wantique.home.domain.model.DetailCurriculum
import com.wantique.home.domain.model.ExamItem
import com.wantique.home.domain.model.Home
import com.wantique.home.domain.model.ProfessorInfo
import com.wantique.home.domain.model.ProfessorItem
import com.wantique.home.domain.model.YearlyCurriculum

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

    fun mapperToDomain(dto: YearlyCurriculumDto): YearlyCurriculum {
        mutableListOf<Curriculum>().apply {
            dto.curriculum.forEach {
                val detailCurriculumList = mutableListOf<DetailCurriculum>()
                it.detailCurriculum.forEach { detail ->
                    detailCurriculumList.add(DetailCurriculum(detail.lecture, detail.description, detail.start))
                }
                add(Curriculum(it.tag, detailCurriculumList))
            }
            return YearlyCurriculum(dto.id, dto.year, dto.url, this)
        }
    }

    fun mapperToDomain(dto: ProfessorInfoDto): ProfessorInfo {
        return ProfessorInfo(dto.name, dto.slogan, dto.url)
    }
}