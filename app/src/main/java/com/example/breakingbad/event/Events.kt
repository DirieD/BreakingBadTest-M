package com.example.breakingbad.event

import com.example.breakingbad.domain.entities.CharacterSearch
import com.example.lib.ControllerFullEvent
import com.example.breakingbad.domain.entities.Character
import com.example.breakingbad.viewmodel.CharacterDetailViewModel
import com.example.breakingbad.viewmodel.CharacterViewModel
import com.example.lib.UIEvent

//Key part of the event driven architecture
//Summarizes the functionality of the application while remaining type safe
//Each event is created with the type variables:
//  -First for the type the controller publishes
//  -Secondly for the type the domain/model publishes
//  -Thirdly for the type the presenter publishes to the viewModel
//By default the controllers value is passed from the controller, to domain, to presenter to viewModel
//So it's not necessary to add a domain or presenter to pass a value straight to the viewModel

object CharactersFetched{val event = ControllerFullEvent.create<Any?, List<Character>, List<CharacterViewModel>>()}

@Deprecated("Should search commutative")
object CharacterProfilesSearched{val event = ControllerFullEvent.create<String, List<Character>, List<CharacterViewModel>>()}
@Deprecated("Should search commutative")
object CharacterProfilesFiltered{val event = ControllerFullEvent.create<String, List<Character>, List<CharacterViewModel>>()}

object CharactersSearched{val event = ControllerFullEvent.create<CharacterSearch, List<Character>, List<CharacterViewModel>>()}

//UIEvents will bypass the domain and presenter and only publish to a view model
object CharactersClicked{val event = ControllerFullEvent.create<Int, Character, CharacterDetailViewModel>()}
object CharacterDetailsMinimized{val event = UIEvent.create<Any?>()}
object Loading{val event = UIEvent.create<Any?>()}

