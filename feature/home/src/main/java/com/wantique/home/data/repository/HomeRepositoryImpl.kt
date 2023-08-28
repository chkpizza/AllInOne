package com.wantique.home.data.repository

import com.wantique.base.state.Resource
import com.wantique.firebase.Firebase
import com.wantique.home.data.mapper.Mapper
import com.wantique.home.domain.model.Home
import com.wantique.home.domain.repository.HomeRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(private val dispatcher: CoroutineDispatcher, private val firebase: Firebase) : HomeRepository {
    override suspend fun getBanner() = withContext(dispatcher) {
        try {
            firebase.getBanner()?.let {
                Resource.Success(Mapper.mapperToDomain(it))
            } ?: run {
                Resource.Error(Throwable("배너를 가져오지 못했습니다"))
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    override suspend fun getCategory() = withContext(dispatcher) {
        try {
            firebase.getCategory()?.let {
                Resource.Success(Mapper.mapperToDomain(it))
            } ?: run {
                Resource.Error(Throwable("카테고리 정보를 가져오지 못했습니다"))
            }
        } catch(e: Exception) {
            Resource.Error(e)
        }
    }

    override suspend fun getProfessors(): Resource<List<Home.Professor>> = withContext(dispatcher) {
        try {
            Resource.Success(Mapper.mapperToDomain(firebase.getProfessors()))
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    override suspend fun getYearlyExam(): Resource<Home.Exam> = withContext(dispatcher) {
        try {
            firebase.getYearlyExam()?.let {
                Resource.Success(Mapper.mapperToDomain(it))
            } ?: run {
                Resource.Error(Throwable("올해 시험 정보를 가져오지 못했습니다."))
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }
}