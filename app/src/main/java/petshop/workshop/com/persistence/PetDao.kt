package petshop.workshop.com.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single

@Dao
interface PetDao {

    @Query("SELECT * FROM $TABLE_NAME")
    fun getAllPets(): Single<List<Pet>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertDataToDatabase(vararg petsList: Pet)

}