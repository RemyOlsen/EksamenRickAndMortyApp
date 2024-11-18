package com.example.eksamenrickandmortyapp.screens.character_list

import android.util.Log
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.List
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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


@Composable
fun CharacterListScreen(
    viewModel: CharacterListViewModel,
    onCharacterClick: (characterId: Int) -> Unit = {},
    onCreateCharacterClick: () -> Unit = {},
    onCustomCharacterListClick: () -> Unit = {},
    onBackButtonClick: () -> Unit = {}
) {

    val rickAndMortyFont = FontFamily(
        Font(R.font.wubba_lubba_dub_dub)
    )

    // Loading
    val loading = viewModel.loading.collectAsState()
    val loadingAttempts = viewModel.loadingAttempts.collectAsState()

    // Loading from API
    val loadTotalPages by viewModel.totalPages.collectAsState()
    val loadTotalCharacters by viewModel.totalCharacters.collectAsState()
    val loadingCurrentPage by viewModel.currentPage.collectAsState()
    val loadAmountOfCharacters by viewModel.amountOfCharacters.collectAsState()

    // Characters collected
    val characters = viewModel.characters.collectAsState()

    // Toggle between views
    val isListView by viewModel.isListView.collectAsState()

    // Search
    val searchQuery by viewModel.searchQuery.collectAsState()
    val isSearching = searchQuery.isNotEmpty()

    // Boolean to check if status messages are visible
    val statusMessagesVisible = !isSearching && (
            loading.value ||
            (loadTotalPages == null && !loading.value) ||
            (loadTotalPages != null && loading.value) ||
            (loading.value && loadingCurrentPage < 1)
            )


    Log.d("CharacterListScreen", "TotalPages: $loadTotalPages")

//    LaunchedEffect(Unit) {
//        viewModel.loadAllFunctions()
//        Log.d("CharacterListScreen", "LaunchedEffect called")
//    }

    // All Content
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(
                colors = listOf(Color(0xFFbf7da5), Color(0xFFecaba6), Color(0xFFf8e99a))
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
                modifier = Modifier.padding(start = 80.dp),
                text = "Characters",
                fontFamily = rickAndMortyFont,
                fontSize = 32.sp,
                color = Color.White
            )


            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {

                // List/Card view button
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .background(
                            if (isListView) Color.Gray
                            else Color.Transparent, shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    IconButton(
                        modifier = Modifier
                            .size(30.dp),
                        onClick = { viewModel.toggleView() }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.List,
                            contentDescription = "List view",
                            tint = Color.White
                        )
                    }
                }


                Spacer(modifier = Modifier.width(8.dp))


                // Refresh Button
                IconButton(
                    onClick = {
                        if (!loading.value) { // To not let users spam the button
//                            viewModel.clearSearchQuery()
                            viewModel.searchCharacters("")
                            viewModel.loadAllFunctions()
                        }
                    }
                ) {
                    Icon(
                        tint = when (loading.value) { // So the color changes when it's not usable
                            true -> Color.Gray
                            false -> Color.White
                        },
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Refresh Characters"
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
                color = Color.White,
                query = searchQuery,
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

//            val focusManager = LocalFocusManager.current
//            OutlinedTextField(
//                modifier = Modifier.padding(8.dp),
//                value = searchQuery,
//                onValueChange = { newValue ->
//
//                    viewModel.searchCharacters(newValue)
//                },
//                label = {
//                    Icon(
//                        imageVector = Icons.Default.Search,
//                        tint = Color.White,
//                        contentDescription = "Search")
//                    Text(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(horizontal = 32.dp),
//                        text = "Search Characters",
//                        fontSize = 16.sp,
//                        color = Color.White
//                    )
//                        },
//                trailingIcon = {
//                    if (searchQuery.isNotEmpty()) {
//                        IconButton(onClick = {
//                            viewModel.searchCharacters("")
//                            focusManager.clearFocus()
//                        }) {
//                            Icon(
//                                tint = Color.White,
//                                imageVector = Icons.Default.Close,
//                                contentDescription = "Clear"
//                            )
//                        }
//                    }
//                },
//                colors = TextFieldDefaults.colors(
//                    focusedTextColor = Color.White, // Input text color when focused
//                    unfocusedTextColor = Color.White, // Input text color when unfocused
//                    cursorColor = Color.White, // Cursor color
//                    focusedContainerColor = Color.Transparent, // Background color when focused
//                    unfocusedContainerColor = Color.Transparent, // Background color when unfocused
//                    focusedIndicatorColor = Color.White, // Border color when focused
//                    unfocusedIndicatorColor = Color.Gray // Border color when unfocused
//                ),
//
//                singleLine = true,
//                shape = RoundedCornerShape(25.dp),
//                keyboardOptions = KeyboardOptions.Default.copy(
//                    imeAction = ImeAction.Search
//                ),
//                keyboardActions = KeyboardActions(
//                    onSearch = {
//
//    //                    viewModel.searchCharacters(searchQuery)
//                        focusManager.clearFocus()
//                    }
//                )
//            )
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
                                text = "Can't reach API\nCheck your internet connection.",
                                style = MaterialTheme.typography.bodyMedium,
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
                                style = MaterialTheme.typography.bodyMedium,
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
                                        "\n$loadAmountOfCharacters of $loadTotalCharacters Characters Loaded",
                                style = MaterialTheme.typography.bodyMedium,
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
                                    style = MaterialTheme.typography.bodySmall,
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

        // Message to tell the user that there's no characters stored on device if they have no connection
        if (characters.value.isEmpty() && loadTotalPages == null && !loading.value) {
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
                    text = "No characters stored on the device.",
                    fontSize = 20.sp,
                    color = Color.White,
                )
            }

            }

//        // Every Item from the API
//        LazyColumn(
//            modifier = Modifier
//                .fillMaxWidth()
//                .weight(1f)
//        ) {
//
//            items(characters.value) { character ->
//                if (isListView) {
//                    CharacterItemList(
//                        character = character,
//                        onClick = { onCharacterClick(character.id) }
//                    )
//                } else {
//                    CharacterItemCard(
//                        character = character,
//                        onClick = { onCharacterClick(character.id) }
//                    )
//
//                }
//
//            }


        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {

            // .chunked puts the characters in a list of lists of 2
            val characterPairs = characters.value.chunked(2)


            if (!isListView) {
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
                        if (characterPair.size < 2) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            } else {
                items(characters.value) { character ->
                    CharacterItemList(
                        character = character,
                        onClick = { onCharacterClick(character.id) }
                    )
                }

            }


        }

    }
}