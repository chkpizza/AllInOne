package com.wantique.home.domain.usecase

import com.wantique.base.state.Resource
import com.wantique.base.state.UiState
import com.wantique.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetBannerUseCase @Inject constructor(private val homeRepository: HomeRepository) {
    operator fun invoke() = homeRepository.getBanner()
        .map {
            when(it) {
                is Resource.Success -> UiState.Success(it.data)
                is Resource.Error -> UiState.Error(it.error)
            }
        }
}