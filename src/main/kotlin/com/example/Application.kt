package com.example

import com.example.plugins.DatabaseFactory.initializationDatabase
import com.example.plugins.configureMonitoring
import com.example.plugins.configureRouting
import com.example.plugins.configureSecurity
import com.example.plugins.configureSerialization
import com.example.routes.configureKoin
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
    configureKoin()
    initializationDatabase()
    configureMonitoring()
    configureSerialization()
    configureSecurity()
    configureRouting()
}
