package com.example.data.model

import io.ktor.server.auth.*


data class UserModel(
    val id: Int,
    val email: String,
    val login: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val isActive: Boolean = false,
    val role: RoleModel,
) : Principal

enum class RoleModel {
    Admin, Moderator, Client;

    companion object {
        fun getRoleModelFromString(role: String): RoleModel = when (role) {
            "admin" -> Admin
            "moderator" -> Moderator
            "client" -> Client
            else -> throw IllegalArgumentException("Unknown role $role")
        }

        fun setRoleInTable(role: RoleModel): String = when(role) {
            Client -> "client"
            Admin -> "admin"
            Moderator -> "moderator"
        }
    }

}
