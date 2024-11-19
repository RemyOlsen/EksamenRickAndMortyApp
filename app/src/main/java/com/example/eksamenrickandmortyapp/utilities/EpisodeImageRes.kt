package com.example.eksamenrickandmortyapp.utilities

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.eksamenrickandmortyapp.R

@Composable
fun EpisodeImage(
    episodeId: Int,
    modifier: Modifier
) {
    val episodeImage = getEpisodeImages(episodeId)

    if (episodeImage != null) {
        Image(
            modifier = modifier,
            painter = painterResource(id = episodeImage),
            contentDescription = "Episode_${episodeId} image",
            alignment = Alignment.Center,
            contentScale = ContentScale.Crop,
        )
    }
}

@Composable
fun getEpisodeImages(episodeId: Int): Int? {
    return when (episodeId) {
        1 -> R.drawable.episode_1
        2 -> R.drawable.episode_2
        3 -> R.drawable.episode_3
        4 -> R.drawable.episode_4
        5 -> R.drawable.episode_5
        6 -> R.drawable.episode_6
        7 -> R.drawable.episode_7
        8 -> R.drawable.episode_8
        9 -> R.drawable.episode_9
        10 -> R.drawable.episode_10
        11 -> R.drawable.episode_11
        12 -> R.drawable.episode_12
        13 -> R.drawable.episode_13
        14 -> R.drawable.episode_14
        15 -> R.drawable.episode_15
        16 -> R.drawable.episode_16
        17 -> R.drawable.episode_17
        18 -> R.drawable.episode_18
        19 -> R.drawable.episode_19
        20 -> R.drawable.episode_20
        21 -> R.drawable.episode_21
        22 -> R.drawable.episode_22
        23 -> R.drawable.episode_23
        24 -> R.drawable.episode_24
        25 -> R.drawable.episode_25
        26 -> R.drawable.episode_26
        27 -> R.drawable.episode_27
        28 -> R.drawable.episode_28
        29 -> R.drawable.episode_29
        30 -> R.drawable.episode_30
        31 -> R.drawable.episode_31
        32 -> R.drawable.episode_32
        33 -> R.drawable.episode_33
        34 -> R.drawable.episode_34
        35 -> R.drawable.episode_35
        36 -> R.drawable.episode_36
        37 -> R.drawable.episode_37
        38 -> R.drawable.episode_38
        39 -> R.drawable.episode_39
        40 -> R.drawable.episode_40
        41 -> R.drawable.episode_41
        42 -> R.drawable.episode_42
        43 -> R.drawable.episode_43
        44 -> R.drawable.episode_44
        45 -> R.drawable.episode_45
        46 -> R.drawable.episode_46
        47 -> R.drawable.episode_47
        48 -> R.drawable.episode_48
        49 -> R.drawable.episode_49
        50 -> R.drawable.episode_50
        51 -> R.drawable.episode_51
        else -> null // Return null if no image exists for the given ID
    }
}
