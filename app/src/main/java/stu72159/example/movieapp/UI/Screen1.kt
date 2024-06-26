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
import stu72159.example.movieapp.AppAdapter
import stu72159.example.movieapp.AppAdapter.Companion.itemposition
import com.example.movieapp.databinding.ActivityScreen1Binding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlin.random.Random

class Screen1 : AppCompatActivity() {
    lateinit var binding: ActivityScreen1Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScreen1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val crashButton = Button(this)
        crashButton.text = "Test Crash"
        crashButton.setOnClickListener {
            throw RuntimeException("Test Crash") // Force a crash
        }

        addContentView(crashButton, ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT))

        val jsonString = readJsonFromAssets("movie.json")
        val movielist = parseJsonToModel(jsonString)

        // Generate random available seats for each movie
        for (movie in movielist) {
            movie.availableSeats = Random.nextInt(1, 16).toString()
        }

        val adapter = AppAdapter(movielist, this@Screen1)
        binding.rvMovies.adapter = adapter

        Log.d("list--> ", "$movielist   c ")
    }

    fun readJsonFromAssets(fileName: String): String {
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
        if (requestCode == AppAdapter.REQUEST_CODE_MOVIE_DETAIL && resultCode == Activity.RESULT_OK) {
            val modifiedAvailableSeats = data?.getStringExtra("modifiedAvailableSeats")
            val modifiedSelectedSeats = data?.getStringExtra("modifiedSelectedSeats")

            Log.d("datacheck: ", "$modifiedAvailableSeats $modifiedSelectedSeats $itemposition")

            // Update the seat data for the corresponding movie item in your RecyclerView
            // You can access the ViewHolder of the clicked item using findViewHolderForAdapterPosition
            val viewHolder = binding.rvMovies.findViewHolderForAdapterPosition(itemposition)
            if (viewHolder is AppAdapter.ViewHolder) {
                viewHolder.binding.availableSeats.text = "Available Seats: $modifiedAvailableSeats"
                viewHolder.binding.selectedseat.text = "Selected Seats: $modifiedSelectedSeats"
            }
        }
    }
}
