package de.fra_uas.fb2.mobiledevices.abeautifulmind

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private lateinit var radioGroup: RadioGroup
    private var myscore = 0;
    private var opponementScore = 0;
    private var increment = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        radioGroup = findViewById(R.id.radioGroup)
        val radio_nash = findViewById<RadioButton>(R.id.radio_Nash)
        val radio_random = findViewById<RadioButton>(R.id.radio_Random)
        val radio_greedy = findViewById<RadioButton>(R.id.radio_Greedy)
        val radio_cautious = findViewById<RadioButton>(R.id.radio_Cautious)
        val nashImage = findViewById<ImageView>(R.id.IvnashImage)

        // Wenn man Nash auswählt, zeigen wir ein foto
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
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



        // Punkte für mein score
        val tvMyScorePoints = findViewById<TextView>(R.id.tvMyScorePoints) //TextField
        val text1 = intent.getIntExtra("action",0)
        myscore = text1
        tvMyScorePoints.text = text1.toString()

        // Punkte für Oppponent score
        val tvOppnentScorePoints = findViewById<TextView>(R.id.tvOppnentScorePoints)
        val text2 = intent.getIntExtra("action2",0)
        opponementScore = text2
        tvOppnentScorePoints.text = text2.toString()

        val zero = 0



        val btnGenerateGame = findViewById<Button>(R.id.btnGenerateGame)

        val tvAmountGamesPlayed = findViewById<TextView>(R.id.tvAmountGamesPlayed)
        var counter = 0

        btnGenerateGame.setOnClickListener {
            //Wenn in radioGroup nichts ausgewählt worden ist, Toast anzeigen
            if (radioGroup.checkedRadioButtonId == -1) {
                val toast = Toast.makeText(this, "Select an opponement type first!", Toast.LENGTH_SHORT)
                toast.show()
            } else{
                //Übergebe mein Radiogroup an die nächste Activity und gehe in der nächte activity
                val intent = Intent(this, GameActivity::class.java)
                intent.putExtra("myscore",myscore)
                intent.putExtra("opponementScore",opponementScore)
                val selectedOption = radioGroup.checkedRadioButtonId
                intent.putExtra("selectedOption", selectedOption)
                counter++
                tvAmountGamesPlayed.text = counter.toString()
                startActivity(intent)
            }
        }

        val startOver = findViewById<Button>(R.id.btnStartOver)
        startOver.setOnClickListener{
            myscore = zero
            opponementScore = zero
            tvMyScorePoints.text = "0"
            tvOppnentScorePoints.text = "0"

        }


    }
}