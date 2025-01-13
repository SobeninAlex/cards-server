package com.example

import com.example.authentification.JwtService
import com.example.data.repository.CardRepositoryImpl
import com.example.data.repository.UserRepositoryImpl
import com.example.domain.usecase.CardUseCase
import com.example.domain.usecase.UserUseCase
import com.example.plugins.DatabaseFactory.initializationDatabase
import com.example.plugins.configureMonitoring
import com.example.plugins.configureRouting
import com.example.plugins.configureSecurity
import com.example.plugins.configureSerialization
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(
        factory = Netty,
        port = 8080,
        host = "0.0.0.0",
        module = Application::module
    ).start(wait = true)
}

fun Application.module() {

    val jwtService = JwtService()
    val userRepository = UserRepositoryImpl()
    val cardRepository = CardRepositoryImpl()
    val userUseCase = UserUseCase(userRepository, jwtService)
    val cardUseCase = CardUseCase(cardRepository)

    initializationDatabase()
    configureMonitoring()
    configureSerialization()
    configureSecurity(
        userUseCase = userUseCase
    )
    configureRouting(
        userUseCase = userUseCase,
        cardUseCase = cardUseCase
    )
}
