package de.fra_uas.fb2.mobiledevices.abeautifulmind

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private lateinit var radioGroup: RadioGroup

    //Globale Variable erstellt
     class Global : Application() {
        companion object {
            var game_counter = 0
            var myscore = 0
            var myopponent = 0
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        radioGroup = findViewById(R.id.radioGroup)
        val nashImage = findViewById<ImageView>(R.id.IvnashImage)
        val gamecounterView = findViewById<TextView>(R.id.tvAmountGamesPlayed)

        //Score Views
        val tvMyScorePoints = findViewById<TextView>(R.id.tvMyScorePoints) //TextField
        val tvOppnentScorePoints = findViewById<TextView>(R.id.tvOppnentScorePoints)

        val btnGenerateGame = findViewById<Button>(R.id.btnGenerateGame)
        val startOver = findViewById<Button>(R.id.btnStartOver)


        // Foto anzeigen wenn Nash ausgewählt wird, annsonsten ausblenden
        radioGroup.setOnCheckedChangeListener {_,checkedId ->
            when (checkedId) {
                R.id.radio_Nash -> {
                    nashImage.visibility = View.VISIBLE
                }
                R.id.radio_Greedy -> {
                    nashImage.visibility = View.GONE
                }
                R.id.radio_Cautious -> {
                    nashImage.visibility = View.GONE
                }
                R.id.radio_Random -> {
                    nashImage.visibility = View.GONE
                }
            }
        }


        //MyScore TextView
        tvMyScorePoints.text = Global.myscore.toString()

        //Opponent TextView
        tvOppnentScorePoints.text = Global.myopponent.toString()

        //Games Counter View
        gamecounterView.text = Global.game_counter.toString()




        btnGenerateGame.setOnClickListener {
            //Wenn in radioGroup nichts ausgewählt worden ist, Toast anzeigen
            if (radioGroup.checkedRadioButtonId == -1) {
                val toast = Toast.makeText(this, "Select an opponement type first!", Toast.LENGTH_SHORT)
                toast.show()
            } else{
                //Übergebe mein Radiogroup an die nächste Activity und gehe in der nächte activity
                val intent = Intent(this, GameActivity::class.java)
                val selectedOption = radioGroup.checkedRadioButtonId
                intent.putExtra("selectedOption", selectedOption)
                startActivity(intent)
            }
        }

        // Scores und Gespielte spiele auf 0 zurücksetzen
        startOver.setOnClickListener{
            gamecounterView.text = "0"
            Global.game_counter = 0
            tvMyScorePoints.text = "0"
            tvOppnentScorePoints.text = "0"

        }
    }
}