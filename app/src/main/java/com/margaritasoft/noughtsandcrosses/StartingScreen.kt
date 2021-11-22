package com.margaritasoft.noughtsandcrosses

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class StartingScreen : AppCompatActivity() {

    val small: Button by lazy{findViewById(R.id.small)}
    val medium: Button by lazy {findViewById(R.id.medium)}
    val large: Button by lazy {findViewById(R.id.large)}

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

    fun startNewGame(scale: Scale) {
        intent = Intent(this, GameActivity::class.java)
        startActivity(intent)
    }
}