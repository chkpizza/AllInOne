package com.wantique.home.domain.repository

import com.wantique.base.state.Resource
import com.wantique.home.domain.model.Home

interface HomeRepository {
    suspend fun getBanner(): Resource<Home.Banner>
    suspend fun getCategory(): Resource<Home.Category>
    suspend fun getProfessors(): Resource<List<Home.Professor>>
    suspend fun getYearlyExam(): Resource<Home.Exam>
}