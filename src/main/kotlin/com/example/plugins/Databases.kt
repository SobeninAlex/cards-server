package com.example.plugins

import com.example.data.model.tables.CardTable
import com.example.data.model.tables.UserTable
import com.typesafe.config.ConfigFactory
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.*
import io.ktor.server.config.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {

    private val appConfig = HoconApplicationConfig(ConfigFactory.load())
    private val dbUrl = System.getenv("DB_URL")
    private val dbUser = System.getenv("DB_USER")
    private val dbPassword = System.getenv("DB_PASSWORD")

    fun Application.initializationDatabase() {
        Database.connect(getHikariDataSource())

        transaction {
            SchemaUtils.create(
                UserTable,
                CardTable,
            )
        }
    }

    private fun getHikariDataSource(): HikariDataSource {
        println("DB_URL: $dbUrl")
        println("DB_USER: $dbUser")

        val config = HikariConfig()
        config.apply {
            driverClassName = "org.postgresql.Driver"
            jdbcUrl = dbUrl
            username = dbUser
            password = dbPassword
            maximumPoolSize = 3
            isAutoCommit = false
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        }
        config.validate()
        return HikariDataSource(config)
    }

    suspend fun <T> dbQuery(block: () -> T): T {
        return newSuspendedTransaction(Dispatchers.IO) { block() }
    }

}