package com.wantique.home.domain.repository

import com.wantique.base.state.Resource
import com.wantique.home.domain.model.ProfessorInfo
import com.wantique.home.domain.model.YearlyCurriculum
import kotlinx.coroutines.flow.Flow

interface ProfessorDetailsRepository {
    fun getProfessorCurriculum(professorId: String): Flow<Resource<YearlyCurriculum>>
    fun getProfessorInfo(professorId: String): Flow<Resource<ProfessorInfo>>
}