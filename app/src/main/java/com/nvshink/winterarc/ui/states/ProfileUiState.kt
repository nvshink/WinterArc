package com.nvshink.winterarc.ui.states

import com.nvshink.winterarc.data.local.user.entity.User

data class ProfileUiState(
    val currentUser: User? = null,
)