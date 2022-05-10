package com.android.example.thebutton

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class Directions : AppCompatActivity() {

    private lateinit var name: EditText
    private lateinit var startButton: ImageView
    private lateinit var directions: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_directions)

        name = findViewById(R.id.nameInput)

        startButton = findViewById(R.id.buttonImage)
        directions = findViewById(R.id.directionsView)

        startButton.setOnClickListener {

            if (name.text.toString().isEmpty()){
                name.error = "Please Enter Your Name"
                name.requestFocus()
                Toast.makeText(applicationContext, "Please Enter Your Name", Toast.LENGTH_LONG).show()
            }
            else{
                val gameScreen = Intent(this, MainActivity::class.java).apply {
                    putExtra("playerName", name.text.toString())
                }
                startActivity(gameScreen)
            }
        }


    }



}

