package com.example.breakingbad.domain.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Character(
    @PrimaryKey
    val char_id : Int,
    val name : String,
    val occupation : List<String>,
    val img : String,
    val status : String,
    val nickname : String,
    val appearance : List<Int>
)
