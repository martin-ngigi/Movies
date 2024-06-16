package com.safiribytes.movies.movieList.data.repository

import android.net.http.HttpException
import com.safiribytes.movies.movieList.data.local.movie.MovieDatabase
import com.safiribytes.movies.movieList.data.mapper.toMovie
import com.safiribytes.movies.movieList.data.mapper.toMovieEntity
import com.safiribytes.movies.movieList.data.remote.MovieApi
import com.safiribytes.movies.movieList.data.domain.model.Movie
import com.safiribytes.movies.movieList.data.domain.repository.MovieListRepository
import com.safiribytes.movies.movieList.data.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class MovieListRepositoryImpl @Inject constructor(
    private  val movieApi: MovieApi,
    private  val movieDatabase: MovieDatabase
) : MovieListRepository {
    override suspend fun getMoviesList(
        forceFetchFromRemote: Boolean,
        category: String,
        page: Int
    ): Flow<Resource<List<Movie>>> {
        return flow { // Flow is a sequence of actions.

            emit(Resource.Loading(true))

            /** if offline, get movies from local if not empty and forceFetchFromRemote is false*/
            val localMoviesList = movieDatabase.movieDAO.getMovieListByCategory(category)
            val shouldLoadLocalMovie = localMoviesList.isNotEmpty() && !forceFetchFromRemote
            if (shouldLoadLocalMovie) {
                emit(
                    Resource.Success (
                    data = localMoviesList.map {movieEntity ->
                        movieEntity.toMovie(category)
                    }
                ))
                emit(Resource.Loading(false))
                return@flow
            }

            /** Get movies from Remote*/
            val moviesListFromApi = try {
                movieApi.getMoviesList(category, page)
            }
            catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Oops! Something went wrong"))
                return@flow
            }
            catch (e: retrofit2.HttpException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Oops! Something went wrong"))
                return@flow
            }
            catch (e: retrofit2.HttpException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Oops! Something went wrong"))
                return@flow
            }
            val movieEntities = moviesListFromApi.results.let {
                it.map {movieDTO ->
                    movieDTO.toMovieEntity(category)
                }
            }
            movieDatabase.movieDAO.upsertMovieList(movieEntities)
            emit(
                Resource.Success(
                movieEntities.map { it.toMovie(category) }
            ))
            emit(Resource.Loading(false))
        }
    }

    override suspend fun getMovie(id: Int): Flow<Resource<Movie>> {
        return flow { // Flow is a sequence of actions.

            emit(Resource.Loading(true))

            val movieEntity = movieDatabase.movieDAO.getMovieById(id)

            if (movieEntity != null) {
                emit( Resource.Success(movieEntity.toMovie(movieEntity.category)))
                emit(Resource.Loading(false))
                return@flow
            }

            emit(Resource.Error("Oops! No such movie"))
            emit(Resource.Loading(false))


        }
    }
}