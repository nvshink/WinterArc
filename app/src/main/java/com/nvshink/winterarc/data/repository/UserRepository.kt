package com.nvshink.winterarc.data.repository

import com.nvshink.winterarc.data.Datasource
import com.nvshink.winterarc.data.model.User

class UserRepository {
    private val user: User = Datasource.loadUser()

    fun getUser(): User {
        return user
    }
}