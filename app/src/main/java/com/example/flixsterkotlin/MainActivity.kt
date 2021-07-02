package com.example.flixsterkotlin

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import org.json.JSONException

const val MOVIE_EXTRA = "MOVIE_EXTRA"
private const val TAG = "MainActivity"
private const val MOVIE_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed"
class MainActivity : AppCompatActivity() {
    private lateinit var rvMovies: RecyclerView
    private val movies = mutableListOf<Movie>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvMovies = findViewById(R.id.rvMovies)

        val movieAdapter = MovieAdapter(this, movies)
        rvMovies.adapter = movieAdapter
        rvMovies.layoutManager = LinearLayoutManager(this)
        val client = AsyncHttpClient()
        client.get(MOVIE_URL, object: JsonHttpResponseHandler() {
            override fun onFailure(
                statusCode: Int, headers: Headers?, response: String?, throwable: Throwable?) {
                Log.e(TAG, "onFailure $statusCode")
            }

            override fun onSuccess(statusCode: Int, headers: Headers?, responseJson: JSON) {
                Log.i(TAG, "onSuccess $responseJson")
                try {
                    val movieJsonArray = responseJson.jsonObject.getJSONArray("results")
                    movies.addAll(Movie.fromJsonArray(movieJsonArray))
                    movieAdapter.notifyDataSetChanged()
                    Log.i(TAG, "Movie list $movies")
                } catch (e: JSONException) {
                    Log.e(TAG, "Encountered exception $e")
                }
            }
        })
    }
}