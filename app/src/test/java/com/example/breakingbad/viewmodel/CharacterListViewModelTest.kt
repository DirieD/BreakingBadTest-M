package com.example.breakingbad.viewmodel

import com.example.breakingbad.unittests.EventUnitTest
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CharacterListViewModelTest : EventUnitTest(){

    private lateinit var viewModel: CharacterListViewModel

            private val charters = mockk<List<CharacterViewModel>>()
    private val charter = mockk<CharacterDetailViewModel>()

    @Before
    fun setup(){
        viewModel = CharacterListViewModel()
    }

    @Test
    fun `update character Should post new profiles to liveData and end loading`(){
        viewModel.beginLoading()
        assertEquals(EXPECTED_VIEW_VISIBLE, viewModel.loadingVisibility)
        viewModel.updateCharacters(charters)
        assertEquals(EXPECTED_VIEW_GONE, viewModel.loadingVisibility)
        assertEquals(charters, viewModel.characterProfilesLiveData.value)
    }

    @Test
    fun `update character details should update details view, make it visible and end loading`(){
        viewModel.beginLoading()
        assertEquals(EXPECTED_VIEW_VISIBLE, viewModel.loadingVisibility)
        assertEquals(EXPECTED_VIEW_GONE, viewModel.detailsVisibility)
        viewModel.updateCharacterDetails(charter)
        assertEquals(EXPECTED_VIEW_GONE, viewModel.loadingVisibility)
        assertEquals(EXPECTED_VIEW_VISIBLE, viewModel.detailsVisibility)
        assertEquals(charter, viewModel.characterDetailViewModel)
    }

    @Test
    fun `hide character details Minimized should hide details view`(){
        assertEquals(EXPECTED_VIEW_GONE, viewModel.detailsVisibility)
        viewModel.updateCharacterDetails(charter)
        assertEquals(EXPECTED_VIEW_VISIBLE, viewModel.detailsVisibility)
        viewModel.hideCharacterDetails()
        assertEquals(EXPECTED_VIEW_GONE, viewModel.detailsVisibility)
    }

    @Test
    fun `begin loading should make loading visible`(){
        assertEquals(EXPECTED_VIEW_GONE, viewModel.loadingVisibility)
        viewModel.beginLoading()
        assertEquals(EXPECTED_VIEW_VISIBLE, viewModel.loadingVisibility)
    }

    companion object {
        private const val EXPECTED_VIEW_GONE = 8
        private const val EXPECTED_VIEW_VISIBLE = 0
    }

}
