package com.example.project1_wordle

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val guess1 = findViewById<TextView>(R.id.Guess1)
        val guess2 = findViewById<TextView>(R.id.Guess2)
        val guess3 = findViewById<TextView>(R.id.Guess3)
        val guess1Check = findViewById<TextView>(R.id.Guess1Check)
        val guess2Check = findViewById<TextView>(R.id.Guess2Check)
        val guess3Check = findViewById<TextView>(R.id.Guess3Check)
        val answer1 = findViewById<TextView>(R.id.Answer1)
        val answer2 = findViewById<TextView>(R.id.Answer2)
        val answer3 = findViewById<TextView>(R.id.Answer3)
        val answer1Check = findViewById<TextView>(R.id.Answer1Check)
        val answer2Check = findViewById<TextView>(R.id.Answer2Check)
        val answer3Check = findViewById<TextView>(R.id.Answer3Check)
        val correctGuess = findViewById<TextView>(R.id.CorrectGuess)
        val guessField = findViewById<EditText>(R.id.GuessField)
        val inputButton = findViewById<Button>(R.id.InputButton)
        val resetButton = findViewById<Button>(R.id.resetButton)
        var guessNum = 0
        var ranWord = FourLetterWordList.getRandomFourLetterWord()
        correctGuess.text = ranWord

        //make keyboard disappear
        fun closeKeyBoard() {
            val view =  this.currentFocus
            if (view != null) {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }

        //reset the screen:
        fun screenReset() {
            guess1.visibility = View.VISIBLE
            answer1.visibility = View.INVISIBLE
            guess1Check.visibility = View.INVISIBLE
            answer1Check.visibility = View.INVISIBLE
            guess2.visibility = View.INVISIBLE
            answer2.visibility = View.INVISIBLE
            guess2Check.visibility = View.INVISIBLE
            answer2Check.visibility = View.INVISIBLE
            guess3.visibility = View.INVISIBLE
            answer3.visibility = View.INVISIBLE
            guess3Check.visibility = View.INVISIBLE
            answer3Check.visibility = View.INVISIBLE
            correctGuess.visibility = View.INVISIBLE
            ranWord = FourLetterWordList.getRandomFourLetterWord()
            correctGuess.text = ranWord
        }

        //toggle between guess and reset button
        fun inputToReset() {
            correctGuess.visibility = View.VISIBLE
            inputButton.visibility = View.INVISIBLE
            inputButton.isEnabled = false
            resetButton.visibility = View.VISIBLE
            guessNum = 0
            resetButton.setOnClickListener {
                resetButton.visibility = View.INVISIBLE
                inputButton.visibility = View.VISIBLE
                inputButton.isEnabled = true
                screenReset()

                resetButton.setOnClickListener(null)
            }

        }
        fun inputButtonHandler() {
            val guess = guessField.text.toString()
            if (guess.length != 4) {
                Toast.makeText(this, "Input Must Have 4 Characters", Toast.LENGTH_SHORT).show()
            }
            else {
                closeKeyBoard()
                guessField.text.clear()
                var check = ""
                for (i in 0..3) {
                    if (guess[i] == ranWord[i]) {
                        check += "O"
                    }
                    else if (ranWord.contains(guess[i])) {
                        check += "+"
                    }
                    else {
                        check += "X"
                    }
                }
                when (guessNum) {
                    0 -> {
                        answer1.text = guess
                        answer1.visibility = View.VISIBLE
                        guess1Check.visibility = View.VISIBLE
                        answer1Check.text = check
                        answer1Check.visibility = View.VISIBLE
                        if (guess == ranWord) {
                            inputToReset()
                        }
                        else {
                            guess2.visibility = View.VISIBLE
                            guessNum = 1
                        }
                    }
                    1 -> {
                        answer2.text = guess
                        answer2.visibility = View.VISIBLE
                        guess2Check.visibility = View.VISIBLE
                        answer2Check.text = check
                        answer2Check.visibility = View.VISIBLE
                        if (guess == ranWord) {
                            inputToReset()
                        }
                        else {
                            guess3.visibility = View.VISIBLE
                            guessNum = 2
                        }
                    }
                    2 -> {
                        answer3.text = guess
                        answer3.visibility = View.VISIBLE
                        guess3Check.visibility = View.VISIBLE
                        answer3Check.text = check
                        answer3Check.visibility = View.VISIBLE
                        answer2.visibility = View.VISIBLE
                        inputToReset()
                    }
                }

            }
        }
        inputButton.setOnClickListener{
            inputButtonHandler()
        }

    }

}