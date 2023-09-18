package com.wantique.home.domain.usecase

import com.wantique.base.state.Resource
import com.wantique.base.state.UiState
import com.wantique.firebase.model.ProfessorPreviewDto
import com.wantique.home.domain.model.Home
import com.wantique.home.domain.model.ProfessorItem
import com.wantique.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetProfessorsUseCase @Inject constructor(private val homeRepository: HomeRepository) {
    operator fun invoke() = homeRepository.getProfessors()
        .map {
            when(it) {
                is Resource.Success -> UiState.Success(mapper(it.data))
                is Resource.Error -> UiState.Error(it.error)
            }
        }

    private fun mapper(dto: List<ProfessorPreviewDto>): List<Home.Professor> {
        return dto.map {
            val professorItemList = mutableListOf<ProfessorItem>()
            it.item.forEach { item ->
                professorItemList.add(ProfessorItem(item.id, item.name, item.belong, item.update))
            }
            Home.Professor(professorItemList)
        }
    }
}