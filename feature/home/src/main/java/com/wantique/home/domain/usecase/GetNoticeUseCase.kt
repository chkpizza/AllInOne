package com.wantique.home.domain.usecase

import com.wantique.base.state.Resource
import com.wantique.base.state.UiState
import com.wantique.firebase.model.NoticeDto
import com.wantique.home.domain.model.Home
import com.wantique.home.domain.model.NoticeItem
import com.wantique.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetNoticeUseCase @Inject constructor(private val homeRepository: HomeRepository) {
    operator fun invoke() = homeRepository.getNotice()
        .map {
            when(it) {
                is Resource.Success -> UiState.Success(mapper(it.data))
                is Resource.Error -> UiState.Error(it.error)
            }
        }

    private fun mapper(dto: NoticeDto): Home.Notice {
        mutableListOf<NoticeItem>().apply {
            dto.notice.forEach {
                add(NoticeItem(it.title, it.body, it.url, it.name, it.uploadDate, it.documentId))
            }

            return Home.Notice(dto.header.header, this)
        }
    }
}