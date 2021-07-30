package com.example.breakingbad.repositry

import com.example.breakingbad.domain.entities.Character
import com.example.breakingbad.network.BreakingBadNetworkService
import com.example.breakingbad.persistence.CharacterDatabase
import com.example.breakingbad.unittests.EventUnitTest
import io.mockk.*
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test

class CharactersRepositoryTest : EventUnitTest() {

    private val networkService: BreakingBadNetworkService = mockk()

    private val databaseService: CharacterDatabase = mockk()

    private val characterRepositry = CharactersRepository(networkService, databaseService)

    private val networkCharactersMock: List<Character> = mockk()
    private val localCharactersMock: List<Character> = mockk()
    private val localCharacterMock: Character = mockk()

    @Test
    fun `refresh characters should make a network call, insert data from the database and return the character Profiles`() {
        coEvery { networkService.fetchCharactersAsync() } returns networkCharactersMock
        coEvery { databaseService.breakingBadDao.insertAll(networkCharactersMock) } just runs
        val characterProfiles = runBlocking { characterRepositry.refreshCharactersAsync() }
        assertEquals(networkCharactersMock, characterProfiles)
        coVerify { networkService.fetchCharactersAsync() }
        coVerify { databaseService.breakingBadDao.insertAll(networkCharactersMock) }
    }

    @Test
    fun `fetch characters profiles will attempt to fetch characters from database, but default to refresh characters over network`() {
        coEvery { networkService.fetchCharactersAsync() } returns networkCharactersMock
        coEvery { databaseService.breakingBadDao.getLocalCharactersAsync() } returns emptyList()
        coEvery { databaseService.breakingBadDao.insertAll(networkCharactersMock) } just runs
        val networkCharacterProfiles = runBlocking { characterRepositry.fetchCharactersAsync() }
        assertEquals(networkCharactersMock, networkCharacterProfiles)
        coVerify { databaseService.breakingBadDao.getLocalCharactersAsync() }
        coVerify { networkService.fetchCharactersAsync() }
        coVerify { databaseService.breakingBadDao.insertAll(networkCharactersMock) }
    }

    @Test
    fun `fetch characters profiles will fetch present characters from database`(){
        coEvery { databaseService.breakingBadDao.getLocalCharactersAsync() } returns localCharactersMock
        every { localCharactersMock.isEmpty() } returns false
        val localCharacterProfiles = runBlocking { characterRepositry.fetchCharactersAsync() }
        assertEquals(localCharactersMock, localCharacterProfiles)
        coVerify { databaseService.breakingBadDao.getLocalCharactersAsync() }
        coVerify(exactly = 0) { networkService.fetchCharactersAsync() }
    }

    @Test
    fun `fetch characters by id will fetch a character from the database by id`() {
        coEvery { databaseService.breakingBadDao.getLocalCharacterAsync(CHAR_ID) } returns localCharacterMock
        val localCharacterProfile = runBlocking { characterRepositry.fetchCharacterByIdAsync(CHAR_ID) }
        assertEquals(localCharacterMock, localCharacterProfile)
        coVerify { databaseService.breakingBadDao.getLocalCharacterAsync(CHAR_ID) }
    }

    @Test
    fun `fetch character by name name will fetch a character from the database by name`() {
        coEvery { databaseService.breakingBadDao.getLocalCharactersByNameAsync(NAME)} returns localCharactersMock
        val localCharacterProfiles = runBlocking { characterRepositry.fetchCharacterByNameAsync(NAME) }
        assertEquals(localCharactersMock,localCharacterProfiles)
        coVerify { databaseService.breakingBadDao.getLocalCharactersByNameAsync(NAME) }
    }

    @Test
    fun `fetch character by appearance will fetch a character from the database by appearance`() {
        coEvery { databaseService.breakingBadDao.getLocalCharactersByAppearanceAsync(SEASON_ARG_1, SEASON_ARG_2, SEASON_ARG_3) } returns localCharactersMock
        val localCharacterProfile = runBlocking { characterRepositry.fetchCharacterByAppearanceAsync(SEASON) }
        assertEquals(localCharactersMock, localCharacterProfile)
        coEvery { databaseService.breakingBadDao.getLocalCharactersByAppearanceAsync(SEASON_ARG_1, SEASON_ARG_2, SEASON_ARG_3) }
    }

    @Test
    fun `fetch character by appearance and name will fetch a character from the database by appearance and name`() {
        coEvery { databaseService.breakingBadDao.getLocalCharacterByAppearanceAndNameAsync(SEASON_ARG_1, SEASON_ARG_2, SEASON_ARG_3, NAME) } returns localCharactersMock
        val localCharacterProfile = runBlocking { characterRepositry.fetchCharacterByAppearanceAndNameAsync(SEASON, NAME)}
        assertEquals(localCharactersMock, localCharacterProfile)
        coEvery { databaseService.breakingBadDao.getLocalCharacterByAppearanceAndNameAsync(SEASON_ARG_1, SEASON_ARG_2, SEASON_ARG_3, NAME) }
    }

    companion object {
        private const val CHAR_ID = 0
        private const val NAME = "Walter White"
        private const val SEASON = "1"
        private const val SEASON_ARG_1 = "[1,%"
        private const val SEASON_ARG_2 = "%,1,%"
        private const val SEASON_ARG_3 = "%,1]"
    }
}
