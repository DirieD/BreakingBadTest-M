package com.example.breakingbad.controller

import com.example.breakingbad.domain.entities.CharacterSearch
import com.example.breakingbad.event.*
import com.example.lib.EventController

//Controller initiates every event in the application

class BreakingBadController : EventController(){
    fun fetchCharacters(){
        Loading.event.publishEvent()
        CharactersFetched.event.publishEvent()
    }

    fun fetchCharacterById(id : Int){
        Loading.event.publishEvent()
        CharactersClicked.event.publishEvent(id)
    }

    fun characterMinimised(){
        CharacterDetailsMinimized.event.publishEvent()
    }

    fun searchCharacters(season: String, name: String){
        Loading.event.publishEvent()
        CharactersSearched.event.publishEvent(CharacterSearch(season,name))
    }
}
