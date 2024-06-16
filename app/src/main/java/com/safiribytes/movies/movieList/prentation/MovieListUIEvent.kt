package com.safiribytes.movies.movieList.prentation

sealed interface MovieListUIEvent {
    data class Paginate(val  category: String): MovieListUIEvent
    object  Navigate: MovieListUIEvent
}