package com.example.project1_wordle

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.text.trimmedLength

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
        val winStreak = findViewById<TextView>(R.id.WinStreak)
        var winNum = 0
        var guessNum = 0
        var ranWord = FourLetterWordList.getRandomFourLetterWord()
        correctGuess.text = ranWord

        //check if the string is only contain alphabet letters
        fun isAlpha(str: String): Boolean {
            for (c in str) {
                if (!c.isLetter()) return false
            }
            return true
        }

        //make keyboard disappear
        fun closeKeyBoard() {
            val view = this.currentFocus
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

        //set the string with multiple colors
        fun checkStringGen(answerCheck: TextView, guess: String, check: String) {
            answerCheck.text = ""
            for (i in 0..3) {
                val word = SpannableString(guess[i].toString())
                when (check[i]) {
                    'O' -> {
                        word.setSpan(
                            ForegroundColorSpan(Color.GREEN),
                            0,
                            word.length,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                    }
                    'X' -> {
                        word.setSpan(
                            ForegroundColorSpan(Color.RED),
                            0,
                            word.length,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                    }
                    '+' -> {
                        word.setSpan(
                            ForegroundColorSpan(Color.parseColor("#ffd700")),
                            0,
                            word.length,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                    }
                }
                answerCheck.append(word)
            }
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

        fun isContain(StringList: List<String>, str: String): Boolean {
            for (s in StringList) {
                if (s.equals(str, true)) {
                    return true
                }
            }
            return false
        }

        fun inputButtonHandler() {
            val guess = guessField.text.toString().uppercase()
            if (guess.length != 4) {
                Toast.makeText(this, "Input Must Have 4 Characters", Toast.LENGTH_SHORT).show()
                return
            }

            if (!isAlpha(guess)) {
                Toast.makeText(this, "Contains characters that are not alphabetical", Toast.LENGTH_SHORT).show()
                return
            }

            if (!isContain(FourLetterWordList.getAllFourLetterWords(), guess)) {
                Toast.makeText(this, "Not in word list", Toast.LENGTH_SHORT).show()
                return
            }


            closeKeyBoard()
            guessField.text.clear()
            var check = ""
            for (i in 0..3) {
                check += if (guess[i] == ranWord[i]) {
                    "O"
                } else if (ranWord.contains(guess[i])) {
                    "+"
                } else {
                    "X"
                }
            }
            when (guessNum) {
                0 -> {
                    answer1.text = guess
                    answer1.visibility = View.VISIBLE
                    guess1Check.visibility = View.VISIBLE
                    checkStringGen(answer1Check, guess, check)
                    answer1Check.visibility = View.VISIBLE
                    if (guess == ranWord) {
                        winNum +=1
                        winStreak.text = winNum.toString()
                        correctGuess.setTextColor(Color.GREEN)
                        inputToReset()
                    } else {
                        guess2.visibility = View.VISIBLE
                        guessNum = 1
                    }
                }
                1 -> {
                    answer2.text = guess
                    answer2.visibility = View.VISIBLE
                    guess2Check.visibility = View.VISIBLE
                    checkStringGen(answer2Check, guess, check)
                    answer2Check.visibility = View.VISIBLE
                    if (guess == ranWord) {
                        winNum +=1
                        winStreak.text = winNum.toString()
                        correctGuess.setTextColor(Color.GREEN)
                        inputToReset()
                    } else {
                        guess3.visibility = View.VISIBLE
                        guessNum = 2
                    }
                }
                2 -> {
                    answer3.text = guess
                    answer3.visibility = View.VISIBLE
                    guess3Check.visibility = View.VISIBLE
                    checkStringGen(answer3Check, guess, check)
                    answer3Check.visibility = View.VISIBLE
                    answer2.visibility = View.VISIBLE
                    if (guess == ranWord) {
                        winNum +=1
                        winStreak.text = winNum.toString()
                        correctGuess.setTextColor(Color.GREEN)
                    } else {
                        winNum = 0
                        winStreak.text = winNum.toString()
                        correctGuess.setTextColor(Color.RED)
                    }
                    inputToReset()
                }
            }
        }

        //button click event listener
        inputButton.setOnClickListener {
            inputButtonHandler()
        }

        //edittext enter key event listener
        guessField.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                inputButtonHandler()
                return@OnKeyListener true
            }
            false
        })

    }

}