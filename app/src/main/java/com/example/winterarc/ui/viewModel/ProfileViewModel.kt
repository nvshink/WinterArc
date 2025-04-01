package com.example.winterarc.ui.viewModel

import androidx.lifecycle.ViewModel
import com.example.winterarc.data.model.User
import com.example.winterarc.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class ProfileViewModel : ViewModel() {
    private val userRepository = UserRepository()

    private val _uiState = MutableStateFlow(
        ProfileUiState (
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

data class ProfileUiState(
    val currentUser: User? = null,
)
