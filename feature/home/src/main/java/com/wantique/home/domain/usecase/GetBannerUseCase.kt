package com.wantique.home.domain.usecase

import com.wantique.base.state.Resource
import com.wantique.base.state.UiState
import com.wantique.home.domain.repository.HomeRepository
import javax.inject.Inject

class GetBannerUseCase @Inject constructor(private val homeRepository: HomeRepository) {
    suspend operator fun invoke() = homeRepository.getBanner().run {
        when(this) {
            is Resource.Success -> UiState.Success(data)
            is Resource.Error -> UiState.Error(error)
        }
    }
}