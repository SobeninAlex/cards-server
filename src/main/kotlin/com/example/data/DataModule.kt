package com.example.data

import com.example.data.repository.CardRepositoryImpl
import com.example.data.repository.UserRepositoryImpl
import com.example.domain.repository.CardRepository
import com.example.domain.repository.UserRepository
import org.koin.dsl.module

val dataModule = module {
    single<UserRepository> {
        UserRepositoryImpl()
    }

    single<CardRepository> {
        CardRepositoryImpl()
    }
}