package com.example.breakingbad.controller

import com.example.breakingbad.domain.entities.CharacterSearch
import com.example.breakingbad.event.*
import com.example.breakingbad.unittests.EventUnitTest
import io.mockk.*
import org.junit.Test

import org.junit.Before

class BreakingBadControllerTest : EventUnitTest(){

    private lateinit var controller: BreakingBadController

    @Before
    fun setup(){
        controller = BreakingBadController()
    }

    @Test
    fun `fetchCharacters publishes loading and initial profile request events`() {
        mockPublishEvent(Loading.event)
        mockPublishEvent(CharactersFetched.event)
        controller.fetchCharacters()
        verify{Loading.event.publishEvent()}
        verify{CharactersFetched.event.publishEvent()}
    }

    @Test
    fun `fetchCharacterById publishes loading and detail profile request events`() {
        mockPublishEvent(Loading.event)
        mockPublishEvent(CharactersClicked.event,EXPECTED_CHAR_ID)
        controller.fetchCharacterById(EXPECTED_CHAR_ID)
        verify{Loading.event.publishEvent()}
        verify{CharactersClicked.event.publishEvent(EXPECTED_CHAR_ID)}
    }

    @Test
    fun `characterMinimised publishes a minimize event`() {
        mockPublishEvent(CharacterDetailsMinimized.event)
        controller.characterMinimised()
        verify{CharacterDetailsMinimized.event.publishEvent()}
    }

    @Test
    fun `searchCharacters publishes loading and profile request events meeting search criteria`() {
        mockPublishEvent(Loading.event)
        mockPublishEvent(CharactersSearched.event)
        controller.searchCharacters(EXPECTED_SEASON,EXPECTED_NAME)
        verify{Loading.event.publishEvent()}
        verify{CharactersSearched.event.publishEvent(expectedCharacterSearch)}
    }

    companion object {
        private const val EXPECTED_CHAR_ID = 0
        private const val EXPECTED_SEASON = "1"
        private const val EXPECTED_NAME = "Walter White"
        private val expectedCharacterSearch = CharacterSearch(EXPECTED_SEASON, EXPECTED_NAME)
    }

}
