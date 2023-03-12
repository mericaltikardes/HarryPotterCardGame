package com.mericaltikardes.harrypottercardgame

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CongratulationsPageActivity : AppCompatActivity() {
    private lateinit var textViewCong: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_congratulations_page)
    }
}