package com.example.eksamenrickandmortyapp.screens.created_character_list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.eksamenrickandmortyapp.data.CustomCharacter

@Composable
fun CustomCharacterItem(
    character: CustomCharacter
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(5)
            )
//            .clickable { onClick() }
            .background(
                when (character.status) {
                "Alive" -> Color(0xFF64ff47)
                "Dead" -> Color(0xFFff4947)
                    else -> Color(0xFFd7dcd6)
                }
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(342.dp)
                .background(color = Color.Gray),
            model = character.imageUrl,
            contentDescription = "Image of ${character.name}",
            alignment = Alignment.Center,
            contentScale = ContentScale.Crop,
        )

        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = character.name,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF031400),
                )
                Text(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    text = "#${character.id}"
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(text = character.species)

            Spacer(modifier = Modifier.height(4.dp))

            Text(text = character.status)
        }
    }
}