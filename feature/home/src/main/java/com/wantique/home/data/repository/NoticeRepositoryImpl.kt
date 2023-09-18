package com.wantique.home.data.repository

import com.wantique.base.state.Resource
import com.wantique.firebase.Firebase
import com.wantique.firebase.model.NoticeItemDto
import com.wantique.home.domain.repository.NoticeRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class NoticeRepositoryImpl @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val firebase: Firebase
) : NoticeRepository {
    override fun getNotice(id: String) = flow<Resource<NoticeItemDto>> {
        firebase.getNoticeById(id)?.let {
            emit(Resource.Success(it))
        } ?: run {
            emit(Resource.Error(Throwable("공지사항을 불러오지 못했습니다")))
        }
    }.catch { e ->
        emit(Resource.Error(e))
    }.flowOn(dispatcher)
    /*
    override fun getNotice(id: String) = flow<Resource<NoticeItem>> {
        firebase.getNoticeById(id)?.let {
            emit(Resource.Success(Mapper.mapperToDomain(it)))
        } ?: run {
            emit(Resource.Error(Throwable("공지사항을 불러오지 못했습니다.")))
        }
    }.catch { e ->
        emit(Resource.Error(e))
    }.flowOn(dispatcher)

     */

    override fun getAllNotice() = flow<Resource<List<NoticeItemDto>>> {
        emit(Resource.Success(firebase.getAllNotice()))
    }.catch { e ->
        emit(Resource.Error(e))
    }.flowOn(dispatcher)
}