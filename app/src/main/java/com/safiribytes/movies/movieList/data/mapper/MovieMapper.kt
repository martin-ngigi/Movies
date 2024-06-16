package com.safiribytes.movies.movieList.data.mapper

import com.safiribytes.movies.movieList.data.local.movie.MovieEntity
import com.safiribytes.movies.movieList.data.remote.respnod.MovieDTO
import com.safiribytes.movies.movieList.data.domain.model.Movie
import java.lang.Exception


fun MovieDTO.toMovieEntity(category: String) : MovieEntity {
    return  MovieEntity(
        adult = adult ?: false,
        backdrop_path = backdrop_path ?: "",
        original_language = original_language ?: "",
        overview =  overview ?: "",
        poster_path = poster_path ?:"",
        release_date = release_date ?: "",
        title = title ?: "",
        vote_average = vote_average ?: 0.0,
        popularity = popularity ?: 0.0,
        vote_count = vote_count ?: 0,
        id = id ?: -1,
        original_title = original_title ?: "",
        video = video ?: false,

        category = category,

        genre_ids = try {
            genre_ids?.joinToString(",") ?: "-1,-2"
        }
        catch (e: Exception) {
            "-1,-2"
        }
    )
}
fun MovieEntity.toMovie(category: String): Movie {
    genre_ids.split("")
    return  Movie(
        backdrop_path = backdrop_path,
        original_language = original_language,
        overview = overview,
        poster_path = poster_path,
        release_date = release_date,
        title = title,
        vote_average = vote_average,
        popularity = popularity,
        vote_count = vote_count,
        video = video,
        id = id,
        adult = adult,
        original_title = original_title,
        category = category,
        genre_ids = try {
            genre_ids.split(",").map {  it.toInt()}
        }
        catch (e: Exception) {
            listOf(-1, -2)
        }
    )
}