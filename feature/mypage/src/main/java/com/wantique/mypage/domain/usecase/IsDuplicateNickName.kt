package com.wantique.mypage.domain.usecase

import com.wantique.base.state.Resource
import com.wantique.base.state.UiState
import com.wantique.mypage.domain.repository.EditRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class IsDuplicateNickName @Inject constructor(private val editRepository: EditRepository) {
    operator fun invoke(nickName: String) = editRepository.isDuplicateNickName(nickName)
        .map {
            when(it) {
                is Resource.Success -> UiState.Success(it.data)
                is Resource.Error -> UiState.Error(it.error)
            }
        }
}