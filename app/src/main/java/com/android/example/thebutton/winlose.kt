package com.android.example.thebutton

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import kotlin.math.roundToInt

class winlose : AppCompatActivity() {

    private lateinit var outMessage: TextView
    private lateinit var fadingButton: ImageView
    private lateinit var againButton: Button
    private lateinit var goodClicks: TextView
    private lateinit var badClicks: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_winlose)

        //Data passed from MainActivity
        val playerName = intent.getStringExtra("playerName")
        val numClicked = intent.getIntExtra("NumberofClicks",0)

        val nullNamecheck = playerName


        outMessage = findViewById(R.id.endGameMessage)
        fadingButton = findViewById(R.id.fadeButtonImage)
        againButton = findViewById(R.id.againButton)
        goodClicks = findViewById(R.id.goodClicks)
        badClicks = findViewById(R.id.badClicks)

        var a: Float
        val playAgain = Intent (this, Directions::class.java)

        //Timer for the button to fade away
        val timer = object: CountDownTimer(1000, 200){
            override fun onTick(timeLeft: Long) {
                a=timeLeft/1000.toFloat()
                fadingButton.setAlpha(a)
            }
            override fun onFinish() {
                this.start()
            }
        }

        timer.start()

        outMessage.text = "$playerName, you lost The Button with a total of $numClicked clicks"

        againButton.setOnClickListener {

            startActivity(playAgain)
        }

    }

}