package com.wantique.mypage.domain.usecase

import com.wantique.base.state.Resource
import com.wantique.base.state.UiState
import com.wantique.firebase.model.UserDto
import com.wantique.mypage.domain.model.UserProfile
import com.wantique.mypage.domain.repository.MyPageRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetUserProfileUseCase @Inject constructor(private val myPageRepository: MyPageRepository) {
    operator fun invoke() = myPageRepository.getUserProfile()
        .map {
            when(it) {
                is Resource.Success -> UiState.Success(mapper(it.data))
                is Resource.Error -> UiState.Error(it.error)
            }
        }

    private fun mapper(dto: UserDto): UserProfile {
        return UserProfile(dto.nickName, dto.profileImageUrl)
    }
}