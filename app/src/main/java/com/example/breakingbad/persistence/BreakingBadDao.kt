package com.example.breakingbad.persistence

import androidx.room.*
import com.example.breakingbad.domain.entities.Character

@Dao
interface BreakingBadDao {
    @Query("select * from Character")
    suspend fun getLocalCharactersAsync(): List<Character>

    @Query("select * from Character where char_id = :id")
    suspend fun getLocalCharacterAsync(id: Int?): Character

    @Query("select * from Character where name like :name")
    suspend fun getLocalCharactersByNameAsync(name: String?): List<Character>

    @Query("select * from Character where appearance like :seasonArg1 or appearance like :seasonArg2 or appearance like :seasonArg3")
    suspend fun getLocalCharactersByAppearanceAsync(seasonArg1: String, seasonArg2: String, seasonArg3: String) : List<Character>

    //This would search commutatively but would require refactoring previous events to take advantage of
    @Query("select * from Character where name like :name and (appearance like :seasonArg1 or  appearance like :seasonArg2 or appearance like :seasonArg3)")
    suspend fun getLocalCharacterByAppearanceAndNameAsync(seasonArg1: String, seasonArg2: String, seasonArg3: String, name: String?) : List<Character>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(characters: List<Character>)
}

@Database(entities = [Character::class] , version = 4, exportSchema = false)
@TypeConverters(StringListTypeConverter::class)
abstract class CharacterDatabase: RoomDatabase() {
    abstract val breakingBadDao: BreakingBadDao
}
