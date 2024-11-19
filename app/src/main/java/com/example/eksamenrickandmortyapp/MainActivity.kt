package com.example.eksamenrickandmortyapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.eksamenrickandmortyapp.data.CharacterRepository
import com.example.eksamenrickandmortyapp.data.entities.Episode
import com.example.eksamenrickandmortyapp.navigation.CharacterDetails
import com.example.eksamenrickandmortyapp.navigation.CharacterList
import com.example.eksamenrickandmortyapp.navigation.CreateCharacter
import com.example.eksamenrickandmortyapp.navigation.CustomCharacterList
import com.example.eksamenrickandmortyapp.navigation.EpisodeDetails
import com.example.eksamenrickandmortyapp.navigation.EpisodeList
import com.example.eksamenrickandmortyapp.navigation.FrontPage
import com.example.eksamenrickandmortyapp.screens.character_details.CharacterDetailsScreen
import com.example.eksamenrickandmortyapp.screens.character_details.CharacterDetailsViewModel
import com.example.eksamenrickandmortyapp.ui.theme.EksamenRickAndMortyAppTheme
import com.example.eksamenrickandmortyapp.screens.character_list.CharacterListScreen
import com.example.eksamenrickandmortyapp.screens.character_list.CharacterListViewModel
import com.example.eksamenrickandmortyapp.screens.created_character_list.CustomCharacterListScreen
import com.example.eksamenrickandmortyapp.screens.created_character_list.CustomCharacterListViewModel
import com.example.eksamenrickandmortyapp.screens.create_character.CharacterCreateScreen
import com.example.eksamenrickandmortyapp.screens.create_character.CharacterCreateViewModel
import com.example.eksamenrickandmortyapp.screens.episode_details.EpisodeDetailsScreen
import com.example.eksamenrickandmortyapp.screens.episode_details.EpisodeDetailsViewModel
import com.example.eksamenrickandmortyapp.screens.episode_list.EpisodeListScreen
import com.example.eksamenrickandmortyapp.screens.episode_list.EpisodeListViewModel
import com.example.eksamenrickandmortyapp.screens.front_page.FrontPageScreen

class MainActivity : ComponentActivity() {

    // Late Initialization of ViewModel so the initialization
    private val _characterListViewModel: CharacterListViewModel by viewModels()
    private val _characterDetailsViewModel:  CharacterDetailsViewModel by viewModels()

    private val _customCharacterCreateViewModel: CharacterCreateViewModel by viewModels()
    private val _customCharacterListViewModel: CustomCharacterListViewModel by viewModels()

    private val _episodeListViewModel: EpisodeListViewModel by viewModels()
    private val _episodeDetailsViewModel: EpisodeDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        CharacterRepository.initializeDatabase(applicationContext)


        setContent {
            EksamenRickAndMortyAppTheme {

                val navController = rememberNavController()

                NavHost(navController = navController,
                    startDestination = FrontPage
                    ) {

                    composable<FrontPage> {
                        FrontPageScreen(
                            onCharacterListClick = {
                                navController.navigate(CharacterList)
                            },
                            onCustomCharacterListClick = {
                                navController.navigate(CustomCharacterList)

                            },
                            onEpisodeListClick = {
                                navController.navigate(EpisodeList)
                            }
                        )
                    }

                    composable<CharacterList> {
                        CharacterListScreen(
                            viewModel = _characterListViewModel,
                            onCharacterClick = { characterId ->
                                navController.navigate(CharacterDetails(characterId))
                            },
                            onBackButtonClick = {
                                navController.popBackStack()
                            },
                            onLogoClick = {
                                navController.navigate(FrontPage) {
                                    popUpTo(navController.graph.startDestinationId) {inclusive = true}
                                    launchSingleTop = true
                                }
                            }
                        )
                    }

                    composable<CharacterDetails> { backStackEntry ->
                        val details = backStackEntry.toRoute<CharacterDetails>()


                        LaunchedEffect(details) {
                            _characterDetailsViewModel.loadCharacterDetails(details.characterId)
                        }

                        CharacterDetailsScreen(
                            viewModel = _characterDetailsViewModel,
                            onBackButtonClick = { navController.popBackStack() },
                            onLoadEpisodesClick = {
                                navController.navigate(EpisodeList)
                            },
                            onEpisodeClick = { episodeId ->
                                navController.navigate(EpisodeDetails(episodeId)) },
                            onLogoClick = {
                                navController.navigate(FrontPage) {
                                    popUpTo(navController.graph.startDestinationId) {inclusive = true}
                                    launchSingleTop = true
                                }
                            }
                        )
                    }

                    composable<CustomCharacterList> {
                        CustomCharacterListScreen(
                            viewModel = _customCharacterListViewModel,
                            onAddNewCharacterClick = { navController.navigate(CreateCharacter) },
                            onBackButtonClick = { navController.popBackStack() },
                            onLogoClick = {
                                navController.navigate(FrontPage) {
                                    popUpTo(navController.graph.startDestinationId) {inclusive = true}
                                    launchSingleTop = true
                                }
                            }
                        )
                    }

                    composable<CreateCharacter> {
                        CharacterCreateScreen(
                            viewModel = _customCharacterCreateViewModel,
                            onSaveSuccess = { navController.popBackStack() },
                            onImageButtonClick = { navController.navigate(CharacterList) },
                            onBackButtonClick = { navController.popBackStack() },
                            onLogoClick = {
                                navController.navigate(FrontPage) {
                                    popUpTo(navController.graph.startDestinationId) {inclusive = true}
                                    launchSingleTop = true
                                }
                            }
                        )
                    }

                    composable<EpisodeList> {
                        EpisodeListScreen(
                            viewModel = _episodeListViewModel,
                            onEpisodeClick = { episodeId ->
                                navController.navigate(EpisodeDetails(episodeId))
                            },
                            onBackButtonClick = { navController.popBackStack() },
                            onLogoClick = {
                                navController.navigate(FrontPage) {
                                    popUpTo(navController.graph.startDestinationId) {inclusive = true}
                                    launchSingleTop = true
                                }
                            }
                        )
                    }

                    composable<EpisodeDetails> { backStackEntry ->
                        val details = backStackEntry.toRoute<EpisodeDetails>()


                        LaunchedEffect(details) {
                            _episodeDetailsViewModel.loadEpisodeDetails(details.episodeId)
                        }

                        EpisodeDetailsScreen(
                            viewModel = _episodeDetailsViewModel,
                            onBackButtonClick = { navController.popBackStack() },
                            onCharacterClick = { characterId ->
                                navController.navigate(CharacterDetails(characterId))
                            },
                            onLoadCharactersClick = {
                                navController.navigate(CharacterList)
                            },
                            onLogoClick = {
                                navController.navigate(FrontPage) {
                                    popUpTo(navController.graph.startDestinationId) {inclusive = true}
                                    launchSingleTop = true
                                }
                            }
                        )
                    }


                }
            }
        }
    }
}
