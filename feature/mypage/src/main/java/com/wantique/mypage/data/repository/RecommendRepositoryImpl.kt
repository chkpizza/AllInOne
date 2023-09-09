package com.wantique.mypage.data.repository

import com.wantique.base.state.Resource
import com.wantique.firebase.Firebase
import com.wantique.mypage.domain.repository.RecommendRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RecommendRepositoryImpl @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val firebase: Firebase
) : RecommendRepository {
    override fun registerRecommend(recommend: String): Flow<Resource<Boolean>> = flow {
        if(firebase.registerRecommend(recommend)) {
            emit(Resource.Success(true))
        } else {
            emit(Resource.Error(Throwable("게시글을 등록하지 못했습니다")))
        }
    }.catch { e ->
        emit(Resource.Error(e))
    }.flowOn(dispatcher)
}