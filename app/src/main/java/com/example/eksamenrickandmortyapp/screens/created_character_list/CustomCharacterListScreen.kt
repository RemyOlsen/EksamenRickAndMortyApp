package com.example.eksamenrickandmortyapp.screens.created_character_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CustomCharacterListScreen(
    viewModel: CustomCharacterListViewModel,
    onBackButtonClick: () -> Unit = {}
) {

    val loading = viewModel.loading.collectAsState()
    val customCharacters = viewModel.customCharacters.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFbd00e0)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { onBackButtonClick() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back to list"
                )
            }
            Text(
                modifier = Modifier.padding(8.dp),
                text = "Custom Characters"
            )
        }

        HorizontalDivider()

        Spacer(modifier = Modifier.height(8.dp))

        if (loading.value) {
            Text(text = "Loading...")
        } else {
            LazyColumn {
                items(customCharacters.value) { character ->
                    CustomCharacterItem(character = character)

                    Spacer(modifier = Modifier.height(8.dp))

                }
            }

        }

    }
}