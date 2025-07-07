package com.nvshink.winterarc.ui.states

import com.nvshink.domain.user.model.UserModel

data class ProfileUiState(
    val currentUser: UserModel? = null,
)