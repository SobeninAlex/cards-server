package com.example.data.model.request

import kotlinx.serialization.Serializable

@Serializable
data class AddCardRequest(
    val id: Int? = null,
    val cardTitle: String,
    val cardDescription: String,
    val createdAt: String,
    val isVerified: Boolean = false,
)
