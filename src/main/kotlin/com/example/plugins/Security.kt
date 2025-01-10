package com.example.plugins

import com.example.authentification.JwtService
import com.example.data.model.RoleModel
import com.example.data.model.UserModel
import com.example.data.repository.UserRepositoryImpl
import com.example.domain.usecase.UserUseCase
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import kotlinx.coroutines.runBlocking

fun Application.configureSecurity() {
    val jwtService = JwtService()
    val repository = UserRepositoryImpl()
    val userUseCase = UserUseCase(repository, jwtService)

    runBlocking {
        userUseCase.createUser(
            UserModel(
                id = 1,
                email = "test@example.com",
                login = "test-login",
                password = "test-password",
                firstName = "test-firstName",
                lastName = "test-lastName",
                isActive = true,
                role = RoleModel.Admin
            )
        )
    }

    authentication {
        jwt("jwt") {
            verifier(jwtService.getVerifier())
            realm = "Service server"
            validate {
                val payload = it.payload
                val email = payload.getClaim("email").asString()
                val user = userUseCase.getUserByEmail(email)
                user
            }
        }
    }
}
