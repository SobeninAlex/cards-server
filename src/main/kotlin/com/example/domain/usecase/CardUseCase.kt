package com.example.domain.usecase

import com.example.data.model.CardModel
import com.example.domain.repository.CardRepository

class CardUseCase(
    private val cardRepository: CardRepository
) {

    suspend fun addCard(card: CardModel) {
        cardRepository.addCard(card = card)
    }

    suspend fun getAllCards(): List<CardModel> {
        return cardRepository.getAllCards()
    }

    suspend fun updateCard(card: CardModel, owner: Int) {
        cardRepository.updateCard(card = card, owner = owner)
    }

    suspend fun deleteCard(cardId: Int, owner: Int): Int {
        return cardRepository.deleteCard(cardId = cardId, owner = owner)
    }

}