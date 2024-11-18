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
import com.example.eksamenrickandmortyapp.data.entities.Character
import com.example.eksamenrickandmortyapp.data.entities.Location
import com.example.eksamenrickandmortyapp.data.entities.Origin

@Composable
fun CharacterItemList(
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
                when (character.status) {
                    "Alive" -> Color(0xFF64ff47)
                    "Dead" -> Color(0xFFff4947)
                    else -> Color(0xFFd7dcd6)
                }
            )
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = Modifier
                .size(108.dp, 108.dp)
                .background(color = Color.Gray)
            ,
            model = character.image,
            contentDescription = "Image of ${character.name}",
            alignment = Alignment.Center,
            contentScale = ContentScale.Crop,
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp),
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
            ) {

                //# Name
                Text(

                    modifier = Modifier.width(220.dp),
                    text = character.name,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF031400),
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis

                )
                //# Character ID
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterEnd),
                    text = "#${character.id}",
                    fontSize = 12.sp
                )
            }

            Spacer( modifier = Modifier.height(2.dp) )

            //# Species and Gender
            Text(
                text = "${character.species} - ${character.gender}",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer( modifier = Modifier.height(2.dp) )

            //# Location Name
            Text(
                text = character.location.name,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
//            Text(
//                text = "Status: ${character.status}",
//                fontSize = 14.sp
//            )


        }
    } // Row
}

@Composable
fun CharacterItemCard(
    character: Character,
    onClick: () -> Unit = {}
) {
    
    val characterStatusColor =
        when (character.status) {
            "Alive" -> Color(0xFF64ff47)
            "Dead" -> Color(0xFFff4947)
            else -> Color(0xFFd7dcd6)
        }

    val textColor =
        when (character.status) {
            "Dead" -> Color.White
            else -> Color.Black
        }

    Column(
        modifier = Modifier
            .width(196.dp)
            .padding(12.dp)
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(5))
            .background(color = characterStatusColor)
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box() {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .size(156.dp)
                    .background(color = Color.Gray)
                    ,
                model = character.image,
                contentDescription = "Image of ${character.name}",
                alignment = Alignment.Center,
                contentScale = ContentScale.Crop,
            )

            //# Character ID
            Text(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .background(
                        color = characterStatusColor,
                        shape = RoundedCornerShape(topStart = 10.dp),
                    )
                ,
                text = "#${character.id}",
                fontSize = 12.sp,
                color = textColor
            )

        }

        Column(
            modifier = Modifier.padding(3.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
            ) {

                // #Name
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = character.name,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = textColor,
                    fontSize = 16.sp
                )

            }

            Spacer(modifier = Modifier.height(1.dp))

            //# Species
            Text(
                text = "${character.species} - ${character.gender}",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = textColor,
                fontSize = 14.sp
            )

//            Spacer(modifier = Modifier.height(1.dp))
            
            //# Location Name
            Text(
                text = character.location.name,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = textColor,
                fontSize = 14.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CharacterItemPreview() {
    CharacterItemList(
        character = Character(
            id = 1,
            name = "Rick Sanchez reeeeeeeeaaaaaaaaaaally long name to make issues with overflow yas qweeeeeeen",
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
    CharacterItemCard(
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