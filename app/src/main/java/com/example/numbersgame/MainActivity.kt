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

    lateinit var userText : EditText
    lateinit var clRoot : ConstraintLayout
    lateinit var guessBut : Button
    lateinit var text2 : TextView
    lateinit var itemList : ArrayList<String>
    lateinit var rvMessages : RecyclerView
    var num = Random.nextInt(11)

    private var answer = 0
    private var guesses = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        clRoot = findViewById(R.id.clRoot)
        itemList = ArrayList()

        rvMessages = findViewById(R.id.rvMessages)

        rvMessages = findViewById<RecyclerView>(R.id.rvMessages )
        rvMessages.adapter = MessageAdapter(this, itemList)
        rvMessages.layoutManager = LinearLayoutManager(this)

        var text1 = findViewById<TextView>(R.id.tvOne)
         text2 = findViewById<TextView>(R.id.tvTwo)
        userText = findViewById(R.id.userText)
        guessBut = findViewById(R.id.Guess)

        text1.text = " You Guessed {${userText.toString().toInt()}} "
        guessBut.setOnClickListener{Guessfunc(num,userText.toString().toInt())}

    }

    fun Guessfunc(num1 : Int , num2 : Int ){

        val msg = userText.text.toString()


        if (msg.isNotEmpty()){
            if(guesses>0){
                if(msg.toInt() == answer){

                    showAlertDialog("You win!\n\nPlay again?")
                }else{
                    guesses--
                    itemList.add("You guessed $msg")
                    itemList.add("You have $guesses guesses left")
                }
                if(guesses==0){

                    itemList.add("You lose - The correct answer was $answer")
                    itemList.add("Game Over")
                    showAlertDialog("You lose...\nThe correct answer was $answer.\n\nPlay again?")
                }


          // itemList.add(msg)
            //userText.text.clear()
        }}else {
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