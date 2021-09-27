package com.example.numbersgame

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlin.properties.Delegates
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    lateinit var clRoot : ConstraintLayout
    lateinit var userText : EditText
    lateinit var guessBut : Button
    lateinit var itemList : ArrayList<String>
    lateinit var rvMessages : RecyclerView

    private var answer = 0
    private var guesses = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        answer = Random.nextInt(10)

        clRoot = findViewById(R.id.clRoot)
        itemList = ArrayList()

        rvMessages = findViewById(R.id.rvMessages)
        rvMessages.adapter = MessageAdapter(this, itemList)
        rvMessages.layoutManager = LinearLayoutManager(this)

        userText = findViewById(R.id.userText)
        guessBut = findViewById(R.id.Guess)

        guessBut.setOnClickListener{Guessfunc()}

    }

    fun Guessfunc(){

        val msg = userText.text.toString()
        if (msg.isNotEmpty()){
            if(guesses>0) {
                if (msg.toInt() == answer) {
                    showAlertDialog("You win!\n\nPlay again?")
                } else {
                    guesses--
                    itemList.add("You guessed $msg")
                    itemList.add("You have $guesses guesses left")
                }
                if (guesses == 0) {

                    itemList.add("You lose - The correct answer was $answer")
                    itemList.add("Game Over")
                    showAlertDialog("You lose...\nThe correct answer was $answer.\n\nPlay again?")
                }
            }
            userText.text.clear()
            userText.clearFocus()
            rvMessages.adapter?.notifyDataSetChanged()
        }else {
                Snackbar.make(clRoot, "Please enter a number", Snackbar.LENGTH_LONG).show()
        }
    }

         fun showAlertDialog(title: String) {
            // build alert dialog
            val dialogBuilder = AlertDialog.Builder(this)

            // set message of alert dialog
            dialogBuilder.setMessage(title)
                // if the dialog is cancelable
                .setCancelable(false)
                // positive button text and action
                .setPositiveButton("Yes", DialogInterface.OnClickListener {
                        dialog, id -> this.recreate()
                })
                // negative button text and action
                .setNegativeButton("No", DialogInterface.OnClickListener {
                        dialog, id -> dialog.cancel()
                })

            // create dialog box
            val alert = dialogBuilder.create()
            // set title for alert dialog box
            alert.setTitle("Game Over")
            // show alert dialog
            alert.show()
        }
}