package com.example.data.model

import com.example.utils.Role

enum class RoleModel {
    Admin, Moderator, Client;

    companion object {
        fun getRoleModelFromString(role: String): RoleModel = when (role) {
            Role.ADMIN -> Admin
            Role.MODERATOR -> Moderator
            Role.CLIENT -> Client
            else -> throw IllegalArgumentException("Unknown role $role")
        }

        fun setRoleInTable(role: RoleModel): String = when(role) {
            Client -> Role.CLIENT
            Admin -> Role.ADMIN
            Moderator -> Role.MODERATOR
        }
    }

}