package com.wantique.auth.domain.usecase

import com.wantique.auth.domain.repository.AuthRepository
import com.wantique.base.state.Resource
import com.wantique.base.state.UiState
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(private val authRepository: AuthRepository) {
    operator fun invoke(profileImageUri: String, nickName: String) = authRepository.registerUser(profileImageUri, nickName)
        .map {
            when(it) {
                is Resource.Success -> UiState.Success(it.data)
                is Resource.Error -> UiState.Error(it.error)
            }
        }
}