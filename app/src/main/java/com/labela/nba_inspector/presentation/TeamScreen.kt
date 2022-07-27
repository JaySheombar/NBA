package com.labela.nba_inspector.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.labela.nba_inspector.models.League
import com.labela.nba_inspector.models.Team
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*

@Composable
fun TeamScreen(navController: NavController) {

    var teams by remember { mutableStateOf(emptyList<Team>()) }

    LaunchedEffect(true) {
        val response = getData()
        teams = response
    }

    Scaffold(
        topBar = {
            TopAppBar {
                Spacer(
                    modifier = Modifier
                        .width(12.dp)
                )

                Text("NBA Inspector", fontSize = 20.sp)
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
            items(teams.size) { index ->
                val team = teams[index]

                TeamRow(
                    text = team.fullName,
                    onClick = { navController.navigate("team_detail/${team.teamId}/${team.fullName}") },
                )
            }
        }
    }
}

@Composable
private fun TeamRow(
    text: String,
    onClick: () -> Unit,
) = Row(
    modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 12.dp)
        .background(color = MaterialTheme.colors.surface)
        .clickable { onClick() }
        .padding(horizontal = 20.dp, vertical = 12.dp)

) {
    Text(
        color = MaterialTheme.colors.onSurface,
        text = text,
        fontSize = 16.sp,
    )
}

private suspend fun getData(): List<Team> {
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
        val response: League = client.get("http://data.nba.net/prod/v2/2022/teams.json")
        client.close()
        response.league?.standard ?: emptyList()
    } catch (error: Exception) {
        client.close()
        emptyList()
    }
}