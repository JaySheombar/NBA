package com.labela.nba_inspector.models

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class RosterLeague(
    @SerialName("league") val rosterLeague: RosterCollection? = null,
)

@kotlinx.serialization.Serializable
data class RosterCollection(
    @SerialName("standard") val personList: PersonList? = null,
)

@kotlinx.serialization.Serializable
data class PersonList(
    @SerialName("players") val players: List<Person> = emptyList(),
)

@kotlinx.serialization.Serializable
data class Person(
    @SerialName("personId") val id: String = "",
)