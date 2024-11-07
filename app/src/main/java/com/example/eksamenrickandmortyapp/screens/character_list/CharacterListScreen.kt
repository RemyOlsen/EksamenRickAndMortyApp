package com.example.eksamenrickandmortyapp.screens.character_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Face
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun CharacterListScreen(
    viewModel: CharacterListViewModel,
    onCharacterClick: (characterId: Int) -> Unit = {},
    onCreateCharacterClick: () -> Unit = {},
    onCustomCharacterListClick: () -> Unit = {}
) {
    val loading = viewModel.loading.collectAsState()
    val characters = viewModel.characters.collectAsState()
    val isListView by viewModel.isListView.collectAsState()
    val isSearching by viewModel.isSearching.collectAsState()

    var searchQuery by remember { mutableStateOf("") }

    // All Content
    Column(
        modifier = Modifier.fillMaxSize().
        background(Color(0xFFbd00e0)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Top navigation bar
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
                // Character creator
                IconButton(
                    onClick = { onCreateCharacterClick() }
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Create Character"
                    )
                }

                // Custom Character List
//                IconButton(
//                    onClick = { onCustomCharacterListClick() }
//                ) {
//                    Icon(
//                        imageVector = Icons.Default.Face,
//                        contentDescription = "Custom Character List"
//                    )
//                }

                Spacer(modifier = Modifier.width(8.dp))

                // List/Card view button
                Box(modifier = Modifier
                    .size(30.dp)
                    .background(
                        if (isListView) Color.Gray
                        else Color.Transparent, shape = CircleShape),
                    contentAlignment = Alignment.Center
                ){
                    IconButton(
                        modifier = Modifier
                            .size(30.dp),
                        onClick = { viewModel.toggleView() }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.List,
                            contentDescription = "List view",
                            tint = if (isListView) Color.White else Color.Black
                        )
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))

                IconButton(
                    onClick = { /*viewModel.loadNextPage()*/ }
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Refresh Characters"
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

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

        HorizontalDivider()

        Spacer(modifier = Modifier.height(8.dp))

        // Search Bar
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                modifier = Modifier
//                    .height(35.dp)
                    .shadow(
                    elevation = 2.dp,
                    shape = RoundedCornerShape(25)
                ),
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Search Characters")},
            )

            Spacer(modifier = Modifier.width(8.dp))

            // Search button
            Button(
//                modifier = Modifier.height(35.dp),
                onClick = {
                    viewModel.searchCharactersByName(searchQuery)
                }
            ) {
                Text("Search")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        HorizontalDivider()

        // Loading indicator if it's fetching data
        if (loading.value) {
            Box(modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
        }

        // Every Item in the list
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
//            item {
//                if (loading.value) {
//                    Box(modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(8.dp),
//                    contentAlignment = Alignment.Center
//                    ) {
//                        CircularProgressIndicator()
//                    }
//                } else if (!isSearching) {
//                    LaunchedEffect(Unit) {
//                        viewModel.loadNextPage()
//                    }
//                }
//            }


        }
    }
}