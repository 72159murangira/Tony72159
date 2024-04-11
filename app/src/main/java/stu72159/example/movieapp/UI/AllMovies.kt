package stu72159.example.movieapp.UI

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Button
import stu72159.example.movieapp.Models.MovieData
import stu72159.example.movieapp.MovieAdapter
import stu72159.example.movieapp.MovieAdapter.Companion.itemposition
import com.example.movieapp.databinding.ActivityAllMoviesBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class AllMovies : AppCompatActivity() {
    lateinit var binding: ActivityAllMoviesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityAllMoviesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val crashButton = Button(this)
        crashButton.text = "Test Crash"
        crashButton.setOnClickListener {
            throw RuntimeException("Test Crash") // Force a crash
        }

        addContentView(crashButton, ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT))
        val jsonString = readJsonFromAssets( "movie.json")
        val movielist = parseJsonToModel(jsonString)

        val adapter = MovieAdapter(movielist,this@AllMovies)
        binding.rvMovies.adapter = adapter



        Log.d("list--> ", "$movielist   c ")

    }

    fun readJsonFromAssets( fileName: String): String {
        return assets.open(fileName).bufferedReader().use { it.readText() }
    }
    fun parseJsonToModel(jsonString: String): List<MovieData> {
        val gson = Gson()
        return gson.fromJson(jsonString, object : TypeToken<List<MovieData>>() {}.type)
    }

    @SuppressLint("SetTextI18n")
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == MovieAdapter.REQUEST_CODE_MOVIE_DETAIL && resultCode == Activity.RESULT_OK) {
            val modifiedavailableSeats = data?.getStringExtra("modifiedavailableSeats")
            val modifiedSelectedSeats = data?.getStringExtra("modifiedSelectedSeats")

            Log.d("datacheck: ", "$modifiedavailableSeats $modifiedSelectedSeats $itemposition")
            // Update the seat data for the corresponding movie item in your RecyclerView
            // You can access the ViewHolder of the clicked item using findViewHolderForAdapterPosition
            val viewHolder = binding.rvMovies.findViewHolderForAdapterPosition(itemposition)
            if (viewHolder is MovieAdapter.ViewHolder) {
                viewHolder.binding.availableSeats.text = "available Seats: $modifiedavailableSeats"
                viewHolder.binding.selectedseat.text = "Selected Seats: $modifiedSelectedSeats"

            }
        }
    }

}