package de.fra_uas.fb2.mobiledevices.abeautifulmind

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.*
import androidx.core.content.ContextCompat


class  GameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val color = ContextCompat.getColor(this, R.color.pink)
        val tvStrategyType = findViewById<TextView>(R.id.tvStrategyType)
        val nashGleichgewichte = mutableListOf<Pair<Int, Int>>()
        val textViewList = mutableListOf<TextView>()
        val btnDismiss = findViewById<Button>(R.id.btnDismiss)
        //Nehmen den ausgewählten wert von der Radiogroup
        val intent = intent
        val selectedOption = intent.getIntExtra("selectedOption", 0)

        // Definition der boxen und wertebereich
        val tvPlayer11 = findViewById<TextView>(R.id.tvPlayer11)
        val Pair1 = generateNumbers(-5, 5)
        val tvPlayer12 = findViewById<TextView>(R.id.tvPlayer12)
        val Pair2 = generateNumbers(-5, 5)
        val tvPlayer21 = findViewById<TextView>(R.id.tvPlayer21)
        val Pair3 = generateNumbers(-5, 5)
        val tvPlayer22 = findViewById<TextView>(R.id.tvPlayer22)
        val Pair4 = generateNumbers(-5, 5)

        val btnActionA = findViewById<Button>(R.id.btnChooseActionA)
        val btnActionB = findViewById<Button>(R.id.btnChooseActionB)

        val tvYourScoreAction = findViewById<TextView>(R.id.tvYourScoreAction)
        val tvOponentScoreAction = findViewById<TextView>(R.id.tvOponentScoreAction)

        val inflater = layoutInflater
        val layout = inflater.inflate(R.layout.toast, null)


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

                val Pair5 = generateOponentChoose('A', 'B')
                tvOpponentChoseAction.text = "${Pair5.first}"

                val inside = tvOpponentChoseAction.text.toString()
                if (inside[0] == 'A') {
                    tvYourScoreAction.setText("${Pair1.first}")
                    tvOponentScoreAction.setText("${Pair1.second}")
                    tvPlayer11.setBackgroundColor(color)
                    MainActivity.Global.game_counter++
                }
                val inside2 = tvOpponentChoseAction.text.toString()
                if (inside2[0] == 'B') {
                    tvYourScoreAction.setText("${Pair2.first}")
                    tvOponentScoreAction.setText("${Pair2.second}")
                    tvPlayer12.setBackgroundColor(color)
                    MainActivity.Global.game_counter++
                }
            }
            // #####################################################      Greedy
            if (selectedOption == R.id.radio_Greedy) {
                when {
                    Pair2.second > Pair1.second && Pair2.second > Pair3.second && Pair2.second > Pair4.second -> {
                    tvOponentScoreAction.text = Pair2.second.toString()
                    tvOpponentChoseAction.setText("B")
                    tvYourScoreAction.setText("${Pair2.first}")
                    tvPlayer12.setBackgroundColor(color)
                    MainActivity.Global.game_counter++
                    }
                else -> {
                    tvOponentScoreAction.text = Pair1.second.toString()
                    tvYourScoreAction.setText("${Pair1.first}")
                    tvOpponentChoseAction.setText("A")
                    tvPlayer11.setBackgroundColor(color)
                    MainActivity.Global.game_counter++
                    }
                }
            }
            // #####################################################        Cautious
            if (selectedOption == R.id.radio_Cautious) {
                var differenz1 = 0
                var differenz2 = 0

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

                when {
                    differenz2 < differenz1 -> {
                    tvOponentScoreAction.text = Pair2.second.toString()
                    tvOpponentChoseAction.setText("B")
                    tvYourScoreAction.setText("${Pair2.first}")
                    tvPlayer12.setBackgroundColor(color)
                    MainActivity.Global.game_counter++
                    }

                    differenz1 < differenz2 -> {
                        tvOponentScoreAction.text = differenz1.toString()
                        tvYourScoreAction.setText("${Pair1.first}")
                        tvOpponentChoseAction.setText("A")
                        tvPlayer11.setBackgroundColor(color)
                        MainActivity.Global.game_counter++
                    }
                }
            }

            // #############################################################       Nash

            if (selectedOption == R.id.radio_Nash) {
                // Erstelle eine 2D-Liste für die Auszahlungen
                val auszahlungenSpieler1 = listOf(listOf(Pair1.first, Pair2.first), listOf(Pair3.first, Pair4.first))
                val auszahlungenSpieler2 = listOf(listOf(Pair1.second, Pair2.second), listOf(Pair3.second, Pair4.second))

                // TextView-Objekte zur Liste hinzufügen
                textViewList.add(tvPlayer11)
                textViewList.add(tvPlayer12)
                textViewList.add(tvPlayer21)
                textViewList.add(tvPlayer22)

                var anzahlGeaenderteHintergruende = 0

                for (i in 0 until 2) {
                    for (j in 0 until 2) {
                        when {
                            auszahlungenSpieler1[i][j] >= auszahlungenSpieler1[1 - i][j] -> {
                                if (auszahlungenSpieler2[i][j] >= auszahlungenSpieler2[i][1 - j]) {
                                    val nashGleichgewicht = Pair(i, j)
                                    nashGleichgewichte.add(nashGleichgewicht)
                                    anzahlGeaenderteHintergruende++
                                }
                            }
                            auszahlungenSpieler2[i][j] >= auszahlungenSpieler2[i][1 - j] -> {
                                if (auszahlungenSpieler1[i][j] >= auszahlungenSpieler1[1 - i][j]) {
                                    val nashGleichgewicht = Pair(i, j)
                                    nashGleichgewichte.add(nashGleichgewicht)
                                    anzahlGeaenderteHintergruende++
                                }
                            }
                        }
                    }
                }

                if (nashGleichgewichte.isNotEmpty()) {
                    when {
                        anzahlGeaenderteHintergruende == 1 -> {
                            for (nashGleichgewicht in nashGleichgewichte) {
                                val textViewIndex = nashGleichgewicht.first * 2 + nashGleichgewicht.second
                                textViewList[textViewIndex].setBackgroundColor(color)
                                MainActivity.Global.game_counter++

                                if (tvPlayer11.background is ColorDrawable && (tvPlayer11.background as ColorDrawable).color == color) {
                                    tvOpponentChoseAction.text = "A"
                                    tvYourScoreAction.text = ("${Pair1.first}")
                                    tvOponentScoreAction.setText("${Pair1.second}")
                                    MainActivity.Global.game_counter++

                                } else if (tvPlayer12.background is ColorDrawable && (tvPlayer12.background as ColorDrawable).color == color) {
                                    tvOpponentChoseAction.text = "B"
                                    tvYourScoreAction.text = ("${Pair2.first}")
                                    tvOponentScoreAction.setText("${Pair2.second}")
                                    MainActivity.Global.game_counter++
                                } else if (tvPlayer21.background is ColorDrawable && (tvPlayer21.background as ColorDrawable).color == color) {
                                    tvOpponentChoseAction.text = "A"
                                    tvYourScoreAction.text = ("${Pair3.first}")
                                    tvOponentScoreAction.setText("${Pair3.second}")
                                    MainActivity.Global.game_counter++
                                } else if (tvPlayer22.background is ColorDrawable && (tvPlayer22.background as ColorDrawable).color == color) {
                                    tvOpponentChoseAction.text = "B"
                                    tvYourScoreAction.text = ("${Pair4.first}")
                                    tvOponentScoreAction.setText("${Pair4.second}")
                                    MainActivity.Global.game_counter++
                                }
                            }

                            val toastTextView = layout.findViewById<TextView>(R.id.tvToast)
                            toastTextView.text = "There is a unique Nash equilibrium in this game:\nMy action: A\nOpponement action: ${tvOpponentChoseAction.text}"

                            val toast = Toast(applicationContext)
                            toast.duration = Toast.LENGTH_LONG
                            toast.view = layout
                            toast.show()

                        }

                        anzahlGeaenderteHintergruende > 1 -> {
                            val toastTextView = layout.findViewById<TextView>(R.id.tvToast)
                            toastTextView.text = "There are multiple Nash equilibria in this game.\n" +
                                    "Opponement cannot choose an action\n" +
                                    "Gane cannot be finished."

                            val toast = Toast(applicationContext)
                            toast.duration = Toast.LENGTH_LONG
                            toast.view = layout
                            toast.show()
                            setTextChose.setText("_")
                        }
                    }
                }
                if (anzahlGeaenderteHintergruende == 0) {
                    val toastTextView = layout.findViewById<TextView>(R.id.tvToast)
                    toastTextView.text = "There are multiple Nash equilibria in this game.\n" +
                            "Opponement cannot choose an action\n" +
                            "Gane cannot be finished."

                    val toast = Toast(applicationContext)
                    toast.duration = Toast.LENGTH_LONG
                    toast.view = layout
                    toast.show()
                    setTextChose.setText("_")
                }
            }
        }


            btnActionB.setOnClickListener {
                // Wird angezeigt ob man option a oder b gewählt wurde von nutzer
                val setTextChose = findViewById<TextView>(R.id.tvYouChoseAction)
                setTextChose.setText("B")

                // Button wird deaktiviert
                btnActionA.isEnabled = false
                btnActionB.isEnabled = false

                val tvOpponentChoseAction = findViewById<TextView>(R.id.tvOpponentChoseAction)

                // #####################################################     Random
                if (selectedOption == R.id.radio_Random) {

                    val Pair5 = generateOponentChoose('A', 'B')
                    tvOpponentChoseAction.text = "${Pair5.first}"

                    val inside = tvOpponentChoseAction.text.toString()
                    if (inside[0] == 'A') {
                        tvYourScoreAction.setText("${Pair3.first}")
                        tvOponentScoreAction.setText("${Pair3.second}")
                        tvPlayer21.setBackgroundColor(color)
                        MainActivity.Global.game_counter++
                    }
                    val inside2 = tvOpponentChoseAction.text.toString()
                    if (inside2[0] == 'B') {
                        tvYourScoreAction.setText("${Pair4.first}")
                        tvOponentScoreAction.setText("${Pair4.second}")
                        tvPlayer22.setBackgroundColor(color)
                        MainActivity.Global.game_counter++
                    }
                }
                // #####################################################      Greedy
                if (selectedOption == R.id.radio_Greedy) {
                        when {
                            Pair4.second > Pair1.second && Pair4.second > Pair2.second && Pair4.second > Pair3.second -> {
                                tvOponentScoreAction.text = Pair4.second.toString()
                                tvOpponentChoseAction.setText("B")
                                tvYourScoreAction.setText("${Pair4.first}")
                                tvPlayer22.setBackgroundColor(color)
                                MainActivity.Global.game_counter++
                            }

                            else -> {
                                tvOponentScoreAction.text = Pair3.second.toString()
                                tvYourScoreAction.setText("${Pair3.first}")
                                tvOpponentChoseAction.setText("A")
                                tvPlayer21.setBackgroundColor(color)
                                MainActivity.Global.game_counter++
                            }
                        }

                }

                // #####################################################        Cautious
                if (selectedOption == R.id.radio_Cautious) {
                    var differenz3 = 0
                    var differenz4 = 0

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
                        differenz3 < differenz4 -> {
                            tvOponentScoreAction.text = Pair3.second.toString()
                            tvOpponentChoseAction.setText("A")
                            tvYourScoreAction.setText("${Pair3.first}")
                            tvPlayer21.setBackgroundColor(color)
                            MainActivity.Global.game_counter++
                        }

                        differenz4 < differenz3 -> {
                            tvOponentScoreAction.text = Pair4.second.toString()
                            tvOpponentChoseAction.setText("B")
                            tvYourScoreAction.setText("${Pair4.first}")
                            tvPlayer22.setBackgroundColor(color)
                            MainActivity.Global.game_counter++
                        }
                    }
                }

                // #############################################################       Nash

                if (selectedOption == R.id.radio_Nash) {
                    // Erstelle eine 2D-Liste für die Auszahlungen
                    val auszahlungenSpieler1 = listOf(listOf(Pair1.first, Pair2.first), listOf(Pair3.first, Pair4.first))
                    val auszahlungenSpieler2 = listOf(listOf(Pair1.second, Pair2.second), listOf(Pair3.second, Pair4.second))



                    // TextView-Objekte zur Liste hinzufügen
                    textViewList.add(tvPlayer11)
                    textViewList.add(tvPlayer12)
                    textViewList.add(tvPlayer21)
                    textViewList.add(tvPlayer22)

                    var anzahlGeaenderteHintergruende = 0

                    for (i in 0 until 2) {
                        for (j in 0 until 2) {
                            when {
                                auszahlungenSpieler1[i][j] >= auszahlungenSpieler1[1 - i][j] -> {
                                    if (auszahlungenSpieler2[i][j] >= auszahlungenSpieler2[i][1 - j]) {
                                        val nashGleichgewicht = Pair(i, j)
                                        nashGleichgewichte.add(nashGleichgewicht)
                                        anzahlGeaenderteHintergruende++
                                    }
                                }

                                auszahlungenSpieler2[i][j] >= auszahlungenSpieler2[i][1 - j] -> {
                                    if (auszahlungenSpieler1[i][j] >= auszahlungenSpieler1[1 - i][j]) {
                                        val nashGleichgewicht = Pair(i, j)
                                        nashGleichgewichte.add(nashGleichgewicht)
                                        anzahlGeaenderteHintergruende++
                                    }
                                }
                            }
                        }
                    }


                    if (nashGleichgewichte.isNotEmpty()) {

                        when {
                            anzahlGeaenderteHintergruende == 1 -> {
                                for (nashGleichgewicht in nashGleichgewichte) {
                                    val textViewIndex = nashGleichgewicht.first * 2 + nashGleichgewicht.second
                                    textViewList[textViewIndex].setBackgroundColor(color)
                                    MainActivity.Global.game_counter++

                                    if (tvPlayer11.background is ColorDrawable && (tvPlayer11.background as ColorDrawable).color == color) {
                                        tvOpponentChoseAction.text = "A"
                                        tvYourScoreAction.text = ("${Pair1.first}")
                                        tvOponentScoreAction.setText("${Pair1.second}")
                                        MainActivity.Global.game_counter++

                                    } else if (tvPlayer12.background is ColorDrawable && (tvPlayer12.background as ColorDrawable).color == color) {
                                        tvOpponentChoseAction.text = "B"
                                        tvYourScoreAction.text = ("${Pair2.first}")
                                        tvOponentScoreAction.setText("${Pair2.second}")
                                        MainActivity.Global.game_counter++
                                    } else if (tvPlayer21.background is ColorDrawable && (tvPlayer21.background as ColorDrawable).color == color) {
                                        tvOpponentChoseAction.text = "A"
                                        tvYourScoreAction.text = ("${Pair3.first}")
                                        tvOponentScoreAction.setText("${Pair3.second}")
                                        MainActivity.Global.game_counter++
                                    } else if (tvPlayer22.background is ColorDrawable && (tvPlayer22.background as ColorDrawable).color == color) {
                                        tvOpponentChoseAction.text = "B"
                                        tvYourScoreAction.text = ("${Pair4.first}")
                                        tvOponentScoreAction.setText("${Pair4.second}")
                                        MainActivity.Global.game_counter++
                                    }
                                }
                                val toastTextView = layout.findViewById<TextView>(R.id.tvToast)
                                toastTextView.text = "There is a unique Nash equilibrium in this game:\nMy action: B\nOpponement action: ${tvOpponentChoseAction.text}"

                                val toast = Toast(applicationContext)
                                toast.duration = Toast.LENGTH_LONG
                                toast.view = layout
                                toast.show()


                            }

                            anzahlGeaenderteHintergruende > 1 -> {
                                val toastTextView = layout.findViewById<TextView>(R.id.tvToast)
                                toastTextView.text = "There are multiple Nash equilibria in this game.\n" +
                                        "Opponement cannot choose an action\n" +
                                        "Gane cannot be finished."

                                val toast = Toast(applicationContext)
                                toast.duration = Toast.LENGTH_LONG
                                toast.view = layout
                                toast.show()
                                setTextChose.setText("_")
                            }
                        }
                    }
                    if (anzahlGeaenderteHintergruende == 0) {
                        val toastTextView = layout.findViewById<TextView>(R.id.tvToast)
                        toastTextView.text = "There are multiple Nash equilibria in this game.\n" +
                                "Opponement cannot choose an action\n" +
                                "Gane cannot be finished."

                        val toast = Toast(applicationContext)
                        toast.duration = Toast.LENGTH_LONG
                        toast.view = layout
                        toast.show()
                        setTextChose.setText("_")
                    }
                }
            }





            btnDismiss.setOnClickListener {
                //Zurück in die erste acticity
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)


                // Nach click übertragen die punkte an die erste activity
                if (tvPlayer11.background is ColorDrawable && (tvPlayer11.background as ColorDrawable).color == color) {
                    MainActivity.Global.myscore += Pair1.first.toInt()
                    MainActivity.Global.myopponent += Pair1.second.toInt()
                    startActivity(intent)

                } else if (tvPlayer12.background is ColorDrawable && (tvPlayer12.background as ColorDrawable).color == color) {
                    MainActivity.Global.myscore += Pair2.first.toInt()
                    MainActivity.Global.myopponent += Pair2.second.toInt()
                    startActivity(intent)

                } else if (tvPlayer21.background is ColorDrawable && (tvPlayer21.background as ColorDrawable).color == color) {
                    MainActivity.Global.myscore += Pair3.first.toInt()
                    MainActivity.Global.myopponent += Pair3.second.toInt()
                    startActivity(intent)

                } else if (tvPlayer22.background is ColorDrawable && (tvPlayer22.background as ColorDrawable).color == color) {
                    MainActivity.Global.myscore += Pair4.first.toInt()
                    MainActivity.Global.myopponent += Pair4.second.toInt()
                    startActivity(intent)
                }

                if (btnActionA.isEnabled == true && btnActionB.isEnabled == true  ) {
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
