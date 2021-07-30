package com.example.breakingbad.viewmodel

import com.example.breakingbad.extensions.ListItemViewModel

data class CharacterViewModel(
    val char_id : Int,
    val name : String,
    val img : String,
    val appearance : List<Int>
) : ListItemViewModel()
