package com.example.breakingbad.network

import com.example.breakingbad.domain.entities.Character
import retrofit2.http.GET

const val BASE_URL = "https://breakingbadapi.com/api/"
const val CHARACTERS = "characters/"

interface BreakingBadNetworkService {
        @GET(CHARACTERS)
        suspend fun fetchCharactersAsync(): List<Character>
}
