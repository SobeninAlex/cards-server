package com.example.plugins

import com.example.domain.usecase.CardUseCase
import com.example.domain.usecase.UserUseCase
import com.example.routes.cardRoute
import com.example.routes.userRoute
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting(
    userUseCase: UserUseCase,
    cardUseCase: CardUseCase
) {
    routing {
        userRoute(userUseCase = userUseCase)
        cardRoute(cardUseCase = cardUseCase)
    }
}
