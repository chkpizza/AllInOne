package com.wantique.home.data.repository

import android.util.Log
import com.wantique.base.state.Resource
import com.wantique.firebase.FireStore
import com.wantique.home.data.mapper.Mapper
import com.wantique.home.domain.model.Professors
import com.wantique.home.domain.repository.HomeRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(private val dispatcher: CoroutineDispatcher, private val fireStore: FireStore) : HomeRepository {
    override suspend fun getBanner() = withContext(dispatcher) {
        try {
            fireStore.getBanner()?.let {
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
            fireStore.getCategory()?.let {
                Resource.Success(Mapper.mapperToDomain(it))
            } ?: run {
                Log.d("categoryTest", "null")
                Resource.Error(Throwable("카테고리 정보를 가져오지 못했습니다"))
            }
        } catch(e: Exception) {
            Resource.Error(e)
        }
    }

    override suspend fun getProfessors(): Resource<List<Professors>> = withContext(dispatcher) {
        try {
            Resource.Success(Mapper.mapperToDomain(fireStore.getProfessors()))
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }
}