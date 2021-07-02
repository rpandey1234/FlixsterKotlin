package com.example.flixsterkotlin

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import org.json.JSONArray

@Parcelize
data class Movie(
    val movieId: Int,
    val voteAverage: Double,
    private val posterPath: String,
    private val backdropPath: String,
    val title: String,
    val overview: String) : Parcelable {
    @IgnoredOnParcel
    val posterImageUrl = "https://image.tmdb.org/t/p/w342/$posterPath"
    @IgnoredOnParcel
    val backdropImageUrl = "https://image.tmdb.org/t/p/w342/$backdropPath"

    companion object {
        fun fromJsonArray(movieJsonArray: JSONArray): List<Movie> {
            val movies = mutableListOf<Movie>()
            for (i in 0 until movieJsonArray.length()) {
                val movieJson = movieJsonArray.getJSONObject(i)
                movies.add(
                    Movie(
                        movieJson.getInt("id"),
                        movieJson.getDouble("vote_average"),
                        movieJson.getString("poster_path"),
                        movieJson.getString("backdrop_path"),
                        movieJson.getString("title"),
                        movieJson.getString("overview"),
                    )
                )
            }
            return movies
        }
    }
}
