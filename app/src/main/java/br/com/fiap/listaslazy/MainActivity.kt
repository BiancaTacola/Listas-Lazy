package br.com.fiap.listaslazy

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.listaslazy.model.Game
import br.com.fiap.listaslazy.repository.getAllGames
import br.com.fiap.listaslazy.repository.getGamesByStudio
import br.com.fiap.listaslazy.ui.theme.ListasLazyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("aaa", getAllGames().toString())
        setContent {
            ListasLazyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GamesScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GamesScreen() {
    var searchTextState by remember {
        mutableStateOf<String>("")
    }

    var gamesListState by remember {
        mutableStateOf<List<Game>>(getAllGames())
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Meus jogos favoritos",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn() {
            items(gamesListState) {
                GameCard(game = it)
            }
        }

        OutlinedTextField(
            value = searchTextState,
            onValueChange = {
                searchTextState = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(text = "Nome do estúdio")
            },
            trailingIcon = {
                IconButton(onClick = {
                    gamesListState = getGamesByStudio(searchTextState)
                }) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "")
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn() {
            items(getAllGames()) { game ->
                GameCard(game = game)
            }
        }
    }
}

@Composable
fun GameCard(game: Game) {
    Card(modifier = Modifier.padding(bottom = 8.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .weight(3f)
            ) {
                Text(
                    text = game.title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = game.studio,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal
                )
                Text(
                    text = game.releaseYear.toString(),
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0000FF) // Cor azul
                )
            }
        }
    }
}
