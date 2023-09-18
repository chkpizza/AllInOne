package com.wantique.home.data.mapper

import com.wantique.firebase.model.BannerDto
import com.wantique.firebase.model.CategoryDto
import com.wantique.firebase.model.NoticeDto
import com.wantique.firebase.model.NoticeItemDto
import com.wantique.firebase.model.ProfessorInfoDto
import com.wantique.firebase.model.ProfessorPreviewDto
import com.wantique.firebase.model.YearlyCurriculumDto
import com.wantique.firebase.model.YearlyExamPlanDto
import com.wantique.home.domain.model.BannerItem
import com.wantique.home.domain.model.Curriculum
import com.wantique.home.domain.model.DetailCurriculum
import com.wantique.home.domain.model.ExamPlanItem
import com.wantique.home.domain.model.Home
import com.wantique.home.domain.model.NoticeItem
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

    fun mapperToDomain(dto: List<ProfessorPreviewDto>): List<Home.Professor> {
        return dto.map {
            val professorItemList = mutableListOf<ProfessorItem>()
            it.item.forEach { item ->
                professorItemList.add(ProfessorItem(item.id, item.name, item.belong, item.update))
            }
            Home.Professor(professorItemList)
        }
    }

    fun mapperToDomain(dto: YearlyExamPlanDto): Home.YearlyExamPlan {
        val examPlanItemList = mutableListOf<ExamPlanItem>().apply {
            dto.exam.forEach {
                add(ExamPlanItem(it.regDate, it.examDate, it.name, it.complete))
            }
        }
        return Home.YearlyExamPlan(dto.title, examPlanItemList)
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

    fun mapperToDomain(dto: NoticeDto): Home.Notice {
        mutableListOf<NoticeItem>().apply {
            dto.notice.forEach {
                add(NoticeItem(it.title, it.body, it.url, it.name, it.documentId))
            }

            return Home.Notice(dto.header.header, this)
        }
    }

    fun mapperToDomain(dto: NoticeItemDto): NoticeItem {
        return NoticeItem(dto.title, dto.body, dto.url, dto.name, dto.documentId)
    }

    /*
    fun mapperToDomain(dto: List<NoticeItemDto>): List<NoticeItem> {
        return dto.map {
            NoticeItem(it.title, it.body, it.url, it.name, it.documentId)
        }
    }

     */
    fun mapperToDomain(vararg dto: NoticeItemDto): List<NoticeItem> {
        return dto.map {
            NoticeItem(it.title, it.body, it.url, it.name, it.documentId)
        }
    }
}