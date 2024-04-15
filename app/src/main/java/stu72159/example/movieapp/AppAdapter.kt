package stu72159.example.movieapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import stu72159.example.movieapp.Models.MovieData
import stu72159.example.movieapp.UI.Screen2
import com.example.movieapp.databinding.MovieItemBinding
import kotlin.random.Random

class AppAdapter(private val movies: List<MovieData>, private val context: Context) :
    RecyclerView.Adapter<AppAdapter.ViewHolder>() {

    companion object {
        const val REQUEST_CODE_MOVIE_DETAIL = 1001
        var itemposition = 0
        var randomno = 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie, position)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    inner class ViewHolder(val binding: MovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: MovieData, position: Int) {
            binding.apply {

                randomno = Random.nextInt(1, 16)

                if (randomno < 3) {
                    cardlayout.visibility = View.VISIBLE
                } else {
                    cardlayout.visibility = View.GONE
                }

                titletv.text = movie.title
                overviewtv.text = movie.overview
                runtime.text = movie.runtime
                availableSeats.text = "Available Seats: $randomno"
                selectedseat.text = "Selected Seats: 0"

                // Load poster image using Glide (or any other image loading library)
                Glide.with(itemView.context)
                    .load(movie.posterurl)
                    .into(posterimg)

                itemView.setOnClickListener {

                    itemposition = adapterPosition

                    Log.d("itempos: ", adapterPosition.toString() + " c")
                    val intent = Intent(context, Screen2::class.java)
                    intent.putExtra("moviename", movie.title)
                    intent.putExtra("image", movie.posterurl)
                    intent.putExtra("desc", movie.overview)
                    intent.putExtra("availableseats", "Available Seats: $randomno")
                    intent.putExtra("selectedseats", "Selected Seats: 0")

                    (context as Activity).startActivityForResult(intent, REQUEST_CODE_MOVIE_DETAIL)

                }
            }
        }
    }
}
