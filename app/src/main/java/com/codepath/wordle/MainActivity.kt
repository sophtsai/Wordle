package com.codepath.wordle

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.codepath.wordle.FourLetterWordList.FourLetterWordList
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.emitter.Emitter
import nl.dionsegijn.konfetti.core.models.Size
import nl.dionsegijn.konfetti.xml.KonfettiView
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {
    var guessCount = 0
    var thisGuess = ""
    private var wordToGuess = FourLetterWordList.getRandomFourLetterWord()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.guessButton)
        val guess1 = findViewById<TextView>(R.id.guess1)
        val guess2 = findViewById<TextView>(R.id.guess2)
        val guess3 = findViewById<TextView>(R.id.guess3)
        val guess1Check = findViewById<TextView>(R.id.guess1check)
        val guess2Check = findViewById<TextView>(R.id.guess2check)
        val guess3Check = findViewById<TextView>(R.id.guess3check)
        val word1 = findViewById<TextView>(R.id.word1)
        val word2 = findViewById<TextView>(R.id.word2)
        val word3 = findViewById<TextView>(R.id.word3)
        val word1Check = findViewById<TextView>(R.id.word1check)
        val word2Check = findViewById<TextView>(R.id.word2check)
        val word3Check = findViewById<TextView>(R.id.word3check)
        val currentGuess = findViewById<EditText>(R.id.editTextGuess)
        val answer = findViewById<TextView>(R.id.guessWord)
        val konfettiView = findViewById<KonfettiView>(R.id.konfettiView)

        answer.text = wordToGuess
        answer.visibility = View.VISIBLE

        button.setOnClickListener {
            guessCount += 1
            thisGuess = currentGuess.text.toString().uppercase()
            when (guessCount) {
                1 -> {
                    currentGuess.text.clear()
                    guess1.visibility = View.VISIBLE
                    word1.text = thisGuess
                    word1.visibility = View.VISIBLE
                    guess1Check.visibility = View.VISIBLE
                    word1Check.text = checkGuess(thisGuess)
                    word1Check.visibility = View.VISIBLE
                }
                2 -> {
                    currentGuess.text.clear()
                    guess2.visibility = View.VISIBLE
                    word2.text = thisGuess
                    word2.visibility = View.VISIBLE
                    guess2Check.visibility = View.VISIBLE
                    word2Check.text = checkGuess(thisGuess)
                    word2Check.visibility = View.VISIBLE
                }
                3 -> {
                    currentGuess.text.clear()
                    guess3.visibility = View.VISIBLE
                    word3.text = thisGuess
                    word3.visibility = View.VISIBLE
                    guess3Check.visibility = View.VISIBLE
                    word3Check.text = checkGuess(thisGuess)
                    word3Check.visibility = View.VISIBLE
                    answer.visibility = View.VISIBLE
                    button.visibility = View.INVISIBLE
                }
                else -> {
                    /*
                    button.text = "RESTART"
                    button.setOnClickListener {
                        guess1.visibility = View.INVISIBLE
                        guess2.visibility = View.INVISIBLE
                        guess3.visibility = View.INVISIBLE
                        guess1Check.visibility = View.INVISIBLE
                        guess2Check.visibility = View.INVISIBLE
                        guess3Check.visibility = View.INVISIBLE
                        word1.visibility = View.INVISIBLE
                        word2.visibility = View.INVISIBLE
                        word3.visibility = View.INVISIBLE
                        word1Check.visibility = View.INVISIBLE
                        word2Check.visibility = View.INVISIBLE
                        word3Check.visibility = View.INVISIBLE
                        wordToGuess = FourLetterWordList.getRandomFourLetterWord()
                        guessCount = 0
                        thisGuess = ""
                        button.text = "GUESS!"
                        Toast.makeText(it.context, "Reached reset", Toast.LENGTH_SHORT).show()
                    }*/
                }

            }
            //hide Keyboard
            this.currentFocus?.let { view ->
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                imm?.hideSoftInputFromWindow(view.windowToken, 0)
            }
            if (thisGuess == wordToGuess) {
                answer.visibility = View.VISIBLE
                button.visibility = View.INVISIBLE
                currentGuess.visibility = View.INVISIBLE
                val party = Party(
                    speed = 0f,
                    maxSpeed = 30f,
                    damping = 0.9f,
                    spread = 360,
                    colors = listOf(0xff99b6, 0xffc2e2, 0xaf99ff, 0xcaadff),
                    emitter = Emitter(duration = 100, TimeUnit.MILLISECONDS).max(100),
                    position = Position.Relative(0.5, 0.3)
                )
                konfettiView.start(party)
            }
        }

    }

    /**
     * Parameters / Fields:
     *   wordToGuess : String - the target word the user is trying to guess
     *   guess : String - what the user entered as their guess
     *
     * Returns a String of 'O', '+', and 'X', where:
     *   'O' represents the right letter in the right place
     *   '+' represents the right letter in the wrong place
     *   'X' represents a letter not in the target word
     */
    private fun checkGuess(guess: String) : String {
        var result = ""
        for (i in 0..3) {
            if (guess[i] == wordToGuess[i]) {
                result += "O"
            }
            else if (guess[i] in wordToGuess) {
                result += "+"
            }
            else {
                result += "X"
            }
        }
        return result
    }
}