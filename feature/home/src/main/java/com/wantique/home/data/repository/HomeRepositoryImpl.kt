package com.wantique.home.data.repository

import com.wantique.base.state.Resource
import com.wantique.firebase.Firebase
import com.wantique.firebase.model.BannerDto
import com.wantique.firebase.model.CategoryDto
import com.wantique.firebase.model.NoticeDto
import com.wantique.firebase.model.ProfessorPreviewDto
import com.wantique.firebase.model.YearlyExamPlanDto
import com.wantique.home.domain.repository.HomeRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(private val dispatcher: CoroutineDispatcher, private val firebase: Firebase) : HomeRepository {
    override fun getBanner(): Flow<Resource<BannerDto>> = flow {
        firebase.getBanner()?.let {
            emit(Resource.Success(it))
        } ?: run {
            emit(Resource.Error(Throwable("배너를 가져오지 못했습니다")))
        }
    }.catch { e ->
        emit(Resource.Error(e))
    }.flowOn(dispatcher)

    override fun getCategory(): Flow<Resource<CategoryDto>> = flow {
        firebase.getCategory()?.let {
            emit(Resource.Success(it))
        } ?: run {
            emit(Resource.Error(Throwable("카테고리를 가져오지 못했습니다")))
        }
    }.catch { e ->
        emit(Resource.Error(e))
    }.flowOn(dispatcher)

    override fun getProfessors() = flow<Resource<List<ProfessorPreviewDto>>> {
        emit(Resource.Success(firebase.getProfessors()))
    }.catch { e ->
        emit(Resource.Error(e))
    }.flowOn(dispatcher)

    override fun getYearlyExam(): Flow<Resource<YearlyExamPlanDto>> = flow {
        firebase.getYearlyExam()?.let {
            emit(Resource.Success(it))
        } ?: run {
            emit(Resource.Error(Throwable("시험 정보를 가져오지 못했습니다")))
        }
    }.catch { e ->
        emit(Resource.Error(e))
    }.flowOn(dispatcher)

    override fun getNotice(): Flow<Resource<NoticeDto>> = flow {
        firebase.getNotice()?.let {
            emit(Resource.Success(it))
        } ?: run {
            emit(Resource.Error(Throwable("공지사항을 가져오지 못했습니다")))
        }
    }.catch { e ->
        emit(Resource.Error(e))
    }.flowOn(dispatcher)


    /*
    override fun getNotice(): Flow<Resource<Home.Notice>> = flow {
        firebase.getNotice()?.let {
            emit(Resource.Success(Mapper.mapperToDomain(it)))
        } ?: run {
            emit(Resource.Error(Throwable("공지사항을 가져오지 못했습니다")))
        }
    }.catch { e ->
        emit(Resource.Error(e))
    }.flowOn(dispatcher)

     */
}