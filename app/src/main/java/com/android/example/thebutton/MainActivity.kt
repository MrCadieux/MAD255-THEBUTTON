package com.android.example.thebutton

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Display
import android.view.Gravity
import android.view.HapticFeedbackConstants
import android.view.View
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.google.android.material.snackbar.Snackbar
import kotlin.math.roundToInt


class MainActivity : AppCompatActivity() {

    private lateinit var scoreLable: TextView
    private lateinit var timeLable: TextView
    private lateinit var screenSize: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var screenBackground: ConstraintLayout
    private lateinit var redButton: ImageButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Data passed from Directions Activity
        val playerName = intent.getStringExtra("playerName")

        //Inflate the custom Layout Toast
        val welcomeMessage = findViewById<LinearLayout>(R.id.startmessage)
        val layout: View = LinearLayout.inflate(this, R.layout.custom_toast, welcomeMessage)

        Toast(this).apply {
            duration = Toast.LENGTH_LONG
            setGravity(Gravity.TOP, 0, 500)
            view = layout
        }.show()


        //Components on Screen
        redButton = findViewById(R.id.redButton)
        scoreLable = findViewById(R.id.scoreView)
        timeLable = findViewById(R.id.percentLeftView)
        screenSize = findViewById(R.id.screenTextView)
        progressBar = findViewById(R.id.opacityProgress)
        screenBackground = findViewById(R.id.background)

        //Variables needed to run game clicks, percentage, opacity, and timer
        var numClicked = 0
        var buttonSize = 1000
        var a: Float
        var millisInFuture = 10000
        var countDownInterval = 50

        val winloseScreen = Intent(this, winlose::class.java).apply{
            //putExtra("NumberofClicks", numClicked)
            putExtra("playerName", playerName)
        }

        //Timer for the button to fade away
        val timer = object: CountDownTimer(millisInFuture.toLong(), countDownInterval.toLong()){
            override fun onTick(timeLeft: Long) {
                a=timeLeft/millisInFuture.toFloat()
                redButton.setAlpha(a)
                a = ((a* 1000.0).roundToInt() / 10.0).toFloat()
                timeLable.text = "${a}%"
                progressBar.progress = a.toInt()
            }
            override fun onFinish() {
                val loser = Toast.makeText(this@MainActivity, "You lost The Button", Toast.LENGTH_LONG)
                loser.setGravity(Gravity.TOP, 20, 500)
                loser.show()
                startActivity(winloseScreen)
            }
        }

        //Screen sizes
        val NAVIGATION_HEIGHT = 48
        val screenWidth = applicationContext.resources.displayMetrics.widthPixels
        val screenHeight = applicationContext.resources.displayMetrics.heightPixels


        //Displays Resolution of users play screen
        screenSize.text = "$screenWidth x $screenHeight"

        //Create the click sound
        val clickSound = MediaPlayer.create(this, R.raw.typewriter_click)

        //THE BUTTONS click listener
        redButton.setOnClickListener {
            numClicked++
            winloseScreen.putExtra("NumberofClicks", numClicked)
            if (numClicked%2==0&&buttonSize>100){
                buttonSize-=15
            }else if (numClicked%2==0&&buttonSize>50){
                buttonSize-=10
                Toast.makeText(this@MainActivity, "$playerName, you have $numClicked clicks!", Toast.LENGTH_SHORT).show()
            }else if (numClicked%2==0&&buttonSize>=25){
                buttonSize-=5
                Toast.makeText(this@MainActivity, "$playerName, you have $numClicked clicks!", Toast.LENGTH_SHORT).show()
            }else if (numClicked%2==0&&buttonSize>0){
                buttonSize-=1
            }
            redButton.layoutParams.height = buttonSize
            redButton.layoutParams.width = buttonSize
            //Random X/Y locations for movement
            val randX = (0 .. screenWidth-redButton.width).random().toFloat()
            val randY = (0 .. screenHeight-redButton.height-NAVIGATION_HEIGHT).random().toFloat()

            //Reset timer and opacity
            a=1f
            timer.cancel()
            //Add number of clicks overall make the sound of clicking the button and display
            clickSound.start()
            scoreLable.text = "Clicks: $numClicked"
            //vibrate(requireViewById(R.id.theButton))

            redButton.animate().x(randX).y(randY)

            //Toast.makeText(this@MainActivity, "$numClicked click!", Toast.LENGTH_SHORT).show()
            timer.start()
        }//theButton onClickListener

    }//OnCreate

    private fun vibrate(view: View) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
    }

}