package com.example.domain.repository

import com.example.data.model.CardModel

interface CardRepository {

    suspend fun addCard(card: CardModel)

    suspend fun getAllCards(): List<CardModel>

    suspend fun updateCard(card: CardModel, owner: Int)

    suspend fun deleteCard(cardId: Int, owner: Int): Int

}