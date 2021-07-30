package com.example.breakingbad.di

import com.example.breakingbad.controller.BreakingBadController
import com.example.breakingbad.domain.BreakingBad
import com.example.breakingbad.event.*
import com.example.breakingbad.presenter.BreakingBadPresenter
import com.example.breakingbad.viewmodel.CharacterListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

//This file gives a brief overview of the entire applications operations split into Controller, Domain, Presenter and ViewModel

val controllerModule = module {
    single(createdAtStart=true) {
        BreakingBadController()
    }
}

val domainModule = module {
    single(createdAtStart=true){
        BreakingBad(get()).apply {
            CharactersFetched.event.registerDomain(::fetchAllCharacters)
            CharactersClicked.event.registerDomain(::fetchCharacterById)
            CharactersSearched.event.registerDomain(::fetchCharacterByAppearanceAndName)
        }
    }
}
val presenterModule = module {
    single(createdAtStart=true) {
        BreakingBadPresenter().apply {
            CharactersFetched.event.registerPresenter(::mapCharacterToViewModel)
            CharactersClicked.event.registerPresenter(::mapCharacterDetailToViewModel)
            CharactersSearched.event.registerPresenter(::mapCharacterToViewModel)
        }
    }
}

val viewModelModule = module {
    viewModel {
        CharacterListViewModel().apply {
            subscribe(CharactersFetched.event, ::updateCharacters)
            subscribe(CharactersClicked.event, ::updateCharacterDetails)
            subscribe(CharacterDetailsMinimized.event, ::hideCharacterDetails)
            subscribe(CharactersSearched.event, ::updateCharacters)
            subscribe(Loading.event, ::beginLoading)
        }
    }
}
