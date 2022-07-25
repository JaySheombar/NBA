package com.labela.nba_inspector.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.labela.nba_inspector.R

@Composable
fun TeamDetailScreen(
    navController: NavController,
    teamId: String,
    teamName: String,
) {
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            Text(text = teamId, color = Color.Black)
        }
    }
}