package com.spaceexplorer.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.spaceexplorer.data.entity.User

@Dao
interface UserDao {
    @Insert
    suspend fun insert(user: User): Long

    @Query("SELECT * FROM users WHERE id = :id")
    suspend fun getUserById(id: Int): User?

    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    suspend fun getUserByUsername(username: String): User?
}
