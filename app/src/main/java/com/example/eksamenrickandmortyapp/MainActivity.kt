package com.example.eksamenrickandmortyapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.eksamenrickandmortyapp.data.CharacterRepository
import com.example.eksamenrickandmortyapp.navigation.CharacterDetails
import com.example.eksamenrickandmortyapp.navigation.CharacterList
import com.example.eksamenrickandmortyapp.navigation.CreateCharacter
import com.example.eksamenrickandmortyapp.navigation.CustomCharacterList
import com.example.eksamenrickandmortyapp.screens.character_details.CharacterDetailsScreen
import com.example.eksamenrickandmortyapp.screens.character_details.CharacterDetailsViewModel
import com.example.eksamenrickandmortyapp.ui.theme.EksamenRickAndMortyAppTheme
import com.example.eksamenrickandmortyapp.screens.character_list.CharacterListScreen
import com.example.eksamenrickandmortyapp.screens.character_list.CharacterListViewModel
import com.example.eksamenrickandmortyapp.screens.created_character_list.CustomCharacterListScreen
import com.example.eksamenrickandmortyapp.screens.created_character_list.CustomCharacterListViewModel
import com.example.eksamenrickandmortyapp.screens.create_character.CharacterCreateScreen
import com.example.eksamenrickandmortyapp.screens.create_character.CharacterCreateViewModel

class MainActivity : ComponentActivity() {

    // Late Initialization of ViewModel so the initialization
    private lateinit var _characterListViewModel: CharacterListViewModel
    private var _characterDetailsViewModel = CharacterDetailsViewModel()

    private var _customCharacterCreateViewModel = CharacterCreateViewModel()
//    private var _customCharacterListViewModel = CustomCharacterListViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        CharacterRepository.initializeDatabase(applicationContext)

        _characterListViewModel = CharacterListViewModel()

        setContent {
            EksamenRickAndMortyAppTheme {

                val navController = rememberNavController()

                NavHost(navController = navController,
                    startDestination = CharacterList /*TODO: Change back to characterList*/
                    ) {

                        composable<CharacterList> {
                            CharacterListScreen(
                                viewModel = _characterListViewModel,
                                onCharacterClick = { characterId ->
                                    navController.navigate(CharacterDetails(characterId))
                                },
                                onCreateCharacterClick = {
                                    navController.navigate(CreateCharacter)
                                },
                                onCustomCharacterListClick = {
                                    navController.navigate(CustomCharacterList)
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
                            onBackButtonClick = { navController.popBackStack() }
                        )
                    }

                    composable<CreateCharacter> {
                        CharacterCreateScreen(
                            viewModel = _customCharacterCreateViewModel,
                            onSaveSuccess = { navController.popBackStack() },
                            onBackButtonClick = { navController.navigate(CharacterList) }

                        )
                    }

//                    composable<CustomCharacterList> {
//                        CustomCharacterListScreen(
//                            viewModel = _customCharacterListViewModel,
//                            onBackButtonClick = { navController.popBackStack() }
//                        )
//                    }




                }
            }
        }
    }
}
