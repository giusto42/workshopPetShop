package petshop.workshop.com.persistence

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [(Pet::class)], version = 1)
abstract class AppDataBase: RoomDatabase() {
    abstract fun databasePetDao() : PetDao
}

const val TABLE_NAME = "pet"