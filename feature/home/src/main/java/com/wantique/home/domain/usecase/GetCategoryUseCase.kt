package com.wantique.home.domain.usecase

import com.wantique.base.state.Resource
import com.wantique.base.state.UiState
import com.wantique.firebase.model.CategoryDto
import com.wantique.home.domain.model.Home
import com.wantique.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCategoryUseCase @Inject constructor(private val homeRepository: HomeRepository) {
    operator fun invoke() = homeRepository.getCategory()
        .map {
            when(it) {
                is Resource.Success -> UiState.Success(mapper(it.data))
                is Resource.Error -> UiState.Error(it.error)
            }
        }

    private fun mapper(dto: CategoryDto): Home.Category {
        return Home.Category(dto.title, dto.item)
    }
}