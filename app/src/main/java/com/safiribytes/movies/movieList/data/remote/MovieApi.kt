package com.safiribytes.movies.movieList.data.remote

import com.safiribytes.movies.movieList.data.remote.respnod.MovieDTO
import com.safiribytes.movies.movieList.data.remote.respnod.MovieListDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET("movie/{category}")
    suspend fun getMoviesList(
        @Path("category") category: String,
        @Query("page") page: Int,
        @Query("api_ke") apiKey: String =  API_KEY
    ): MovieListDTO

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/"
        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"
        const val API_KEY = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIzOWUxMzkwMzU5MzE0Y2MyODgyNjYwZTZkNTk5ZDAzNyIsInN1YiI6IjY2NTdlYjlkNTE3MjViMzUxYjkzZGRlYyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.d340DJcRloO97pxRmiICoLQ9uny_T_2slhOQ5aVBJzc"
    }
}