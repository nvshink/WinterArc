package com.nvshink.data.local.user.repository

import com.nvshink.data.local.user.entity.User
import com.nvshink.domain.user.model.UserModel
import com.nvshink.domain.user.repository.UserRepository

class UserRepositoryImpl : UserRepository {
    override fun getUser(): UserModel {
        return UserModel(username = User.username)
    }
}