package com.example.noorapp.authentication.domain


import com.example.noorapp.authentication.utils.AuthServiceResult
import kotlinx.coroutines.flow.Flow


interface AuthRepository {
    suspend fun signIn(email: String, password: String): Flow<AuthServiceResult<Unit>>
    suspend fun signUp(name: String, email: String, password: String,): Flow<AuthServiceResult<Unit>>
    suspend fun forgetPassword(email: String): Flow<AuthServiceResult<Unit>>
    suspend fun getUserDetail(uid: String): Flow<String>
    suspend fun resetPassword(oldPassword: String, newPassword: String): Flow<AuthServiceResult<Unit>>
    suspend fun updateProfile(name: String): Flow<AuthServiceResult<Unit>>
}

