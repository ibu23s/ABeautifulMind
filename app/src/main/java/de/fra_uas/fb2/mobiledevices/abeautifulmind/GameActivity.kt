package de.fra_uas.fb2.mobiledevices.abeautifulmind

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class GameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val probe = findViewById<TextView>(R.id.probe)

        val tvStrategyType = findViewById<TextView>(R.id.tvStrategyType)

        //Nehmen den ausgewählten wert von der Radiogroup
        val intent = intent
        val selectedOption = intent.getIntExtra("selectedOption", 0)

        // Definition der boxex und wertebereich
        val tvPlayer11 = findViewById<TextView>(R.id.tvPlayer11)
        val Pair1 = generateNumbers(-5,5)
        val tvPlayer12 = findViewById<TextView>(R.id.tvPlayer12)
        val Pair2 = generateNumbers(-5,5)
        val tvPlayer21 = findViewById<TextView>(R.id.tvPlayer21)
        val Pair3 = generateNumbers(-5,5)
        val tvPlayer22 = findViewById<TextView>(R.id.tvPlayer22)
        val Pair4 = generateNumbers(-5,5)


        //Random zahlen in die textView anzeigen

        tvPlayer11.text = "${Pair1.first} / ${Pair1.second}"
        tvPlayer12.text = "${Pair2.first} / ${Pair2.second}"
        tvPlayer21.text = "${Pair3.first} / ${Pair3.second}"
        tvPlayer22.text = "${Pair4.first} / ${Pair4.second}"

        //Stellen den Richtigen text
        when (selectedOption) {
            R.id.radio_Random -> tvStrategyType.text = "Random"
            R.id.radio_Greedy -> tvStrategyType.text = "Greedy"
            R.id.radio_Cautious -> tvStrategyType.text = "Cautious"
            R.id.radio_Nash -> tvStrategyType.text = "Nash"
        }

        val btnActionA = findViewById<Button>(R.id.btnChooseActionA)
        val btnActionB = findViewById<Button>(R.id.btnChooseActionB)

        var tvYourScoreAction = findViewById<TextView>(R.id.tvYourScoreAction)
        var tvOponentScoreAction = findViewById<TextView>(R.id.tvOponentScoreAction)


        btnActionA.setOnClickListener {
            // Wird angezeigt ob man option a oder b gewählt wurde von nutzer
            val setTextChose = findViewById<TextView>(R.id.tvYouChoseAction)
            setTextChose.setText("A")


            // Button wird deaktiviert
            btnActionA.isEnabled = false
            btnActionB.isEnabled = false

            val tvOpponentChoseAction = findViewById<TextView>(R.id.tvOpponentChoseAction)

            // #####################################################     Random
            if (selectedOption == R.id.radio_Random) {

                val Pair5 = generateOponentChoose('A','B')
                tvOpponentChoseAction.text = "${Pair5.first}"

                val inside = tvOpponentChoseAction.text.toString()
                if (inside[0] == 'A') {
                    tvYourScoreAction.setText("${Pair1.first}")
                    tvOponentScoreAction.setText("${Pair1.second}")
                    tvPlayer11.setBackgroundColor(Color.GREEN)
                }
                val inside2 = tvOpponentChoseAction.text.toString()
                if (inside2[0] == 'B') {
                    tvYourScoreAction.setText("${Pair2.first}")
                    tvOponentScoreAction.setText("${Pair2.second}")
                    tvPlayer12.setBackgroundColor(Color.GREEN)
                }
            }
            // #####################################################      Greedy
            if (selectedOption == R.id.radio_Greedy) {
                when {
                    Pair2.second > Pair1.second && Pair2.second > Pair3.second && Pair2.second > Pair4.second -> {
                        tvOponentScoreAction.text = Pair2.second.toString()
                        tvOpponentChoseAction.setText("B")
                        tvYourScoreAction.setText("${Pair2.first}")
                        tvPlayer12.setBackgroundColor(Color.GREEN)
                    }
                    Pair3.second > Pair1.second && Pair3.second > Pair2.second && Pair3.second > Pair4.second -> {
                        tvOponentScoreAction.text = Pair3.second.toString()
                        tvOpponentChoseAction.setText("A")
                        tvYourScoreAction.setText("${Pair3.first}")
                        tvPlayer21.setBackgroundColor(Color.GREEN)
                    }
                    Pair4.second > Pair1.second && Pair4.second > Pair2.second && Pair4.second > Pair3.second -> {
                        tvOponentScoreAction.text = Pair4.second.toString()
                        tvOpponentChoseAction.setText("B")
                        tvYourScoreAction.setText("${Pair4.first}")
                        tvPlayer22.setBackgroundColor(Color.GREEN)
                    }
                    else -> {
                        tvOponentScoreAction.text = Pair1.second.toString()
                        tvYourScoreAction.setText("${Pair1.first}")
                        tvOpponentChoseAction.setText("A")
                        tvPlayer11.setBackgroundColor(Color.GREEN)
                    }
                }

            }

            // #####################################################        Cautious
            if (selectedOption == R.id.radio_Cautious) {
                var differenz1 = 0
                var differenz2 = 0
                var differenz3 = 0
                var differenz4 = 0

                differenz1 = 0;
                if (Pair1.first > Pair1.second) {
                    differenz1 = Pair1.first - Pair1.second;
                } else if (Pair1.second > Pair1.first) {
                    differenz1 = Pair1.second - Pair1.first;
                }

                differenz2 = 0;
                if (Pair2.first > Pair2.second) {
                    differenz2 = Pair2.first - Pair2.second;
                } else if (Pair2.second > Pair2.first) {
                    differenz2 = Pair2.second - Pair2.first;
                }

                differenz3 = 0;
                if (Pair3.first > Pair3.second) {
                    differenz3 = Pair3.first - Pair3.second;
                } else if (Pair3.second > Pair3.first) {
                    differenz3 = Pair3.second - Pair3.first;
                }

                differenz4 = 0;
                if (Pair4.first > Pair4.second) {
                    differenz4 = Pair4.first - Pair4.second;
                } else if (Pair4.second > Pair4.first) {
                    differenz4 = Pair4.second - Pair4.first;
                }


                when {
                    differenz2 < differenz1 && differenz2 < differenz3 && differenz2 < differenz4 -> {
                        tvOponentScoreAction.text = Pair2.second.toString()
                        tvOpponentChoseAction.setText("B")
                        tvYourScoreAction.setText("${Pair2.first}")
                        tvPlayer12.setBackgroundColor(Color.GREEN)
                    }
                    differenz3 < differenz1 && differenz3 < differenz2 && differenz3 < differenz4 -> {
                        tvOponentScoreAction.text = Pair3.second.toString()
                        tvOpponentChoseAction.setText("A")
                        tvYourScoreAction.setText("${Pair3.first}")
                        tvPlayer21.setBackgroundColor(Color.GREEN)
                    }
                    differenz4 < differenz1 && differenz4 < differenz2 && differenz4 < differenz3 -> {
                        tvOponentScoreAction.text = Pair4.second.toString()
                        tvOpponentChoseAction.setText("B")
                        tvYourScoreAction.setText("${Pair4.first}")
                        tvPlayer22.setBackgroundColor(Color.GREEN)
                    }
                    else -> {
                        tvOponentScoreAction.text = differenz1.toString()
                        tvYourScoreAction.setText("${Pair1.first}")
                        tvOpponentChoseAction.setText("A")
                        tvPlayer11.setBackgroundColor(Color.GREEN)
                    }
                }
            }

           // #############################################################       Nash

            if (selectedOption == R.id.radio_Nash) {
                //aktion
                }

        }


        btnActionB.setOnClickListener {
            val setTextChose = findViewById<TextView>(R.id.tvYouChoseAction)
            setTextChose.setText("B")

            // Nach click in Button wird A oder B für opponement generiert
            val tvOpponentChoseAction = findViewById<TextView>(R.id.tvOpponentChoseAction)
            val Pair5 = generateOponentChoose('A','B')
            tvOpponentChoseAction.text = "${Pair5.first}"

            // Button wird deaktiviert
            btnActionB.isEnabled = false
            btnActionA.isEnabled = false

            // Mein & oponement score wird angezeigt
            val inside = tvOpponentChoseAction.text.toString()
            if (inside[0] == 'A') {
                tvYourScoreAction.setText("${Pair3.first}")
                tvOponentScoreAction.setText("${Pair3.second}")
                tvPlayer21.setBackgroundColor(Color.GREEN)
            }

            val inside2 = tvOpponentChoseAction.text.toString()
            if (inside2[0] == 'B') {
                tvYourScoreAction.setText("${Pair4.first}")
                tvOponentScoreAction.setText("${Pair4.second}")
                tvPlayer22.setBackgroundColor(Color.GREEN)
            }

        }


        // Definieren was auf die mainActivity als score zurück geht
        val text1 = Pair1.first.toString()
        val text2 = Pair1.second.toString()
        val text3 = Pair2.first.toString()
        val text4 = Pair2.second.toString()
        val text5 = Pair3.first.toString()
        val text6 = Pair3.second.toString()
        val text7 = Pair4.first.toString()
        val text8 = Pair4.second.toString()

        val btnDismiss = findViewById<Button>(R.id.btnDismiss)
        btnDismiss.setOnClickListener {
            //Zurück in die erste acticity
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("increment", btnDismiss.isEnabled)
            startActivity(intent)

            // Nach click übertragen die punkte an die erste activity
            if (tvPlayer11.background is ColorDrawable && (tvPlayer11.background as ColorDrawable).color == Color.GREEN) {
                val intent7 = Intent(this, MainActivity::class.java)
                intent7.putExtra("action", text1)
                intent7.putExtra("action2", text2)
                startActivity(intent7)

            } else if (tvPlayer12.background is ColorDrawable && (tvPlayer12.background as ColorDrawable).color == Color.GREEN) {
                val intent8 = Intent(this, MainActivity::class.java)
                intent8.putExtra("action", text3)
                intent8.putExtra("action2", text4)
                startActivity(intent8)

            } else if (tvPlayer21.background is ColorDrawable && (tvPlayer21.background as ColorDrawable).color == Color.GREEN) {
                val intent8 = Intent(this, MainActivity::class.java)
                intent8.putExtra("action", text5)
                intent8.putExtra("action2", text6)
                startActivity(intent8)

            } else if (tvPlayer22.background is ColorDrawable && (tvPlayer22.background as ColorDrawable).color == Color.GREEN) {
                val intent8 = Intent(this, MainActivity::class.java)
                intent8.putExtra("action", text7)
                intent8.putExtra("action2", text8)
                startActivity(intent8)
            }

            if(btnActionA.isEnabled == true && btnActionB.isEnabled == true ) {
                Toast.makeText(this, "The game could not be finished", Toast.LENGTH_SHORT).show()
            }

        }


    }
    // Funktion um Random Zahlen zu generieren
    fun generateNumbers(min: Int,max: Int): Pair<Int,Int>{
        val minimum = (min..max).random()
        val maximum = (min..max).random()
        return Pair(minimum, maximum)
    }

    fun generateOponentChoose(actionA: Char, actionB: Char): Pair<Char,Char> {
        val actionA = (actionA..actionB).random()
        // val actionB = (actionA..actionB).random()
        return Pair(actionA, actionB)
    }
}
