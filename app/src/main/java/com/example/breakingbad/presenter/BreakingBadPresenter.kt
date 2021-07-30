package com.example.breakingbad.presenter

import com.example.breakingbad.domain.entities.Character
import com.example.breakingbad.viewmodel.CharacterDetailViewModel
import com.example.breakingbad.viewmodel.CharacterViewModel

//Responsible for all the mapping in the application
class BreakingBadPresenter {

    fun mapCharacterToViewModel(characters : List<Character>?) : List<CharacterViewModel>?{
        return characters?.map { characterProfile -> with(characterProfile){
                CharacterViewModel(char_id,name,img,appearance)
            }
        }
    }

    fun mapCharacterDetailToViewModel(character : Character?) : CharacterDetailViewModel?{
        return character?.run{
            CharacterDetailViewModel(char_id,name,mapOccupations(occupation),img,status,nickname,mapAppearances(appearance))
        }
    }

    private fun mapOccupations(occupations: List<String>): String {
        return OCCUPATIONS_PREFIX + occupations.joinToString("\n")
    }

    private fun mapAppearances(appearances: List<Int>): String {
        return APPEARANCES_PREFIX + appearances.joinToString(", ")
    }

    companion object {
        const val OCCUPATIONS_PREFIX = "Occupations: "
        const val APPEARANCES_PREFIX = "Appeared In Seasons: "
        const val ALL_SEASONS = "All Seasons"
    }
}
