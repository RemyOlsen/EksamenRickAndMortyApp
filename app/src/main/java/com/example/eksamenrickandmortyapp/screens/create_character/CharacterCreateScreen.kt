package com.example.eksamenrickandmortyapp.screens.create_character

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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.eksamenrickandmortyapp.R


// TODO: ADD IMAGE SELECTION

@Composable
fun CharacterCreateScreen(
    viewModel: CharacterCreateViewModel,
    onSaveSuccess: () -> Unit = {},
    onBackButtonClick: () -> Unit = {},
    onImageButtonClick: () -> Unit = {}
) {
    val rickAndMortyFont = FontFamily(
        Font(R.font.wubba_lubba_dub_dub)
    )

    val name by viewModel.characterName.collectAsState()
    val status by viewModel.status.collectAsState()
    val species by viewModel.species.collectAsState()
    val gender by viewModel.gender.collectAsState()
    val origin by viewModel.origin.collectAsState()
    val description by viewModel.description.collectAsState()
    val imageUrl by viewModel.imageUrl.collectAsState()

    val cachedCharacters by viewModel.cachedCharacters.collectAsState()

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

    LaunchedEffect(Unit) {
        viewModel.getCharacterImages()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(
                colors = listOf(
                    Color(0xFF8343a1),
                    Color(0xFF9cdae8),
                    Color(0xFFdefec2)
                ))),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
//                .background(Color.White)
            ,
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
                modifier = Modifier.padding(start = 30.dp),
                text = "Create a Character",
                fontFamily = rickAndMortyFont,
                fontSize = 32.sp,
                color = Color.White
            )
        }

        HorizontalDivider()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(scrollState)
            ,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //# Name
            CustomOutlinedTextField(
                label = "Name",
                value = name,
                textColor = Color.White,
                onValueChange = { viewModel.characterNameInput(it) },
                maxLength = 32,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Name",
                        tint = Color.White
                    )
                },
                trailingIcon = {
                    if (name.isNotEmpty()) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Clear",
                            tint = Color.White,
                            modifier = Modifier.clickable { viewModel.characterNameInput("") }
                        )

                    }
                }
            )

            Spacer(modifier = Modifier.height(2.dp))


            /*
            * The Dropdown menu was really hard to style, I tried a lot of stuff and checked the documentation
            * I figured I'd not spend more time on this and just let it be, as it's "okay" as the rest of the app is fine.
            */
            //# Status
            Box {
                CustomOutlinedTextField(
                    label = "Status",
                    value = status,
                    onValueChange = {},
                    maxLength = 32,
                    readOnly = true,
                    textColor = Color.White,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Status",
                            tint = Color.White
                        )
                    },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Dropdown Arrow",
                            tint = Color.White

                        )
                    }
                )

                Spacer(modifier = Modifier.height(2.dp))

                DropdownMenu(
                    expanded = isStatusDropdownExpanded,
                    onDismissRequest = { isStatusDropdownExpanded = false },
                    modifier = Modifier
                        .width(380.dp)
                        .background(brush = Brush.verticalGradient(
                            colors = listOf( // I just put same gradient as the background on the box to make it look better
                                Color(0xFF8343a1),
                                Color(0xFF9cdae8),
                                Color(0xFFdefec2)
                            )))
                        .padding(2.dp)
                ) {
                    statusOptions.forEach { option ->
                        DropdownMenuItem(
                            onClick = {
                                viewModel.statusInput(option)
                                isStatusDropdownExpanded = false
                            },
                            text = {
                                Text(
                                text = option,
                                color = when (option) {
                                    "Dead" -> Color.White
                                    else -> Color.Black
                                }
                            ) },
                            modifier = Modifier.background(
                                when (option) {
                                    "Alive" -> Color(0xCC64FF47)
                                    "Dead" -> Color(0xCCFF4947)
                                    else -> Color(0xCCD7DCD6)
                                }
                            ),
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

            Spacer(modifier = Modifier.height(2.dp))


            //# Species
            CustomOutlinedTextField(
                label = "Species",
                value = species,
                maxLength = 32,
                textColor = Color.White,
                onValueChange = { viewModel.speciesInput(it) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Face,
                        contentDescription = "Species",
                        tint = Color.White
                    )
                },
                trailingIcon = {
                    if (species.isNotEmpty()) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Clear",
                            tint = Color.White,
                            modifier = Modifier.clickable { viewModel.speciesInput("") }

                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(2.dp))


            //# Gender
            CustomOutlinedTextField(
                label = "Gender",
                value = gender,
                maxLength = 32,
                textColor = Color.White,
                onValueChange = { viewModel.genderInput(it) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Gender",
                        tint = Color.White
                    )
                },
                trailingIcon = {
                    if (gender.isNotEmpty()) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Clear",
                            tint = Color.White,
                            modifier = Modifier.clickable { viewModel.genderInput("") }
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(2.dp))


            //# Origin
            CustomOutlinedTextField(
                label = "Origin",
                value = origin,
                textColor = Color.White,
                onValueChange = { viewModel.originInput(it) },
                maxLength = 32,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = "Origin",
                        tint = Color.White
                    )
                },
                trailingIcon = {
                    if (origin.isNotEmpty()) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Clear",
                            tint = Color.White,
                            modifier = Modifier.clickable { viewModel.originInput("") }
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(2.dp))


            //# Description
            CustomOutlinedTextField(
                label = "Description",
                value = description ?: "",
                textColor = Color.White,
                onValueChange = { viewModel.descriptionInput(it) },
                maxLength = 128,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Description",
                        tint = Color.White
                    )
                },
                trailingIcon = {
                    if (description?.isNotEmpty() == true) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Clear",
                            tint = Color.White,
                            modifier = Modifier.clickable { viewModel.descriptionInput("") }
                        )
                    }
                }
            )


            //# Image Selector
            Box(
                modifier = Modifier
                    .clickable {
                        if (cachedCharacters.isEmpty()) {
                            onImageButtonClick()
                        }
                    }
                    .padding(vertical = 10.dp, horizontal = 8.dp)
                    .height(56.dp)
                    .fillMaxWidth()
                    .background(
                        color = Color(0x54000000)
                        ,
                        shape = RoundedCornerShape(25.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = Color.Gray,
                        shape = RoundedCornerShape(25.dp)),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp)
                    ,
                ) {
                    if (cachedCharacters.isNotEmpty()) {
                        AsyncImage(
                            modifier = Modifier
                                .size(25.dp)
                            ,
                            model = imageUrl,
                            contentDescription = "Selected Image",
                        )
                        Text(
                            modifier = Modifier.padding(start = 16.dp),
                            text = "Image (Scrollable)",
                            color = Color.White,
                            fontSize = 16.sp,
                        )

                    } else {
                        Text(
                            modifier = Modifier.padding(start = 16.dp)
                                .fillMaxWidth(),
                            text = "No images available\n" +
                                    "Click to Load",
                            color = Color.White,
                            fontSize = 16.sp,
                        )
                    }
                }
            }

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                ,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(cachedCharacters) { character ->
                    CharacterImageItem(
                        character = character,
                        onClick = { viewModel.imageInput(character.image) },
                        isSelected = character.image == imageUrl
                    )

                }
            }

            Spacer(modifier = Modifier.padding(8.dp))

            //# Save Button
            Button(
                onClick = {
                    viewModel.saveCustomCharacter()
                },
                enabled = name.isNotBlank() && status.isNotBlank(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFF64ff47))
            ) {
                Text(
                    text = "Save Character",
                    color = Color.Black,
                    fontSize = 16.sp
                )
            }
        }
    }
}



@Composable
fun CustomOutlinedTextField(
    label: String,
    value: String,
    textColor: Color,
    onValueChange: (String) -> Unit,
    maxLength: Int = Int.MAX_VALUE,
    readOnly: Boolean = false,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = {
            if (it.length <= maxLength) {
                onValueChange(it)
            }
        },
        label = { Text(label, color = Color.White) },
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        singleLine = true,
        readOnly = readOnly,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        shape = RoundedCornerShape(25.dp),
        colors = TextFieldDefaults.colors(
            focusedTextColor = textColor,
            unfocusedTextColor = textColor,
            cursorColor = textColor,
            focusedContainerColor = Color(0x54000000),
            unfocusedContainerColor = Color(0x54000000),
            focusedIndicatorColor = Color.White,
            unfocusedIndicatorColor = Color.Gray
        )
    )
}