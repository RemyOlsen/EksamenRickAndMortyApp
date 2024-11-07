package com.example.eksamenrickandmortyapp.screens.create_character

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


// TODO: ADD IMAGE SELECTION

@Composable
fun CharacterCreateScreen(
    viewModel: CharacterCreateViewModel,
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
    var isStatusDropdownExpanded by remember { mutableStateOf(false) }

    val statusOptions = listOf("Alive", "Dead", "unknown")
    val scrollState = rememberScrollState()

    LaunchedEffect(saveSuccess) {
        saveSuccess?.let {
            if (it) {
                onSaveSuccess()
                viewModel.resetSaveState()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFbd00e0))
//            .padding(16.dp)
            .verticalScroll(scrollState),
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

        HorizontalDivider()

        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp)
            ,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomTextField(
                label = "Name",
                value = name,
                onValueChange = { viewModel.characterNameInput(it) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Box {
                CustomTextField(
                    label = "Status",
                    value = status,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Dropdown Arrow"
                        )
                    }
                )

                DropdownMenu(
                    expanded = isStatusDropdownExpanded,
                    onDismissRequest = { isStatusDropdownExpanded = false },
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                        .padding(8.dp)
                ) {
                    statusOptions.forEach { option ->
                        DropdownMenuItem(
                            onClick = {
                                viewModel.statusInput(option)
                                isStatusDropdownExpanded = false
                            },
                            text = { Text(option) },
                            modifier = Modifier.background(
                                when (option) {
                                    "Alive" -> Color(0xCC64FF47)
                                    "Dead" -> Color(0xCCFF4947)
                                    else -> Color(0xCCD7DCD6)
                                }
                            )
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
                // An invisible box on top of the TextField to handle click so it will expand when clicking anywhere on the status bar
                // Otherwise it didn't work, as the other elements blocked the clicks, could only make icon clickabe
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .clickable { isStatusDropdownExpanded = true }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            CustomTextField(label = "Species", value = species, onValueChange = { viewModel.speciesInput(it) })
            Spacer(modifier = Modifier.height(16.dp))

            CustomTextField(label = "Type (Optional)", value = type ?: "", onValueChange = { viewModel.typeInput(it) })
            Spacer(modifier = Modifier.height(16.dp))

            CustomTextField(label = "Gender", value = gender, onValueChange = { viewModel.genderInput(it) })
            Spacer(modifier = Modifier.height(16.dp))

            CustomTextField(label = "Origin", value = origin, onValueChange = { viewModel.originInput(it) })
            Spacer(modifier = Modifier.height(16.dp))

            CustomTextField(label = "Location", value = location, onValueChange = { viewModel.locationInput(it) })
            Spacer(modifier = Modifier.height(16.dp))

            CustomTextField(label = "Description (Optional)", value = description ?: "", onValueChange = { viewModel.descriptionInput(it) })
            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { viewModel.saveCustomCharacter() },
                enabled = !isSaving && name.isNotBlank() && species.isNotBlank() && gender.isNotBlank() && origin.isNotBlank() && location.isNotBlank(),
                modifier = Modifier
                    .width(180.dp)
                    .padding(vertical = 8.dp),
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(Color.Green)
            ) {
                if (isSaving) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        color = Color.Black,
                        text = "Save Character")
                }
            }
        }
    }
}

@Composable
fun CustomTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    readOnly: Boolean = false,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF2F2F2), shape = MaterialTheme.shapes.medium)
            .padding(8.dp),
        singleLine = true,
        readOnly = readOnly,
        trailingIcon = trailingIcon
    )
}