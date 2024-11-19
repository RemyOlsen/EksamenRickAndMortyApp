package com.example.eksamenrickandmortyapp.screens.episode_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eksamenrickandmortyapp.data.entities.Episode
import com.example.eksamenrickandmortyapp.utilities.EpisodeImage
import com.example.eksamenrickandmortyapp.utilities.getEpisodeImages


@Composable
fun EpisodeItem(
    episode: Episode,
    onClick: () -> Unit = {}
) {

    Column(
        modifier = Modifier
            .width(196.dp)
            .padding(12.dp)
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(5)
            )
            .background(color = Color(0xFFf2fefc))
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box() {

            EpisodeImage(
                modifier = Modifier
                .fillMaxWidth()
                .size(156.dp)
                .background(color = Color.White),
                episodeId = episode.id,
            )
//            Image(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .size(156.dp)
//                    .background(color = Color.White)
//                ,
//                painter = painterResource(id = R.drawable.episode_1),
//                contentDescription = "Image of ${episode.name}",
//                alignment = Alignment.Center,
//                contentScale = ContentScale.Crop,
//
//            )

            //# Character ID
            Text(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .background(
                        color = Color(0xFFf2fefc)
                        ,
                        shape = RoundedCornerShape(topStart = 10.dp),
                    )
                ,
                text = "#${episode.id}",
                fontSize = 12.sp,
                color = Color.Black
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
                    text = episode.name,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.Black,
                    fontSize = 16.sp
                )

            }

            Spacer(modifier = Modifier.height(1.dp))

            //# Air Date
            Text(
                text = episode.airDate,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Color.Black,
                fontSize = 14.sp
            )

//            Spacer(modifier = Modifier.height(1.dp))

            //# Episode
            Text(
                text = episode.episode,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Color.Black,
                fontSize = 14.sp
            )
        }
    }
}