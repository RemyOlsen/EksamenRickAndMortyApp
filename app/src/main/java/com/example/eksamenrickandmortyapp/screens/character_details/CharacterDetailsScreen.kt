package com.example.eksamenrickandmortyapp.screens.character_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun CharacterDetailsScreen(
    viewModel: CharacterDetailsViewModel,
    onBackButtonClick: () -> Unit = {}
) {
    val loading = viewModel.loading.collectAsState()
    val characterState = viewModel.selectedCharacter.collectAsState()

    val character = characterState.value
    if(character == null) {
        Text(text = "Failed to get character details")
        return
    }

    val textColor = if (character.status == "Dead") Color.White else Color.Black

    // Background Image
    AsyncImage(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Gray),
        model = character.image,
        contentDescription = character.name,
        alignment = Alignment.Center,
        contentScale = ContentScale.Crop
    )

    // Content
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                if (character.status == "Alive") Color(0xCC64FF47)
                else if(character.status == "Dead") Color(0xCCFF4947)
                else Color(0xCCD7DCD6)
            ),
    ) {
        // Header with back button and title
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
                text = "Character Details",
                style = MaterialTheme.typography.titleLarge
            )
        }

        // Content
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            // Character Image
            AsyncImage(
                model = character.image,
                contentDescription = character.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(343.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Character Name
            Text(
                text = character.name,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.SemiBold,
//                fontSize = 32.sp,
                color = textColor,
                textAlign = TextAlign.Center,
//                maxLines = 1,
//                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Status
            Text(
                text = "Status: ${character.status}",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Medium,
//                fontSize = 16.sp,
                color = textColor
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Species
            Text(
                text = "Species: ${character.species}",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Medium,
//                fontSize = 16.sp,
                color = textColor
            )

            // Type (if available)
            if (character.type.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Type: ${character.type}",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Medium,
//                    fontSize = 16.sp,
                    color = textColor
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Gender
            Text(
                text = "Gender: ${character.gender}",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Medium,
//                fontSize = 16.sp,
                color = textColor
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Origin
            Text(
                text = "Origin: ${character.origin.name}",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Medium,
//                fontSize = 16.sp,
                color = textColor
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Location
            Text(
                text = "Location: ${character.location.name}",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Medium,
//                fontSize = 16.sp,
                color = textColor
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Episodes List
            Text(
                text = "Episodes:",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Medium,
                color = textColor
            )

            character.episode.forEach { episodeUrl ->
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = episodeUrl,
                    style = MaterialTheme.typography.bodyMedium,
                    color = textColor
                )
            }
        }
    }
}