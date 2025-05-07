package com.nvshink.winterarc.ui.viewModels

import androidx.lifecycle.ViewModel
import com.nvshink.winterarc.data.model.User
import com.nvshink.winterarc.data.repository.UserRepository
import com.nvshink.winterarc.ui.states.ProfileUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class ProfileViewModel : ViewModel() {
    private val userRepository = UserRepository()

    private val _uiState = MutableStateFlow(
        ProfileUiState(
            currentUser = userRepository.getUser()
        )
    )
    val uiState: StateFlow<ProfileUiState> = _uiState

    fun updateProfileState(user: User) {
        _uiState.update {
            it.copy(
                currentUser = user
            )
        }
    }



}

