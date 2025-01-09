package com.example.data.model.tables

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object CardTable : Table("cards") {
    val id: Column<Int> = integer("card_id").autoIncrement()
    val owner: Column<Int> = integer("card_owner").references(UserTable.id)
    val title: Column<String> = varchar("title", 50)
    val description: Column<String> = varchar("description", 2000)
    val createdAt: Column<String> = varchar("created_at", 50)

    override val primaryKey: PrimaryKey = PrimaryKey(id)
}