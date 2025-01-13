package com.example.plugins

import com.example.domain.usecase.UserUseCase
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

fun Application.configureSecurity(userUseCase: UserUseCase) {
    authentication {
        jwt("jwt") {
            verifier(userUseCase.getJWTVerifier())
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
