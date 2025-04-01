package com.example.winterarc.data.repository

import com.example.winterarc.data.Datasource
import com.example.winterarc.data.model.User

class UserRepository {
    private val user: User = Datasource.loadUser()

    fun getUser(): User {
        return user
    }
}