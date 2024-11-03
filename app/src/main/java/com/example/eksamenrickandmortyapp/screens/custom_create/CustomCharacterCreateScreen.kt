package com.example.eksamenrickandmortyapp.screens.custom_create

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CustomCharacterCreateScreen(
    viewModel: CustomCharacterCreateViewModel,
    onSaveSuccess: () -> Unit = {},
    onBackButtonClick: () -> Unit = {}
) {

    val name by viewModel.characterName.collectAsState()
    val status by viewModel.status.collectAsState()
    val species by viewModel.species.collectAsState()
    val type by viewModel.type.collectAsState()
    val gender by viewModel.gender.collectAsState()
    val origin by viewModel.origin.collectAsState()
    val location by viewModel.location.collectAsState()
    val description by viewModel.description.collectAsState()
    val imageUrl by viewModel.imageUrl.collectAsState()
    val isSaving by viewModel.isSaving.collectAsState()
    val saveSuccess by viewModel.saveSuccess.collectAsState()

    // Handle navigation or UI feedback on save success
    LaunchedEffect(saveSuccess) {
        saveSuccess?.let {
            if (it) {
                onSaveSuccess()
                viewModel.resetSaveState()
            } else {
                // Optionally, show an error message
            }
        }
    }
    Column(
        modifier = Modifier.fillMaxSize().
        background(Color(0xFFbd00e0)),
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
                text = "Create a Character"
            )
        }
    }

//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text("Create Custom Character") },
//                navigationIcon = {
//                    IconButton(onClick = { onCancel() }) {
//                        Icon(
//                            imageVector = Icons.Default.Menu, // Use an appropriate icon
//                            contentDescription = "Cancel"
//                        )
//                    }
//                }
//            )
//        }
//    ) { paddingValues ->
//        Column(
//            modifier = Modifier
//                .padding(paddingValues)
//                .padding(16.dp)
//                .fillMaxSize()
//                .background(Color(0xFFF0F0F0)),
//            verticalArrangement = Arrangement.Top,
//            horizontalAlignment = Alignment.Start
//        ) {
//            // Name Input
//            OutlinedTextField(
//                value = name,
//                onValueChange = { viewModel.characterNameInput(it) },
//                label = { Text("Name") },
//                modifier = Modifier.fillMaxWidth(),
//                singleLine = true
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//
//            // Status Input
//            OutlinedTextField(
//                value = status,
//                onValueChange = { viewModel.statusInput(it) },
//                label = { Text("Status") },
//                modifier = Modifier.fillMaxWidth(),
//                singleLine = true
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//
//            // Species Input
//            OutlinedTextField(
//                value = species,
//                onValueChange = { viewModel.speciesInput(it) },
//                label = { Text("Species") },
//                modifier = Modifier.fillMaxWidth(),
//                singleLine = true
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//
//            // Type Input
//            OutlinedTextField(
//                value = type ?: "",
//                onValueChange = { viewModel.typeInput(if (it.isBlank()) null else it) },
//                label = { Text("Type (Optional)") },
//                modifier = Modifier.fillMaxWidth(),
//                singleLine = true
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//
//            // Gender Input
//            OutlinedTextField(
//                value = gender,
//                onValueChange = { viewModel.genderInput(it) },
//                label = { Text("Gender") },
//                modifier = Modifier.fillMaxWidth(),
//                singleLine = true
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//
//            // Origin Input
//            OutlinedTextField(
//                value = origin,
//                onValueChange = { viewModel.originInput(it) },
//                label = { Text("Origin") },
//                modifier = Modifier.fillMaxWidth(),
//                singleLine = true
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//
//            // Location Input
//            OutlinedTextField(
//                value = location,
//                onValueChange = { viewModel.locationInput(it) },
//                label = { Text("Location") },
//                modifier = Modifier.fillMaxWidth(),
//                singleLine = true
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//
//            // Description Input
//            OutlinedTextField(
//                value = description ?: "",
//                onValueChange = { viewModel.descriptionInput(if (it.isBlank()) null else it) },
//                label = { Text("Description (Optional)") },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(100.dp),
//                maxLines = 5
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//
//            // Image URL Input
//            OutlinedTextField(
//                value = imageUrl,
//                onValueChange = { viewModel.imageInput(it) },
//                label = { Text("Image URL") },
//                modifier = Modifier.fillMaxWidth(),
//                singleLine = true,
//                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Uri)
//            )
//            Spacer(modifier = Modifier.height(16.dp))
//
//            // Save Button
//            Button(
//                onClick = { viewModel.saveCustomCharacter() },
//                modifier = Modifier.fillMaxWidth(),
//                enabled = !isSaving && name.isNotBlank() && species.isNotBlank() && gender.isNotBlank() && origin.isNotBlank() && location.isNotBlank()
//            ) {
//                if (isSaving) {
//                    CircularProgressIndicator(
//                        color = Color.White,
//                        modifier = Modifier.size(20.dp),
//                        strokeWidth = 2.dp
//                    )
//                } else {
//                    Text("Save Character")
//                }
//            }
//
//            Spacer(modifier = Modifier.height(16.dp))
//        }
//    }
}