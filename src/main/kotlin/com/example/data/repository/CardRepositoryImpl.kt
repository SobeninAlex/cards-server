package com.example.data.repository

import com.example.data.model.CardModel
import com.example.data.model.tables.CardTable
import com.example.domain.repository.CardRepository
import com.example.plugins.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class CardRepositoryImpl : CardRepository {

    override suspend fun addCard(card: CardModel) {
        dbQuery {
            CardTable.insert { table ->
                table[ownerId] = card.ownerId
                table[title] = card.cardTitle
                table[description] = card.cardDescription
                table[createdAt] = card.createdAt
                table[isVerified] = card.isVerified
            }
        }
    }

    override suspend fun getAllCards(): List<CardModel> {
        return dbQuery {
            CardTable.selectAll()
                .mapNotNull { rowToCard(it) }
        }
    }

    override suspend fun updateCard(card: CardModel, owner: Int) {
        dbQuery {
            CardTable.update(
                where = {
                    CardTable.ownerId.eq(owner) and CardTable.id.eq(card.id)
                },
            ) { table ->
                table[ownerId] = card.ownerId
                table[title] = card.cardTitle
                table[description] = card.cardDescription
                table[createdAt] = card.createdAt
                table[isVerified] = card.isVerified
            }
        }
    }

    override suspend fun deleteCard(cardId: Int, owner: Int) {
        dbQuery {
            CardTable.deleteWhere {
                id.eq(cardId) and ownerId.eq(owner)
            }
        }
    }

    private fun rowToCard(row: ResultRow?): CardModel? {
        if (row == null) return null
        return CardModel(
            id = row[CardTable.id],
            ownerId = row[CardTable.ownerId],
            cardTitle = row[CardTable.title],
            cardDescription = row[CardTable.description],
            createdAt = row[CardTable.createdAt],
            isVerified = row[CardTable.isVerified],
        )
    }

}