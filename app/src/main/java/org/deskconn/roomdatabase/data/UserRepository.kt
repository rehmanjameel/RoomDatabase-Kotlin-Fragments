package org.deskconn.roomdatabase.data

import androidx.lifecycle.LiveData
import org.deskconn.roomdatabase.User

//** Repository class abstracts  access to multiple data sources
class UserRepository(private val userDao: UserDao) {
    val readsAllData: LiveData<List<User>> = userDao.readAllData()

    suspend fun addUser(user: User){
        userDao.addUser(user)
    }

    suspend fun updateUser(user: User){
        userDao.updateUser(user)
    }

    suspend fun deleteUser(user: User) {
        userDao.deleteUser(user)
    }

    suspend fun deleteAllUsers() {
        userDao.deleteAllUsers()
    }
}