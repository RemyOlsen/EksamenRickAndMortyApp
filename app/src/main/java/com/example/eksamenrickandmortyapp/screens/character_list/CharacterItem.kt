package com.example.eksamenrickandmortyapp.screens.character_list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.eksamenrickandmortyapp.data.Character
import com.example.eksamenrickandmortyapp.data.Location
import com.example.eksamenrickandmortyapp.data.Origin

@Composable
fun CharacterItemSmall(
    character: Character,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(124.dp)
            .padding(
                horizontal = 12.dp,
                vertical = 8.dp
            )
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(10)
            )
            .background(
                if (character.status == "Alive") Color(0xFF64ff47)
                else if(character.status == "Dead") Color(0xFFff4947)
                else Color(0xFFd7dcd6)
            )
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = Modifier
                .size(108.dp, 108.dp)
                .background(color = Color.Gray),
            model = character.image,
            contentDescription = "Image of ${character.name}",
            alignment = Alignment.Center,
            contentScale = ContentScale.Crop,
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp),
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    modifier = Modifier.width(220.dp),
                    text = character.name,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF031400),
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis

                )
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterEnd),
                    text = "#${character.id}",
                    fontSize = 12.sp
                )
            }

            Spacer( modifier = Modifier.height(4.dp) )

            Text( text = "${character.species} - ${character.gender}" )

            Spacer( modifier = Modifier.height(4.dp) )

            Text(
                text = "Status: ${character.status}",
                fontSize = 14.sp
            )

        }
    } // Row
}

@Composable
fun CharacterItemBig(
    character: Character,
    onClick: () -> Unit = {}
) {
    
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(5))
            .background(
                if (character.status == "Alive") Color(0xFF64ff47)
                else if(character.status == "Dead") Color(0xFFff4947)
                else Color(0xFFd7dcd6))
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(342.dp)
                .background(color = Color.Gray),
            model = character.image,
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

@Preview(showBackground = true)
@Composable
fun CharacterItemPreview() {
    CharacterItemSmall(
        character = Character(
            id = 1,
            name = "Rick Sanchez really fucking long name to make issues with overflow",
            status = "Alive",
            species = "Human",
            type = "",
            gender = "Male",
            origin = Origin(
                name = "Earth (C-137)",
                url = "https://rickandmortyapi.com/api/location/1"
            ),
            location = Location(
                name = "Citadel of Ricks",
                url = "https://rickandmortyapi.com/api/location/3"
            ),
            image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
            episode = listOf(
                "https://rickandmortyapi.com/api/episode/1",
                "https://rickandmortyapi.com/api/episode/2",
                "https://rickandmortyapi.com/api/episode/3",
                ),
            url = "https://rickandmortyapi.com/api/character/1",
            created = "2017-11-04T18:48:46.250Z",
        ),
    )
}

@Preview(showBackground = true)
@Composable
fun CharacterItemVerticalPreview() {
    CharacterItemBig(
            character = Character(
             id = 1,
            name = "Rick Sanchez",
            status = "Alive",
            species = "Human",
            type = "",
            gender = "Male",
            origin = Origin(
                name = "Earth (C-137)",
                url = "https://rickandmortyapi.com/api/location/1"
            ),
            location = Location(
                name = "Citadel of Ricks",
                url = "https://rickandmortyapi.com/api/location/3"
            ),
            image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
            episode = listOf(
                "https://rickandmortyapi.com/api/episode/1",
                "https://rickandmortyapi.com/api/episode/2",
                "https://rickandmortyapi.com/api/episode/3",
            ),
            url = "https://rickandmortyapi.com/api/character/1",
            created = "2017-11-04T18:48:46.250Z",
        ),
    )
}