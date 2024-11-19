package com.example.eksamenrickandmortyapp.screens.character_details

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.eksamenrickandmortyapp.R
import com.example.eksamenrickandmortyapp.screens.character_list.CharacterItemCard
import com.example.eksamenrickandmortyapp.screens.episode_list.EpisodeItem

@Composable
fun CharacterDetailsScreen(
    viewModel: CharacterDetailsViewModel,
    onBackButtonClick: () -> Unit = {},
    onLoadEpisodesClick: () -> Unit = {},
    onEpisodeClick: (Int) -> Unit = {},
    onLogoClick: () -> Unit = {}
) {
    val rickAndMortyFont = FontFamily(
        Font(R.font.wubba_lubba_dub_dub)
    )

    val characterState = viewModel.selectedCharacter.collectAsState()

    val cachedEpisodes by viewModel.cachedEpisodes.collectAsState()

    val character = characterState.value
    if (character == null) {
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
                else if (character.status == "Dead") Color(0xCCFF4947)
                else Color(0xCCD7DCD6)
            )
    ) {
        // Header with back button and title
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { onBackButtonClick() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = textColor
                )
            }

            Text(
                modifier = Modifier
                    .clickable { onLogoClick() }
                    .padding(start = 30.dp)
                ,
                text = "Character Details",
                fontFamily = rickAndMortyFont,
                fontSize = 32.sp,
                color = textColor
            )
        }

        HorizontalDivider(color = textColor)

        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Character Info
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

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
                        fontSize = 48.sp,
                        fontFamily = rickAndMortyFont,
                        textAlign = TextAlign.Center,
                        color = textColor,
                        lineHeight = 48.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Status
                    Text(
                        text = "Status: ${character.status}",
                        fontSize = 16.sp,
                        color = textColor
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Species
                    Text(
                        text = "Species: ${character.species}",
                        fontSize = 16.sp,
                        color = textColor
                    )

                    // Type (if available)
                    if (character.type.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Type: ${character.type}",
                            fontSize = 16.sp,
                            color = textColor
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Gender
                    Text(
                        text = "Gender: ${character.gender}",
                        fontSize = 16.sp,
                        color = textColor
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Origin
                    Text(
                        text = "Origin: ${character.origin.name}",
                        fontSize = 16.sp,
                        color = textColor
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Location
                    Text(
                        text = "Location: ${character.location.name}",
                        fontSize = 16.sp,
                        color = textColor
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Episodes Title
                    Text(
                        text = "Episodes:",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = textColor
                    )
                    if (cachedEpisodes.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .clickable {
                                    if (cachedEpisodes.isEmpty()) {
                                        onLoadEpisodesClick()
                                    }
                                }
                                .padding(vertical = 10.dp, horizontal = 8.dp)
                                .height(56.dp)
                                .fillMaxWidth()
                                .background(brush = Brush.horizontalGradient(
                                    colors = listOf(
                                        Color(0xFF7b3946),
                                        Color(0xFFd9ce42),
                                        Color(0xFF8df1ef),
                                    ))
                                    ,
                                    shape = RoundedCornerShape(25.dp)
                                )
                                .border(
                                    width = 1.dp,
                                    color = Color.LightGray,
                                    shape = RoundedCornerShape(25.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Click To\nLoad Characters",
                                textAlign = TextAlign.Center,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White



                            )

                        }
                    }

                }
            }

            // Characters List
            if (cachedEpisodes.isNotEmpty()) {
                val episodePairs = cachedEpisodes.chunked(2)
                items(episodePairs) { characterPair ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        characterPair.forEach { episode ->
                            EpisodeItem(
                                episode = episode,
                                onClick = { onEpisodeClick(episode.id) },
                            )
                        }
                    }
                }
            } else {
                item {
                    Text(
                        text = "No episodes available.",
                        fontSize = 14.sp,
                        color = Color.White,
                        modifier = Modifier.padding(16.dp)
                    )

                }
            }


        }
    }
}