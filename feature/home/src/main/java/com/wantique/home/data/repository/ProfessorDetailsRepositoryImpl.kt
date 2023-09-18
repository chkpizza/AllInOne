package com.wantique.home.data.repository

import com.wantique.base.state.Resource
import com.wantique.firebase.Firebase
import com.wantique.firebase.model.ProfessorInfoDto
import com.wantique.firebase.model.YearlyCurriculumDto
import com.wantique.home.domain.repository.ProfessorDetailsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ProfessorDetailsRepositoryImpl @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val firebase: Firebase
) : ProfessorDetailsRepository {
    override fun getProfessorCurriculum(professorId: String): Flow<Resource<YearlyCurriculumDto>> = flow {
        firebase.getProfessorCurriculum(professorId)?.let {
            emit(Resource.Success(it))
        } ?: run {
            emit(Resource.Error(Throwable("커리큘럼 정보를 가져오지 못했습니다")))
        }
    }.catch { e ->
        emit(Resource.Error(e))
    }.flowOn(dispatcher)

    override fun getProfessorInfo(professorId: String): Flow<Resource<ProfessorInfoDto>> = flow {
        firebase.getProfessorInfo(professorId)?.let {
            emit(Resource.Success(it))
        } ?: run {
            emit(Resource.Error(Throwable("교수님의 정보를 가져오지 못했습니다")))
        }
    }.catch { e ->
        emit(Resource.Error(e))
    }.flowOn(dispatcher)
}