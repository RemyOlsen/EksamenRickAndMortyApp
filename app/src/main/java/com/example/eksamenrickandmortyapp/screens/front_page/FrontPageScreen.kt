package com.example.eksamenrickandmortyapp.screens.front_page

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eksamenrickandmortyapp.R
import com.example.eksamenrickandmortyapp.screens.character_list.CharacterListViewModel
import java.time.format.TextStyle



@Preview(showBackground = true)
@Composable
fun FrontPageScreen(
//    viewModel: CharacterListViewModel,
//    onCharacterClick: (characterId: Int) -> Unit = {},
    onCharacterListClick: () -> Unit = {},
    onCustomCharacterListClick: () -> Unit = {}
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray)
    ) {
        // Background Image
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.rickandmortyfront),
            contentDescription = "Rick and Morty Background",
            contentScale = ContentScale.Crop
        )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(enabled = true, state = rememberScrollState())
                ,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                // Logo
                Image(
                    modifier = Modifier
                        .width(500.dp)
                    ,
                    painter = painterResource(id = R.drawable.rickandmortylogo),
                    contentDescription = "Rick and Morty Logo",
                    contentScale = ContentScale.Inside
                )


                MenuButton(
                    buttonText = "Characters",
                    onClick = {
                        onCharacterListClick()
                    }
                )
                MenuButton(
                    buttonText = "Custom Characters",
                    onClick = {
                        onCustomCharacterListClick()
                    }
                )
            }
    }
}


@Composable
fun MenuButton(
    buttonText: String,
    onClick: () -> Unit
) {

    val RickAndMortyFont = FontFamily(
        Font(R.font.wubba_lubba_dub_dub)
    )

    // I'm using a clickable box instead of a button, because it's less code since I need it if I want a gradient background
    Box(
        modifier = Modifier
            .clickable { onClick() }
            .padding(16.dp)
            .height(60.dp)
            .fillMaxWidth()
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xCCbf7da5),
                        Color(0xCCecaba6),
                        Color(0xCCf8e99a)
                    )
                ),
                shape = RoundedCornerShape(50.dp)
            )
            .border(
                width = 2.dp,
                color = Color.Black,
                shape = RoundedCornerShape(50.dp)),
        contentAlignment = Alignment.Center
    ) {
        // Content
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = buttonText,
                color = Color.White,
                    fontFamily = RickAndMortyFont,
                fontSize = 32.sp
            )
        }
    }
}

@Preview
@Composable
fun MenuButtonPreview() {
    MenuButton(
        buttonText = "Characters",
//        fontFamily = FontFamily.Default,
        onClick = {}
    )
}
