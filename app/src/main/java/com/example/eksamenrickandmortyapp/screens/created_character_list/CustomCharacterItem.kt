package com.example.eksamenrickandmortyapp.screens.created_character_list

import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.eksamenrickandmortyapp.data.entities.CustomCharacter

@Composable
fun CustomCharacterItem(
    customCharacter: CustomCharacter
) {

    val textColor =
        when (customCharacter.status) {
            "Dead" -> Color.White
            else -> Color.Black
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(5)
            )
            .background(
                when (customCharacter.status) {
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
            model = customCharacter.imageUrl,
            contentDescription = "Image of ${customCharacter.name}",
            alignment = Alignment.Center,
            contentScale = ContentScale.Crop,
        )
        // Info bar
        Column(
            modifier = Modifier.padding( horizontal = 8.dp),
        ) {
            // Name and ID
            Box(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Icon(
                    modifier = Modifier.align(Alignment.CenterStart),
                    imageVector = Icons.Default.Person,
                    contentDescription = "Name",
                    tint = textColor
                )
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = customCharacter.name,
                    fontWeight = FontWeight.SemiBold,
                    color = textColor,
                )
                Text(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    text = "#${customCharacter.id}",
                    color = textColor
                )
            }

            Spacer(modifier = Modifier.height(2.dp))

            // Species
            Box(
                modifier = Modifier.fillMaxWidth()
            )
            {
                Icon(
                    modifier = Modifier.align(Alignment.CenterStart),
                    imageVector = Icons.Default.Face,
                    contentDescription = "Species",
                    tint = textColor
                )
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = customCharacter.species,
                    color = textColor
                )
            }

            Spacer(modifier = Modifier.height(2.dp))


            // Gender
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    modifier = Modifier.align(Alignment.CenterStart),
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Gender",
                    tint = textColor
                )

                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = customCharacter.gender,
                    color = textColor
                )
            }

            Spacer(modifier = Modifier.height(2.dp))

            // Origin
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    modifier = Modifier.align(Alignment.CenterStart),
                    imageVector = Icons.Default.Home,
                    contentDescription = "Origin",
                    tint = textColor
                )

                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = customCharacter.origin,
                    color = textColor
                )

            }

            if (customCharacter.description != null) {
                HorizontalDivider(color = textColor)
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Description",
                        tint = textColor
                    )

                    Text(
                        text = customCharacter.description,
                        textAlign = TextAlign.Center,
                        color = textColor
                    )

                }
            }
        }
    }
}