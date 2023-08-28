package com.wantique.home.domain.usecase

import com.wantique.base.state.Resource
import com.wantique.base.state.UiState
import com.wantique.home.domain.repository.ProfessorDetailsRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetProfessorCurriculumUseCase @Inject constructor(private val professorDetailsRepository: ProfessorDetailsRepository) {
    operator fun invoke(professorId: String) = professorDetailsRepository.getProfessorCurriculum(professorId)
        .map {
            when(it) {
                is Resource.Success -> UiState.Success(it.data)
                is Resource.Error -> UiState.Error(it.error)
            }
        }
}