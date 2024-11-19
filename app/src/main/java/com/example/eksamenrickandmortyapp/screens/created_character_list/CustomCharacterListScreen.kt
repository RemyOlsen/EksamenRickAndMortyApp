package com.example.eksamenrickandmortyapp.screens.created_character_list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eksamenrickandmortyapp.R
import com.example.eksamenrickandmortyapp.components.SearchBar

@Composable
fun CustomCharacterListScreen(
    viewModel: CustomCharacterListViewModel,
    onAddNewCharacterClick: () -> Unit = {},
    onBackButtonClick: () -> Unit = {},
    onLogoClick: () -> Unit = {}
) {

    val rickAndMortyFont = FontFamily(
        Font(R.font.wubba_lubba_dub_dub)
    )

    val loading = viewModel.loading.collectAsState()
    val customCharacters = viewModel.customCharacters.collectAsState()

    val searchQuery by viewModel.searchQuery.collectAsState()


    LaunchedEffect(Unit) {
        viewModel.searchCharacters("")
        viewModel.loadCustomCharacters()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(
                colors = listOf(
                    Color(0xFFfafce8),
                    Color(0xFF87c6db),
                    Color(0xFF82fce5))
            )),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
            ,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { onBackButtonClick() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }

            Text(
                modifier = Modifier
                    .clickable { onLogoClick() }
                    .padding(start = 30.dp),
                text = "Custom Characters",
                fontFamily = rickAndMortyFont,
                fontSize = 32.sp,
                color = Color.Black
            )
        }

        HorizontalDivider()

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            //# SearchBar
            SearchBar(
                modifier = Modifier
                    .padding(end = 16.dp)
                    .width(300.dp)
                ,
                defaultText = "Search Characters",
                query = searchQuery,
                color = Color.Gray,
                onQueryChange = { newValue ->
                    viewModel.searchCharacters(newValue)
                },
                onClearClick = {
                    viewModel.searchCharacters("")
                },
                onSearch = {
                    //                    viewModel.searchCharacters(searchQuery)
                }
            )

            // Add Button
            Button(
                modifier = Modifier
                    .size(52.dp),
                onClick = { onAddNewCharacterClick() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.Red
                    ),
                shape = CircleShape,
                elevation = ButtonDefaults.buttonElevation(8.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf( // Using same colors as front page buttons, but no transparency, so elevation also doesn't make it ugly
                                    Color(0xFF9cdae8),
                                    Color(0xFF8343a1),
                                    Color(0xFFdefec2),
                                )
                            ),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Character",
                        tint = Color.White,
                        modifier = Modifier.size(28.dp) // Icon size adjusted for balance
                    )
                }
            }
        }


        if (loading.value) {
            Text(text = "Loading...")
        } else {
            LazyColumn {
                items(customCharacters.value) { character ->
                    CustomCharacterItem(customCharacter = character)

                    Spacer(modifier = Modifier.height(8.dp))

                }
            }

        }

    }
}