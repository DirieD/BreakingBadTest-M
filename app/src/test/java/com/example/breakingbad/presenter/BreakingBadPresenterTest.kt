package com.example.breakingbad.presenter

import com.example.breakingbad.domain.entities.Character
import com.example.breakingbad.presenter.BreakingBadPresenter.Companion.APPEARANCES_PREFIX
import com.example.breakingbad.presenter.BreakingBadPresenter.Companion.OCCUPATIONS_PREFIX
import com.example.breakingbad.viewmodel.CharacterDetailViewModel
import com.example.breakingbad.viewmodel.CharacterViewModel
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class BreakingBadPresenterTest {

    private lateinit var presenter: BreakingBadPresenter

    @Before
    fun setup(){
        presenter = BreakingBadPresenter()
    }

    @Test
    fun `map character to viewmodel correctly maps characters to viewModels`() {
        assertEquals(expectedCharacterProfileViewModels, presenter.mapCharacterToViewModel(expectedCharacterProfiles))
    }

    @Test
    fun `map character to detailed viewmodel maps a characters to the correct detailedViewModel`(){
        assertEquals(expectedCharacterDetailViewModel, presenter.mapCharacterDetailToViewModel(expectedCharacterProfileB))
    }

    companion object {
        private const val CHAR_ID_A = 0
        private const val NAME_A = "Walter White"
        private val occupations_A = listOf("A1","A2","A3")
        private const val IMG_A = "ImageA"
        private const val STATUS_A = "Presumed Dead"
        private const val NICKNAME_A = "Heisenberg"
        private val appearances_A = listOf(1,2,3,4,5)

        private const val CHAR_ID_B = 1
        private const val NAME_B = "Jessie Pinkman"
        private val occupations_B = listOf("B1","B2","B3")
        private const val EXPECTED_OCCUPATIONS = "${OCCUPATIONS_PREFIX}B1\nB2\nB3"
        private const val IMG_B = "ImageB"
        private const val STATUS_B = "Alive"
        private const val NICKNAME_B = "Cap n Cook"
        private val appearances_B = listOf(1,2,4,5)
        private const val EXPECTED_APPEARANCES = "${APPEARANCES_PREFIX}1, 2, 4, 5"

        private val expectedCharacterProfileA = Character(CHAR_ID_A, NAME_A, occupations_A, IMG_A, STATUS_A, NICKNAME_A, appearances_A)
        private val expectedCharacterProfileB = Character(CHAR_ID_B, NAME_B, occupations_B, IMG_B, STATUS_B, NICKNAME_B, appearances_B)

        private val expectedCharacterProfiles = listOf(expectedCharacterProfileA, expectedCharacterProfileB)

        private val expectedCharacterProfileViewModelA = CharacterViewModel(CHAR_ID_A,NAME_A, IMG_A,appearances_A)
        private val expectedCharacterProfileViewModelB = CharacterViewModel(CHAR_ID_B,NAME_B, IMG_B,appearances_B)

        private val expectedCharacterProfileViewModels = listOf(expectedCharacterProfileViewModelA, expectedCharacterProfileViewModelB)

        private val expectedCharacterDetailViewModel = CharacterDetailViewModel(CHAR_ID_B,NAME_B,EXPECTED_OCCUPATIONS,IMG_B,STATUS_B,NICKNAME_B,EXPECTED_APPEARANCES)
    }
}
