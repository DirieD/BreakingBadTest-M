package com.example.breakingbad.viewmodel

import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.lifecycle.MutableLiveData
import com.example.lib.EventViewModel

class CharacterListViewModel : EventViewModel() {

    val characterProfilesLiveData = MutableLiveData<List<CharacterViewModel>>()
    var characterDetailViewModel = DefaultCharacterDetailViewModel
    var detailsVisibility = GONE
    var loadingVisibility = GONE

    fun updateCharacters(characters: List<CharacterViewModel>?){
        endLoading()
        characterProfilesLiveData.postValue(characters)
    }

    fun updateCharacterDetails(detailViewModel: CharacterDetailViewModel?){
        endLoading()
        characterDetailViewModel = detailViewModel.also { detailsVisibility = VISIBLE } ?: DefaultCharacterDetailViewModel.also { detailsVisibility = GONE }
    }

    fun hideCharacterDetails(cOut: Any? = null){
        detailsVisibility = GONE
    }

    fun beginLoading(cOut: Any? = null){ loadingVisibility = VISIBLE }
    private fun endLoading(){ loadingVisibility = GONE }

    companion object {
        private val DefaultCharacterDetailViewModel = CharacterDetailViewModel(0,"","","","","","")
    }
}
