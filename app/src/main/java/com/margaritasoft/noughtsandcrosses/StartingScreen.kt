package com.margaritasoft.noughtsandcrosses

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class StartingScreen : AppCompatActivity() {

    private val small: Button by lazy{findViewById(R.id.small)}
    private val medium: Button by lazy {findViewById(R.id.medium)}
    private val large: Button by lazy {findViewById(R.id.large)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_starting_screen)

        small.setOnClickListener {
            startNewGame(Scale.SMALL)
        }

        medium.setOnClickListener {
            startNewGame(Scale.MEDIUM)
        }

        large.setOnClickListener {
            startNewGame(Scale.LARGE)
        }

    }

    private fun startNewGame(scale: Scale) {
        intent = Intent(this, GameActivity::class.java)
        intent.putExtra("scale", scale)
        startActivity(intent)
    }
}