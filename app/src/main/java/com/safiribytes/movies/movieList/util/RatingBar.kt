package com.safiribytes.movies.movieList.data.domain.util

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    starModifier: Modifier = Modifier,
    rating: Double = 0.0,
    stars: Int = 5,
    starColor: Color = Color.Yellow
) {
    val filledStars = kotlin.math.floor(rating).toInt()
    val unfilledStars = (stars - kotlin.math.ceil(rating)).toInt()
    val halfStar = !(rating.rem(1).equals(0.0))

    Row (modifier = modifier){
        repeat(filledStars) {
            Icon(
                modifier = starModifier,
                imageVector = Icons.Rounded.Star,
                contentDescription = null,
                tint = starColor
            )
        }
        if (halfStar) {
            Icon(
                modifier = starModifier,
                imageVector = Icons.Rounded.PlayArrow,
//                imageVector = Icons.Rounded.StarHalf,
                contentDescription = null,
                tint = starColor
            )
        }
        repeat(unfilledStars){
            Icon(
                modifier = starModifier,
                imageVector = Icons.Outlined.Star,
//                imageVector = Icons.Rounded.StarOutlined,
                contentDescription = null,
                tint = starColor
            )
        }
    }
}