package com.example.domain

import com.example.domain.usecase.CardUseCase
import com.example.domain.usecase.UserUseCase
import org.koin.dsl.module

val domainModule = module {
    factory<UserUseCase> {
        UserUseCase(
            userRepository = get(),
            jwtService = get(),
        )
    }

    factory<CardUseCase> {
        CardUseCase(
            cardRepository = get(),
        )
    }
}