package com.labela.nba_inspector

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.labela.nba_inspector.models.League
import com.labela.nba_inspector.models.Team
import com.labela.nba_inspector.ui.theme.NBAinspectorTheme
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { NBAinspectorTheme { TeamScreen() } }
    }
}

// [Atlanta hawks, Boston Celtics, Brooklyn Nets]
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun TeamScreen() {

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
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(teams.size) { index ->
                val team = teams[index]
                TeamRow(text = team.fullName)
            }
        }
    }
}

@Composable
fun TeamRow(
    text: String,
) = Row(
    modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 12.dp),
) {
    Text(
        text = text,
        color = Color.Black,
        fontSize = 16.sp,
    )
}

suspend fun getData(): List<Team> {
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
    } catch(error: Exception) {
        client.close()
        emptyList()
    }
}