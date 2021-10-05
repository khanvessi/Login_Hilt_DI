package com.example.logintask.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM users")
    fun getAllUsers(): Flow<List<User>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserUser(users: User): Long
//
//    @Query("SELECT * FROM users WHERE email = :userName AND number = :password")
//    suspend fun validateUser(userName: String?, password: Int?)

//    @Query("DELETE FROM users")
//    suspend fun deleteAllUsers()

    @Delete
    suspend fun deleteUser(user: User)

    @Update
    suspend fun updateUser(user: User)

}