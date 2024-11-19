package com.example.eksamenrickandmortyapp.screens.episode_list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eksamenrickandmortyapp.R
import com.example.eksamenrickandmortyapp.components.SearchBar
import kotlinx.coroutines.delay

// This page is essentially just a copy of the character page. Probably a better way to do this to do "DRY", but that would require massive reworks
@Composable
fun EpisodeListScreen(
    viewModel: EpisodeListViewModel,
    onEpisodeClick: (episodeId: Int) -> Unit = {},
    onBackButtonClick: () -> Unit = {},
    onLogoClick: () -> Unit = {}
) {
    val rickAndMortyFont = FontFamily(
        Font(R.font.wubba_lubba_dub_dub)
    )

    // Loading
    val loading = viewModel.loading.collectAsState()
    val loadingAttempts = viewModel.loadingAttempts.collectAsState()

    // Loading from API
    val loadTotalPages by viewModel.totalPages.collectAsState()
    val loadTotalEpisodes by viewModel.totalEpisodes.collectAsState()
    val loadingCurrentPage by viewModel.currentPage.collectAsState()
    val loadAmountOfEpisodes by viewModel.amountOfEpisodes.collectAsState()

    // Episodes collected
    val episodes by viewModel.episodes.collectAsState()

    // Search
    val searchQuery by viewModel.searchQuery.collectAsState()
    val isSearching = searchQuery.isNotEmpty()

    /// API Notification message
    var showConnectionMessage by remember { mutableStateOf(true) }

    val pullingFromDatabase = !loading.value && loadTotalPages == null && episodes.isNotEmpty()
    val statusMessagesVisible =
        showConnectionMessage && !isSearching && (
                loading.value ||
                        (loadTotalPages == null && !loading.value) ||
                        (loadTotalPages != null && loading.value) ||
                        (loading.value && loadingCurrentPage < 1)
                )

    LaunchedEffect(pullingFromDatabase) {
        if (pullingFromDatabase && loadTotalPages == null) {
            showConnectionMessage = true
            delay(3000)
            showConnectionMessage = false
        }
    }


    // All Content
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(
                colors = listOf(
                    Color(0xFF7b3946),
                    Color(0xFFd9ce42),
                    Color(0xFF8df1ef))
            )),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        // Top navigation bar
        Row(
            modifier = Modifier.fillMaxWidth()
//                .background(Color.White)
            ,
            horizontalArrangement = Arrangement.SpaceBetween,
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
                    .padding(start = 100.dp),
                text = "Episodes",
                fontFamily = rickAndMortyFont,
                fontSize = 32.sp,
                color = Color.White
            )


            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(8.dp))

                // Refresh Button
                IconButton(
                    onClick = {
                        if (!loading.value) { // To not let users spam the button
//                            viewModel.clearSearchQuery()
                            viewModel.searchEpisodes("")
                            viewModel.loadAllFunctions()
                        }
                    }
                ) {
                    Icon(
                        tint = when (loading.value) { // So the color changes when it's not usable
                            true -> Color.Transparent
                            false -> Color.White
                        },
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Refresh Episodes"
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

            }
        }

        HorizontalDivider()


        //# Searchbar
        // I had some help from chatgpt here to get it to search in real time
        // and to color it as it was lots of parameters which I didn't know which to get
        if (!statusMessagesVisible) {

            SearchBar(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .fillMaxWidth(),
                defaultText = "Search Episodes",
                color = Color.White,
                query = searchQuery,
                onQueryChange = { newValue ->
                    viewModel.searchEpisodes(newValue)
                },
                onClearClick = {
                    viewModel.searchEpisodes("")
                },
                onSearch = {
//                    viewModel.searchEpisodes(searchQuery)
                }
            )
        }


        if (statusMessagesVisible) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                color = Color.White,
                shape = RoundedCornerShape(8.dp),
                tonalElevation = 2.dp
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    when {
                        // Case 1: No Internet Connection
                        loadTotalPages == null && !loading.value -> {
                            Icon(
                                imageVector = Icons.Default.Warning,
                                contentDescription = "No Connection",
                                tint = Color(165, 37, 37, 255),
                                modifier = Modifier.size(48.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Can't reach API" +
                                        "\nCheck your internet connection" +
                                        "\nand refresh the page",
                                fontSize = 16.sp, // Equivalent to bodyMedium
                                textAlign = TextAlign.Center,
                                color = Color.Black
                            )
                        }

                        // Case 2: Connecting to the API
                        loading.value && loadingCurrentPage < 1 -> {
                            CircularProgressIndicator()
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Connecting to the API...",
                                fontSize = 16.sp, // Equivalent to bodyMedium
                                textAlign = TextAlign.Center,
                                color = Color.Black
                            )
                        }

                        // Case 3: Loading Pages
                        loading.value && loadTotalPages != null -> {
                            CircularProgressIndicator()
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Loading page $loadingCurrentPage of $loadTotalPages" +
                                        "\n$loadAmountOfEpisodes of $loadTotalEpisodes Episodes Loaded",
                                fontSize = 16.sp, // Equivalent to bodyMedium
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            if (loadingAttempts.value > 1) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Icon(
                                    imageVector = Icons.Default.Warning,
                                    contentDescription = "Bad Connection",
                                    tint = Color(165, 37, 37, 255),
                                    modifier = Modifier.size(24.dp)
                                )
                                Text(
                                    text = "Bad Connection!" +
                                            "\nFailed to load page $loadingCurrentPage, trying again." +
                                            "\nAttempt ${loadingAttempts.value}",
                                    fontSize = 14.sp, // Equivalent to bodySmall
                                    textAlign = TextAlign.Center,
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                        }


                    }
                }
            }
        }


        if (statusMessagesVisible) {
            HorizontalDivider(
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        // Message to tell the user that there's no episodes stored on device if they have no connection
        if (episodes.isEmpty() && loadTotalPages == null && !loading.value) {
            Column(modifier = Modifier.padding(top = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    modifier = Modifier.size(64.dp)
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(50)
                        )
                    ,
                    imageVector = Icons.Default.Info,
                    tint = Color(165, 37, 37, 255),
                    contentDescription = "Info"
                )
                Text(
                    text = "No Episodes stored on the device.",
                    fontSize = 20.sp,
                    color = Color.White,
                )
            }

        }


        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
        // .chunked puts the episodes in a list of lists of 2
            val episodePairs = episodes.chunked(2)
            items(episodePairs) { episodePair ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    episodePair.forEach { episode ->
                        EpisodeItem(
                            episode = episode,
                            onClick = { onEpisodeClick(episode.id) },

                            )
                    }
                }
            }
        }



    }

}