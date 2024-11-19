package com.example.eksamenrickandmortyapp.screens.episode_details

import android.util.Log
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eksamenrickandmortyapp.R
import com.example.eksamenrickandmortyapp.screens.character_list.CharacterItemCard
import com.example.eksamenrickandmortyapp.utilities.EpisodeImage

@Composable
fun EpisodeDetailsScreen(
    viewModel: EpisodeDetailsViewModel, // Create and use an Episode-specific ViewModel
    onCharacterClick: (Int) -> Unit = {},
    onLoadCharactersClick: () -> Unit = {},
    onBackButtonClick: () -> Unit = {},
    onLogoClick: () -> Unit = {}
) {

    val rickAndMortyFont = FontFamily(
        Font(R.font.wubba_lubba_dub_dub)
    )

    val episodeState = viewModel.selectedEpisode.collectAsState()

    val cachedCharacters by viewModel.cachedCharacters.collectAsState()
    Log.d("EpisodeDetailsScreen", "Cached characters size: ${cachedCharacters.size}")

    val episode = episodeState.value
    if (episode == null) {
        Text(text = "Failed to get episode details")
        return
    }

    // Content
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF746490),
                        Color(0xFFef68c5),
                        Color(0xFFfdfdfd),
                    )
                )
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
                    tint = Color.White
                )
            }

            Text(
                modifier = Modifier
                    .clickable { onLogoClick() }
                    .padding(start = 55.dp),
                text = "Episode Details",
                fontFamily = rickAndMortyFont,
                fontSize = 32.sp,
                color = Color.White
            )
        }

        HorizontalDivider(color = Color.White)


        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Episode Info
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

//                    Image(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(343.dp),
//                        painter = episode.image,
//                        contentDescription = episode.name
//                    )

                    EpisodeImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(343.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        episodeId = episode.id
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    //# Name
                    Text(
                        text = episode.name,
                        fontSize = 48.sp,
                        fontFamily = rickAndMortyFont,
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        lineHeight = 48.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    //# Code
                    Text(
                        text = "Episode: ${episode.episode}",
                        fontSize = 16.sp,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    //# Air Date
                    Text(
                        text = "Air Date: ${episode.airDate}",
                        fontSize = 16.sp,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    //# Characters Title
                    Text(
                        text = "Characters:",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    if (cachedCharacters.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .clickable {
                                    if (cachedCharacters.isEmpty()) {
                                        onLoadCharactersClick()
                                    }
                                }
                                .padding(vertical = 10.dp, horizontal = 8.dp)
                                .height(56.dp)
                                .fillMaxWidth()
                                .background(brush = Brush.horizontalGradient(
                                    colors = listOf(
                                        Color(0xFFbf7da5),
                                        Color(0xFFecaba6),
                                        Color(0xFFf8e99a)
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
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White

                            )

                        }
                    }

                }
            }

            // Characters List
            if (cachedCharacters.isNotEmpty()) {
                val characterPairs = cachedCharacters.chunked(2)
                items(characterPairs) { characterPair ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        characterPair.forEach { character ->
                            CharacterItemCard(
                                character = character,
                                onClick = { onCharacterClick(character.id) },
                            )
                        }
                    }
                }
            } else {
                item {
                    Text(
                        text = "No characters available.",
                        fontSize = 14.sp,
                        color = Color.White,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}
