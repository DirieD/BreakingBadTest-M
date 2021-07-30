package com.example.breakingbad.domain

import com.example.breakingbad.domain.entities.CharacterSearch
import com.example.breakingbad.domain.entities.Character
import com.example.breakingbad.presenter.BreakingBadPresenter.Companion.ALL_SEASONS
import com.example.breakingbad.repositry.CharactersRepository

class BreakingBad(private val charactersRepository: CharactersRepository) {

    suspend fun fetchAllCharacters(cOut : Any? = null) : List<Character>{
        return charactersRepository.fetchCharactersAsync()
    }

    suspend fun fetchCharacterById(id : Int?) : Character {
        return charactersRepository.fetchCharacterByIdAsync(id)
    }

    //Performs a commutative search on both season appearance and name to query the data
    suspend fun fetchCharacterByAppearanceAndName(characterSearchPair : CharacterSearch?) : List<Character> {
        val season = characterSearchPair?.season
        val name = characterSearchPair?.name
        return if (season == ALL_SEASONS && name == "") charactersRepository.fetchCharactersAsync()
        else if (season == ALL_SEASONS) charactersRepository.fetchCharacterByNameAsync(name)
        else if (name == "") charactersRepository.fetchCharacterByAppearanceAsync(season)
        else charactersRepository.fetchCharacterByAppearanceAndNameAsync(season, name)
    }

}
