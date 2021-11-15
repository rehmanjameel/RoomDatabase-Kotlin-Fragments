package org.deskconn.roomdatabase.data

import androidx.lifecycle.LiveData
import androidx.room.*
import org.deskconn.roomdatabase.User

//** Contains the methods used for accessing the database **//

@Dao    //** Annotating the interface with Dao **//
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)  //** It will ignore the new user with same name **//
    suspend fun addUser(user: User)     //suspend keyword is used for coroutines

    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("Delete from user_Table")
    suspend fun deleteAllUsers()

    @Query("Select * from user_Table order by id ASC") //Ascending order annotating with the query that will fetch the data
    fun readAllData(): LiveData<List<User>>

}