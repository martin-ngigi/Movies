package com.safiribytes.movies.details.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmap
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.safiribytes.movies.R
import com.safiribytes.movies.movieList.data.domain.util.RatingBar
import com.safiribytes.movies.movieList.data.domain.util.getAverageColor
import com.safiribytes.movies.movieList.data.remote.MovieApi

@Composable
fun DetailsScreen() {
    val detailsViewModel = hiltViewModel<DetailsViewModel>()
    val detailsState = detailsViewModel.detailsState.collectAsState().value
    val backDropImageState = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(MovieApi.IMAGE_BASE_URL + detailsState.movie?.backdrop_path)
            .size(Size.ORIGINAL)
            .build()
    ).state

    val posterImageState = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(MovieApi.IMAGE_BASE_URL + detailsState.movie?.poster_path)
            .size(Size.ORIGINAL)
            .build()
    ).state

    Column (
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ){
        // Top Image
        if(backDropImageState is AsyncImagePainter.State.Error) {
            Box (
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ){
                Icon(
                    modifier = Modifier.size(70.dp),
                    imageVector = Icons.Rounded.Warning,
                    contentDescription = detailsState.movie?.title
                )
            }
        }

        if(backDropImageState is AsyncImagePainter.State.Success) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp),
                painter = backDropImageState.painter,
                contentDescription = detailsState.movie?.title,
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ){
            Box(
                modifier = Modifier
                    .width(160.dp)
                    .height(240.dp)
            ) {
                // poster image
                if (posterImageState is AsyncImagePainter.State.Error) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(12.dp))
                            .background(MaterialTheme.colorScheme.primaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            modifier = Modifier.size(70.dp),
                            imageVector = Icons.Rounded.Warning,
                            contentDescription = detailsState.movie?.title
                        )
                    }
                }

                if (posterImageState is AsyncImagePainter.State.Success) {
                    Image(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(12.dp)),
                        painter = posterImageState.painter,
                        contentDescription = detailsState.movie?.title,
                        contentScale = ContentScale.Crop
                    )
                }
            }

                // Details of image
                detailsState.movie?.let { movie ->
                    Column (
                        modifier = Modifier.fillMaxWidth()
                    ){
                        /** Title*/
                        Text(
                            modifier = Modifier.padding(start = 16.dp),
                            text = movie.title,
                            fontSize = 19.sp,
                            fontWeight =  FontWeight.SemiBold
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        //Rating bar
                        Row (
                            modifier = Modifier
                                .padding(start = 16.dp)
                        ){
                            RatingBar(
                                starModifier = Modifier.padding(10.dp),
                                rating = movie.vote_average / 2
                            )

                            androidx.compose.material.Text(
                                modifier = Modifier.padding(start = 4.dp),
                                text = movie.vote_average.toString().take(3),
                                color = Color.LightGray,
                                fontSize = 14.sp,
                                maxLines = 1
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Language
                        Text(
                            modifier = Modifier.padding(start = 16.dp),
                            text = stringResource(R.string.language) + movie.original_language,
                        )


                        Spacer(modifier = Modifier.height(10.dp))

                        // Release date
                        Text(
                            modifier = Modifier.padding(start = 16.dp),
                            text = stringResource(R.string.release_date) + movie.release_date,
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        // Votes
                        Text(
                            modifier = Modifier.padding(start = 16.dp),
                            text = "${movie.vote_count }" + stringResource(R.string.votes) ,
                        )

                }
            }
        }

        Spacer(modifier = Modifier.height(22.dp))

        // Overview
        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = stringResource(R.string.overview),
            fontSize = 19.sp,
            fontWeight =  FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = stringResource(R.string.overview),
            fontSize = 19.sp,
            fontWeight =  FontWeight.SemiBold
        )

        detailsState.movie?.let {

            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = it.overview,
                fontSize = 16.sp,
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
    }



}
