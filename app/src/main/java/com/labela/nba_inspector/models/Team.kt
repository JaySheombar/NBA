package com.labela.nba_inspector.models

@kotlinx.serialization.Serializable
data class League(
    val league: TeamCollection? = null,
)

@kotlinx.serialization.Serializable
data class TeamCollection(
    val standard: List<Team> = emptyList(),
)

@kotlinx.serialization.Serializable
data class Team(
    val isNBAFranchise: Boolean = false,
    val isAllStar: Boolean = false,
    val city: String = "",
    val altCityName: String = "",
    val fullName: String = "",
    val tricode: String = "",
    val teamId: String = "",
    val nickname: String = "",
    val urlName: String = "",
    val teamShortName: String = "",
    val confName: String = "",
    val divNam: String = "",
)