package com.example.data.model

data class CardModel(
    val id: Int,
    val ownerId: Int,
    val cardTitle: String,
    val cardDescription: String,
    val createdAt: String,
    val isVerified: Boolean = false,
)
