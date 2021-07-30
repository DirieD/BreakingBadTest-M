package com.example.breakingbad.domain

import com.example.breakingbad.domain.entities.CharacterSearch
import com.example.breakingbad.domain.entities.Character
import com.example.breakingbad.presenter.BreakingBadPresenter.Companion.ALL_SEASONS
import com.example.breakingbad.repositry.CharactersRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*

class BreakingBadTest {

    private val characterRepository : CharactersRepository = mockk()

    private val breakingBad = BreakingBad(characterRepository)

    private val allCharactersMock : List<Character> = mockk()
    private val charactersBySeasonMock : List<Character> = mockk()
    private val charactersByNameMock : List<Character> = mockk()
    private val characterBySeasonAndNameMock : List<Character> = mockk()
    private val singleCharacterMock : Character = mockk()

    @Test
    fun `fetch characters fetches all character profiles`() {
        coEvery { characterRepository.fetchCharactersAsync() } returns allCharactersMock
        val allCharacterProfiles = runBlocking { breakingBad.fetchAllCharacters() }
        assertEquals(allCharactersMock, allCharacterProfiles)
        coVerify { characterRepository.fetchCharactersAsync() }
    }

    @Test
    fun `fetch characters by id will fetch a character by id`() {
        coEvery { characterRepository.fetchCharacterByIdAsync(CHAR_ID) } returns singleCharacterMock
        val singleCharacterProfile = runBlocking { breakingBad.fetchCharacterById(CHAR_ID) }
        assertEquals(singleCharacterMock, singleCharacterProfile)
        coVerify { characterRepository.fetchCharacterByIdAsync(CHAR_ID) }
    }

    @Test
    fun `fetch characters by appearance and name fetch all characters by appearances and name`(){
        coEvery { characterRepository.fetchCharacterByAppearanceAndNameAsync(characterSearchOnly.season, characterSearchOnly.name) } returns characterBySeasonAndNameMock
        val characterProfiles = runBlocking { breakingBad.fetchCharacterByAppearanceAndName(characterSearchOnly) }
        assertEquals(characterBySeasonAndNameMock, characterProfiles)
        coVerify { characterRepository.fetchCharacterByAppearanceAndNameAsync(characterSearchOnly.season, characterSearchOnly.name) }
    }

    @Test
    fun `fetch characters by name will fetch characters by only name when appearances is all seasons`(){
        coEvery { characterRepository.fetchCharacterByNameAsync(characterSearchName.name) } returns charactersByNameMock
        val characterProfiles = runBlocking { breakingBad.fetchCharacterByAppearanceAndName(characterSearchName) }
        assertEquals(charactersByNameMock, characterProfiles)
        coVerify { characterRepository.fetchCharacterByNameAsync(characterSearchName.name) }
    }

    @Test
    fun `fetch characters by appearance will fetch characters only appearance when name is blank`(){
        coEvery { characterRepository.fetchCharacterByAppearanceAsync(characterSearchSeason.season) } returns charactersBySeasonMock
        val characterProfiles = runBlocking { breakingBad.fetchCharacterByAppearanceAndName(characterSearchSeason) }
        assertEquals(charactersBySeasonMock, characterProfiles)
        coVerify { characterRepository.fetchCharacterByAppearanceAsync(characterSearchSeason.season) }
    }

    @Test
    fun `fetch characters by appearance and name will fetch all characters only when appearance is all seasons and when name is blank`(){
        coEvery { characterRepository.fetchCharactersAsync() } returns allCharactersMock
        val characterProfiles = runBlocking { breakingBad.fetchCharacterByAppearanceAndName(characterSearchAll) }
        assertEquals(allCharactersMock, characterProfiles)
        coVerify { characterRepository.fetchCharactersAsync() }
    }

    companion object {
        private const val CHAR_ID = 0
        private const val APPEARANCE = "1"
        private const val NAME = "Walter White"
        private const val BLANK = ""

        private val characterSearchAll = CharacterSearch(ALL_SEASONS, BLANK)
        private val characterSearchSeason = CharacterSearch(APPEARANCE, BLANK)
        private val characterSearchName = CharacterSearch(ALL_SEASONS, NAME)
        private val characterSearchOnly = CharacterSearch(APPEARANCE, NAME)
    }
}
