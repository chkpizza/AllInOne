package com.wantique.home.domain.repository

import com.wantique.base.state.Resource
import com.wantique.home.domain.model.Home
import com.wantique.home.domain.model.Professors

interface HomeRepository {
    suspend fun getBanner(): Resource<Home.Banner>
    suspend fun getCategory(): Resource<Home.Category>
    suspend fun getProfessors(): Resource<List<Professors>>
}