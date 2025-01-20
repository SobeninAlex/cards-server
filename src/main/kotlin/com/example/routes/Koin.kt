package com.example.routes

import com.example.authentification.authModule
import com.example.data.dataModule
import com.example.domain.domainModule
import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureKoin() {
    install(Koin) {
        slf4jLogger()
        modules(
            authModule,
            dataModule,
            domainModule,
        )
    }
}