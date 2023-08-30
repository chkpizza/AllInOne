package com.wantique.home.domain.repository

import com.wantique.base.state.Resource
import com.wantique.home.domain.model.Home
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    fun getBanner(): Flow<Resource<Home.Banner>>
    fun getCategory(): Flow<Resource<Home.Category>>
    fun getProfessors(): Flow<Resource<List<Home.Professor>>>
    fun getYearlyExam(): Flow<Resource<Home.Exam>>
}