package com.example.plugins

import com.example.domain.usecase.CardUseCase
import com.example.domain.usecase.UserUseCase
import com.example.routes.cardRoute
import com.example.routes.userRoute
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    val userUseCase by inject<UserUseCase>()
    val cardUseCase by inject<CardUseCase>()

    routing {
        userRoute(userUseCase = userUseCase)
        cardRoute(cardUseCase = cardUseCase)
    }
}
