package org.deskconn.roomdatabase

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

//** This user class will represent an entity **//
//** Represent table with in the database **//

@Parcelize
@Entity(tableName = "user_Table")   //Annotating the entity annotation and specify the table name init
class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val firstName: String,
    val lastName: String,
    val age: Int
): Parcelable