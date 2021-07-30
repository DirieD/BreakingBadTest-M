package com.example.breakingbad.repositry

import com.example.breakingbad.domain.entities.Character
import com.example.breakingbad.network.BreakingBadNetworkService
import com.example.breakingbad.persistence.CharacterDatabase

class CharactersRepository(private val networkService: BreakingBadNetworkService, private val database: CharacterDatabase) {
    suspend fun refreshCharactersAsync() : List<Character>{
        val characterProfiles = networkService.fetchCharactersAsync()
        database.breakingBadDao.insertAll(characterProfiles)
        return characterProfiles
    }

    suspend fun fetchCharactersAsync() : List<Character> {
        val localCharacterProfiles = database.breakingBadDao.getLocalCharactersAsync()
        return if (localCharacterProfiles.isEmpty()) refreshCharactersAsync() else localCharacterProfiles
    }

    suspend fun fetchCharacterByIdAsync(id : Int?) : Character {
        return database.breakingBadDao.getLocalCharacterAsync(id)
    }

    suspend fun fetchCharacterByNameAsync(name: String?) : List<Character> {
        return database.breakingBadDao.getLocalCharactersByNameAsync(name)
    }

    suspend fun fetchCharacterByAppearanceAsync(season: String?): List<Character> {
        val seasonArgs = createSeasonArgs(season)
        return database.breakingBadDao.getLocalCharactersByAppearanceAsync(seasonArgs[0],seasonArgs[1],seasonArgs[2])
    }

    suspend fun fetchCharacterByAppearanceAndNameAsync(season: String?, name: String?): List<Character> {
        val seasonArgs = createSeasonArgs(season)
        return database.breakingBadDao.getLocalCharacterByAppearanceAndNameAsync(seasonArgs[0],seasonArgs[1],seasonArgs[2],name)
    }

    private fun createSeasonArgs(season: String?) = listOf("[$season,%","%,$season,%","%,$season]")

}
