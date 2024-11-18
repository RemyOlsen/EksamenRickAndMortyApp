package com.example.eksamenrickandmortyapp.screens.create_character

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.eksamenrickandmortyapp.data.entities.Character

@Composable
fun CharacterImageItem(
    character: Character,
    onClick: () -> Unit,
    isSelected: Boolean
) {
    Box(
        modifier = Modifier
            .size(120.dp)
            .clickable { onClick() }
            .background(
                shape = RoundedCornerShape(8.dp),
                color = if (isSelected) Color.Green else Color.Transparent
            )
            .padding(4.dp)
    ) {
        AsyncImage(
            modifier = Modifier
                .size(120.dp)
                .clip(RoundedCornerShape(8.dp))
            ,
            contentScale = ContentScale.Crop,
            model = character.image,
            contentDescription = character.name,
        )
    }
}