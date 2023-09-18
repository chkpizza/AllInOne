package com.wantique.home.domain.repository

import com.wantique.base.state.Resource
import com.wantique.firebase.model.BannerDto
import com.wantique.firebase.model.CategoryDto
import com.wantique.firebase.model.NoticeDto
import com.wantique.firebase.model.ProfessorPreviewDto
import com.wantique.firebase.model.YearlyExamPlanDto
import com.wantique.home.domain.model.Home
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    fun getBanner(): Flow<Resource<BannerDto>>
    fun getCategory(): Flow<Resource<CategoryDto>>
    fun getProfessors(): Flow<Resource<List<ProfessorPreviewDto>>>
    fun getYearlyExam(): Flow<Resource<YearlyExamPlanDto>>
    fun getNotice(): Flow<Resource<NoticeDto>>
}