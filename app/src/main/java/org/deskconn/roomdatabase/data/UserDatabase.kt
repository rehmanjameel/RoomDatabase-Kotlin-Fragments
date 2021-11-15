package org.deskconn.roomdatabase.data

import android.content.Context
import android.provider.CalendarContract
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.deskconn.roomdatabase.User
import java.security.AccessControlContext

@Database(entities = [User::class], version = 1, exportSchema = false) //exportSchema default value is true
abstract class UserDatabase: RoomDatabase() {

    abstract fun userDao(): UserDao //Abstract function which will return the UserDao

    companion object{   // everything in the companion object is visible to other classes

        //Volatile means rights to this field are immediately made visible to other threads
        @Volatile
        private var INSTANCE: UserDatabase? = null  //Make the DB singleton class bcz user DB only have one instance of its class

        fun getDataBase(context: Context): UserDatabase{
            val tempInstance = INSTANCE     // Creating the new instance if instance occurs or present
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "user_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}