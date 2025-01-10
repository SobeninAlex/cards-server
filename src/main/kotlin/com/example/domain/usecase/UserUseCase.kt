package com.example.domain.usecase

import com.example.authentification.JwtService
import com.example.data.model.UserModel
import com.example.domain.repository.UserRepository

class UserUseCase(
    private val userRepository: UserRepository,
    private val jwtService: JwtService
) {

    suspend fun createUser(userModel: UserModel) {
        userRepository.insertUser(userModel)
    }

    suspend fun getUserByEmail(email: String): UserModel? {
        return userRepository.getUserByEmail(email)
    }

    fun generateToken(user: UserModel): String {
        return jwtService.generateToken(user)
    }

}