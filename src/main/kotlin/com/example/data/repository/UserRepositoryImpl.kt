package com.example.data.repository

import com.example.data.model.RoleModel
import com.example.data.model.UserModel
import com.example.data.model.tables.UserTable
import com.example.domain.repository.UserRepository
import com.example.plugins.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll

class UserRepositoryImpl : UserRepository {

    override suspend fun getUserByEmail(email: String): UserModel? = dbQuery {
        UserTable.selectAll()
            .where { UserTable.email eq email }
            .map { rowToUser(it) }
            .singleOrNull()
    }

    override suspend fun insertUser(userModel: UserModel) {
        dbQuery {
            UserTable.insert { table ->
                table[email] = userModel.email
                table[login] = userModel.login
                table[password] = userModel.password
                table[firstName] = userModel.firstName
                table[lastName] = userModel.lastName
                table[isActive] = userModel.isActive
                table[role] = RoleModel.setRoleInTable(userModel.role)
            }
        }
    }

    private fun rowToUser(row: ResultRow?): UserModel? {
        if (row == null) return null
        return UserModel(
            id = row[UserTable.id],
            email = row[UserTable.email],
            login = row[UserTable.login],
            password = row[UserTable.password],
            firstName = row[UserTable.firstName],
            lastName = row[UserTable.lastName],
            isActive = row[UserTable.isActive],
            role = RoleModel.getRoleModelFromString(row[UserTable.role]),
        )
    }

}