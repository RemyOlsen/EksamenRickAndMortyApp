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
import com.example.eksamenrickandmortyapp.screens.character_details.CharacterDetailsScreen
import com.example.eksamenrickandmortyapp.screens.character_details.CharacterDetailsViewModel
import com.example.eksamenrickandmortyapp.ui.theme.EksamenRickAndMortyAppTheme
import com.example.eksamenrickandmortyapp.screens.character_list.CharacterListScreen
import com.example.eksamenrickandmortyapp.screens.character_list.CharacterListViewModel

class MainActivity : ComponentActivity() {

    // Late Initialization of ViewModel so the initialization
    private lateinit var _characterListViewModel: CharacterListViewModel
    private var _characterDetailsViewModel = CharacterDetailsViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        CharacterRepository.initializeDatabase(applicationContext)

        _characterListViewModel = CharacterListViewModel()

        setContent {
            EksamenRickAndMortyAppTheme {

                val navController = rememberNavController()

                NavHost(navController = navController,
                    startDestination = CharacterList
                    ) {

                        composable<CharacterList> {
                            CharacterListScreen(
                                viewModel = _characterListViewModel,
                                onCharacterClick = { characterId ->
                                    navController.navigate(CharacterDetails(characterId))
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
                }

//                CharacterListScreen(
//                    viewModel = _characterListViewModel,
//                    onCharacterClick = { }
//                )
            }
        }
    }
}

//@Composable
//@Preview
//fun CharacterItemScreen() {
//    val character = Character(
//        id = 1,
//        name = "Rick Sanchez",
//        status = "Alive",
//        species = "Human",
//        type = "",
//        gender = "Male",
//        origin = Origin(
//            name = "Earth (C-137)",
//            url = "https://rickandmortyapi.com/api/location/1"
//        ),
//        location = Location(
//            name = "Citadel of Ricks",
//            url = "https://rickandmortyapi.com/api/location/3"
//        ),
//        image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
//        episode = listOf(
//            "https://rickandmortyapi.com/api/episode/1",
//            "https://rickandmortyapi.com/api/episode/2",
//            "https://rickandmortyapi.com/api/episode/3",
//        ),
//        url = "https://rickandmortyapi.com/api/character/1",
//        created = "2017-11-04T18:48:46.250Z"
//    )
//
//    CharacterItem(
//        character = character,
//        onClick = { /* Handle click event here if needed */ }
//    )
//}
