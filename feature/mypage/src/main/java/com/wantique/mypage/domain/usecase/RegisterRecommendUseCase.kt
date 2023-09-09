package com.wantique.mypage.domain.usecase

import com.wantique.base.state.Resource
import com.wantique.base.state.UiState
import com.wantique.mypage.domain.repository.RecommendRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RegisterRecommendUseCase @Inject constructor(private val recommendRepository: RecommendRepository) {
    operator fun invoke(recommend: String) = recommendRepository.registerRecommend(recommend)
        .map {
            when(it) {
                is Resource.Success -> UiState.Success(it.data)
                is Resource.Error -> UiState.Error(it.error)
            }
        }
}