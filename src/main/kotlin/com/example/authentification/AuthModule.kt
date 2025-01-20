package com.example.authentification

import org.koin.dsl.module

val authModule = module {
    factory<JwtService> {
        JwtService()
    }
}