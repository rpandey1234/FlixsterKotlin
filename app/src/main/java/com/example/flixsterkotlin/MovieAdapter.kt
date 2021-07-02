package com.example.flixsterkotlin

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bumptech.glide.Glide

private const val TAG = "MovieAdapter"
class MovieAdapter(private val context: Context, private val movies: List<Movie>) : Adapter<MovieAdapter.ViewHolder>() {

    // Expensive: inflates a view and creates a ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.i(TAG, "onCreateViewHolder")
        val view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false)
        return ViewHolder(view)
    }

    // Cheap: simply bind data to an existing ViewHolder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.i(TAG, "onBindViewHolder $position")
        val movie = movies[position]
        holder.bind(movie)
    }

    override fun getItemCount() = movies.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val ivPoster = itemView.findViewById<ImageView>(R.id.ivPoster)
        private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        private val tvOverview = itemView.findViewById<TextView>(R.id.tvOverview)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(movie: Movie) {
            val isInLandscape = context.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
            val imageUrl = if (isInLandscape) movie.backdropImageUrl else movie.posterImageUrl
            Glide.with(context).load(imageUrl).into(ivPoster)
            tvTitle.text = movie.title
            tvOverview.text = movie.overview
        }

        override fun onClick(v: View?) {
            if (adapterPosition != RecyclerView.NO_POSITION) {
                val movie = movies[adapterPosition]
                Log.i(TAG, "onClickListener for movie ${movie.title}")
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra(MOVIE_EXTRA, movie)
                context.startActivity(intent)
            }
        }
    }
}
