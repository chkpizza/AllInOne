package com.wantique.home.domain.usecase

import com.wantique.base.state.Resource
import com.wantique.base.state.UiState
import com.wantique.firebase.model.BannerDto
import com.wantique.home.domain.model.BannerItem
import com.wantique.home.domain.model.Home
import com.wantique.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetBannerUseCase @Inject constructor(private val homeRepository: HomeRepository) {
    operator fun invoke() = homeRepository.getBanner()
        .map {
            when(it) {
                is Resource.Success -> UiState.Success(mapper(it.data))
                is Resource.Error -> UiState.Error(it.error)
            }
        }

    private fun mapper(dto: BannerDto): Home.Banner {
        val banners = dto.item.map {
            BannerItem(it.id, it.url)
        }

        return Home.Banner(dto.notice, banners)
    }
}