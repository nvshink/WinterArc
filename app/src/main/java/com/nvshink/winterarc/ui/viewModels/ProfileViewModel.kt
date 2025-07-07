package com.nvshink.winterarc.ui.viewModels

import androidx.lifecycle.ViewModel
import com.nvshink.data.local.user.repository.UserRepositoryImpl
import com.nvshink.domain.user.model.UserModel
import com.nvshink.winterarc.ui.states.ProfileUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class ProfileViewModel : ViewModel() {
    private val userRepository = UserRepositoryImpl()

    private val _uiState = MutableStateFlow(
        ProfileUiState(
            currentUser = userRepository.getUser()
        )
    )
    val uiState: StateFlow<ProfileUiState> = _uiState

    fun updateProfileState(user: UserModel) {
        _uiState.update {
            it.copy(
                currentUser = user
            )
        }
    }



}

