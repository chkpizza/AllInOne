package com.wantique.home.domain.usecase

import com.wantique.base.state.Resource
import com.wantique.base.state.UiState
import com.wantique.firebase.model.NoticeItemDto
import com.wantique.home.domain.model.NoticeItem
import com.wantique.home.domain.repository.NoticeRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllNoticeUseCase @Inject constructor(private val noticeRepository: NoticeRepository) {
    operator fun invoke() = noticeRepository.getAllNotice()
        .map {
            when(it) {
                is Resource.Success -> UiState.Success(mapper(it.data))
                is Resource.Error -> UiState.Error(it.error)
            }
        }

    private fun mapper(dto: List<NoticeItemDto>): List<NoticeItem> {
        return dto.map {
            NoticeItem(it.title, it.body, it.url, it.name, it.url, it.documentId)
        }
    }
}