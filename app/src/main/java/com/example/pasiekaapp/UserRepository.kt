package com.example.pasiekaapp


import kotlinx.coroutines.flow.Flow
interface UserRepository {
    suspend fun createUser(fullName: String, email: String, password: String): Flow<User>

}