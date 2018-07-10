package com.prateek.numbers

import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.prateek.numbers.services.FetchService
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        fun backGroundColor() {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = ContextCompat.getColor(this, android.R.color.transparent)
            window.navigationBarColor = ContextCompat.getColor(this, android.R.color.transparent)
            window.setBackgroundDrawableResource(R.drawable.main_gradient)
        }
        backGroundColor()
        setContentView(R.layout.activity_main)
        resultTxt.visibility = View.INVISIBLE
        shareBtn.visibility = View.INVISIBLE
        progressBar.visibility = View.INVISIBLE

        searchBtn.setOnClickListener {
            if (numberTxt.text.trim().isNotEmpty()) {
                progressBar.visibility = View.VISIBLE
                searchBtn.isEnabled = false
                FetchService.getData(this, numberTxt.text.trim().toString()) {isSuccess ->

                    if (isSuccess) {
                        resultTxt.text = FetchService.result
                        resultTxt.visibility = View.VISIBLE
                        shareBtn.visibility = View.VISIBLE
                        progressBar.visibility = View.INVISIBLE
                        searchBtn.isEnabled = true
                    } else {
                        progressBar.visibility = View.INVISIBLE
                        Toast.makeText(this, "Nothing found for this number.", Toast.LENGTH_SHORT).show()
                    }

                }
            } else {
                Toast.makeText(this, "Enter a number first.", Toast.LENGTH_SHORT).show()
            }
        }

        shareBtn.setOnClickListener {
            val shareIntent = Intent(android.content.Intent.ACTION_SEND)
            shareIntent.setType("text/plain")
            val shareString = "Fact about ${numberTxt.text}: \n\n${resultTxt.text}.\n\nThank me later."
            shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Share Fact")
            shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareString)
            startActivity(Intent.createChooser(shareIntent, "Share via"))
        }
    }
}
