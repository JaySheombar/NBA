package com.labela.nba_inspector.models

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class PlayerLeague(
    @SerialName("league") val playerLeague: PlayersCollection? = null,
)

@kotlinx.serialization.Serializable
data class PlayersCollection(
    @SerialName("standard") val players: List<Player> = emptyList(),
)

@kotlinx.serialization.Serializable
data class Player(
    @SerialName("firstName") val firstName: String = "",
    @SerialName("lastName") val lastName: String = "",
    @SerialName("personId") val personId: String = "",
    @SerialName("teamId") val teamId: String = "",
    @SerialName("jersey") val jersey: String = "",
    @SerialName("pos") val position: String = "",
    @SerialName("country") val country: String = "",
)