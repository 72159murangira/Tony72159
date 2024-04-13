package stu72159.example.movieapp.UI

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.databinding.ActivityMovieDetailBinding

class MovieDetail : AppCompatActivity() {
    lateinit var binding:ActivityMovieDetailBinding

    private var moviename:String?=null
    private var postimg:String?=null
    private var desc:String?=null
    private var availableSeats:String?=null
    private var selectedSeats:String?=null
    companion object
    {
        var selected_count=0
        var available_count=0
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        moviename=intent.getStringExtra("moviename")
        postimg=intent.getStringExtra("image")
        desc=intent.getStringExtra("desc")

        availableSeats=intent.getStringExtra("availableseats")
        selectedSeats=intent.getStringExtra("selectedseats")


        available_count = availableSeats?.replace(Regex("[^0-9]"), "")?.toIntOrNull() ?: 0


        binding.plusbtn.setOnClickListener {
            if (available_count >0)
            {
                selected_count++
                available_count--
            }
            else
            {
                Toast.makeText(this@MovieDetail, "No more seats available!", Toast.LENGTH_LONG).show()
            }



            binding.selectedseats.text="Selected Seats: $selected_count"
            binding.availableSeats.text="Available Seats: $available_count"
        }
        binding.minusbtn.setOnClickListener {
            if (selected_count >0)
            {
                selected_count--
                available_count++
            }
            else
            {
                Toast.makeText(this@MovieDetail,"Click on PLUS icon to select seats",Toast.LENGTH_LONG).show()
            }
            binding.selectedseats.text = "Selected Seats: $selected_count"
            binding.availableSeats.text = "Available Seats: $available_count"

        }

        binding.selectedseats.text=selectedSeats
        binding.availableSeats.text=availableSeats
        Glide.with(this@MovieDetail)
            .load(postimg)
            .into(binding.posterimg
            )

        binding.moviedesc.text=desc
        binding.titletv.text=moviename

    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val intent = Intent()
        intent.putExtra("modifiedAvailableSeats", available_count.toString())
        intent.putExtra("modifiedSelectedSeats", selected_count.toString())
        setResult(Activity.RESULT_OK, intent)
        super.onBackPressed()
    }
}