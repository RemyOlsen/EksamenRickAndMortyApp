package com.example.eksamenrickandmortyapp.screens.character_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CharacterListScreen(
    viewModel: CharacterListViewModel,
    onCharacterClick: (characterId: Int) -> Unit = {}
) {
    val loading = viewModel.loading.collectAsState()
    val characters = viewModel.characters.collectAsState()
    val isListView by viewModel.isListView.collectAsState()
    val isSearching by viewModel.isSearching.collectAsState()

    var searchQuery by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().
        background(Color(0xFFbd00e0)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(modifier = Modifier.fillMaxWidth()
            .background(Color.White),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text =  "Characters"
            )
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier.size(30.dp)
                    .background(if (isListView) Color.Gray else Color.Transparent, shape = CircleShape)) {
                    IconButton(
//                        modifier = Modifier
//                            .size(30.dp)
//                            .background(if (isListView) Color.Gray else Color.Transparent, shape = CircleShape,),
                        onClick = { viewModel.toggleView() }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.List,
                            contentDescription = "List view",
                            tint = if (isListView) Color.White else Color.Black
                        )
                    }

                }
                IconButton(
                    onClick = { viewModel.loadNextPage() }
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Refresh Characters"
                    )
                }
                IconButton(
                    onClick = { /* TODO */ }
                ) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Options"
                    )
                }
            }
        }
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Search Characters")},
//                modifier = Modifier.fillMaxWidth()
            )
            Button(
//                modifier = Modifier.align(Alignment.End),
                onClick = {
                    viewModel.searchCharactersByName(searchQuery)
                }
            ) {
                Text("Search")
            }
        }

//        Spacer(modifier = Modifier.height(8.dp))


        HorizontalDivider()

        LazyColumn(
            modifier = Modifier.fillMaxWidth()
                .weight(1f)
        ) {
            items(characters.value) { character ->
                if (isListView) {
                    CharacterItemSmall(
                        character = character,
                        onClick = { onCharacterClick(character.id) }
                    )
                } else {
                    CharacterItemBig(
                        character = character,
                        onClick = { onCharacterClick(character.id) }
                )

                }

            }

            // Loads more characters when the user scrolls to the bottom of the list
            item {
                if (loading.value) {
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                } else if (!isSearching) {
                    LaunchedEffect(Unit) {
                        viewModel.loadNextPage()
                    }
                }
            }


        }
    }
}