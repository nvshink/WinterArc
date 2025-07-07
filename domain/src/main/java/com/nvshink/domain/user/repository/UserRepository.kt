package com.nvshink.domain.user.repository

import com.nvshink.domain.user.model.UserModel

interface UserRepository {
    fun getUser(): UserModel
}