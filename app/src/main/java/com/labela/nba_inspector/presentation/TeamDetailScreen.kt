package com.labela.nba_inspector.presentation

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.labela.nba_inspector.R
import com.labela.nba_inspector.models.*
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*

@Composable
fun TeamDetailScreen(
    navController: NavController,
    teamId: String,
    teamName: String,
) {
    var players by remember { mutableStateOf(emptyList<Player>()) }

    LaunchedEffect(true) {
        val rosterData = getRosterData(teamId = teamId)
        val playerData = getPlayerData()

        Log.d("SANJAY", "$rosterData")
        Log.d("SANJAY", "$playerData")

        val list = mutableListOf<Player>()
        rosterData.forEach { person ->
            val player = playerData.find { it.personId == person.id }
            player?.let { list.add(it) }
        }

        players = list.toList()
    }

    Scaffold(
        topBar = {
            TopAppBar {
                IconButton(
                    onClick = { navController.navigateUp() },
                    content = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_back),
                            contentDescription = null,
                        )
                    }
                )

                Spacer(modifier = Modifier.width(12.dp))

                Text(teamName, fontSize = 20.sp)
            }
        }
    ) { contentPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(players.size) { index ->
                val player = players[index]

                PlayerRow(
                    firstName = player.firstName,
                    lastName = player.lastName,
                    jersey = player.jersey,
                )
            }
        }
    }
}

@Composable
private fun PlayerRow(
    firstName: String,
    lastName: String,
    jersey: String,
) = Row(
    modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 12.dp)
        .background(color = Color.Black)
        .padding(horizontal = 20.dp, vertical = 12.dp),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween,
) {
    Text(text = "$firstName $lastName", color = Color.White)
    Text(text = "$jersey", color = Color.White)
}

private suspend fun getPlayerData(): List<Player> {
    val client = HttpClient(Android) {
        install(JsonFeature) {
            serializer = KotlinxSerializer(
                kotlinx.serialization.json.Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                }
            )
        }
    }

    return try {
        val response: PlayerLeague = client.get("http://data.nba.net/prod/v1/2022/players.json")
        client.close()
        response.playerLeague?.players ?: emptyList()
    } catch (error: Exception) {
        client.close()
        emptyList()
    }
}

private suspend fun getRosterData(teamId: String): List<Person> {
    val client = HttpClient(Android) {
        install(JsonFeature) {
            serializer = KotlinxSerializer(
                kotlinx.serialization.json.Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                }
            )
        }
    }

    return try {
        val response: RosterLeague = client.get("http://data.nba.net/prod/v1/2022/teams/${teamId}/roster.json")
        client.close()
        response.rosterLeague?.personList?.players ?: emptyList()
    } catch (error: Exception) {
        client.close()
        emptyList()
    }
}