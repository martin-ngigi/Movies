package com.safiribytes.movies.core.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.safiribytes.movies.R
import com.safiribytes.movies.movieList.data.domain.util.Screen
import com.safiribytes.movies.movieList.prentation.MovieListState
import com.safiribytes.movies.movieList.prentation.MovieListUIEvent
import com.safiribytes.movies.movieList.prentation.MovieListViewModel
import com.safiribytes.movies.movieList.prentation.PopularMovieScreen
import com.safiribytes.movies.movieList.prentation.UpcomingMovieScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomScreen(navController: NavHostController) {
    val moviesListViewModel = hiltViewModel<MovieListViewModel>()
    val movieState = moviesListViewModel.movieListState.collectAsState().value
    val bottomNavController = rememberNavController()

    Scaffold(
        bottomBar = {
                    BottomNavigationBar(
                        bottomNavController = bottomNavController,
                        onEvent = moviesListViewModel::onEvent
                    )
        },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (movieState.isCurrentPopularScreen)
                            stringResource(R.string.popular_movies)
                        else
                            stringResource(R.string.upcoming_movies),
                        fontSize = 20.sp
                    )
                },
                modifier = Modifier.shadow(2.dp),
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    MaterialTheme.colorScheme.inverseOnSurface
                )
            )
        }
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(it)) {
            NavHost(
                navController = bottomNavController,
                startDestination = Screen.PopularMovieList.rout
            ) {
                composable(Screen.PopularMovieList.rout) {
                    PopularMovieScreen(
                        movieListState = movieState,
                        navController = navController,
                        onEvent = moviesListViewModel::onEvent
                    )
                }

                composable(Screen.UpcomingMovieList.rout) {
                    UpcomingMovieScreen(
                        movieListState = movieState,
                        navController = navController,
                        onEvent = moviesListViewModel::onEvent
                    )
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(
    bottomNavController: NavController,
    onEvent: (MovieListUIEvent) -> Unit
    ) {
   val items = listOf(
       BottomItem(
           title = stringResource(R.string.popular),
           icon = Icons.Rounded.PlayArrow
       ),

       BottomItem(
           title = stringResource(R.string.upcoming),
           icon = Icons.Rounded.Star
       )
   )

    val selected = rememberSaveable {
        mutableIntStateOf(0)
    }

    NavigationBar {
        Row (
            modifier = Modifier.background(MaterialTheme.colorScheme.inverseOnSurface)
        ){
            items.forEachIndexed { index, bottomItem -> 
                NavigationBarItem(
                    selected = selected.intValue == index ,
                    onClick = {
                           selected.intValue = index
                        when(selected.value){
                            0 -> {
                                onEvent(MovieListUIEvent.Navigate)
                                bottomNavController.popBackStack()
                                bottomNavController.navigate(Screen.PopularMovieList.rout)
                            }
                             1 -> {
                                 onEvent(MovieListUIEvent.Navigate)
                                 bottomNavController.popBackStack()
                                 bottomNavController.navigate(Screen.UpcomingMovieList.rout)
                             }
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = bottomItem.icon,
                            contentDescription = bottomItem.title,
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    },
                    label = {
                        Text(
                            text = bottomItem.title,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                )
            }
        }
    }


}

data class BottomItem(
    val title: String,
    val icon: ImageVector
)