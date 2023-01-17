package com.itis.testapplication.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.google.gson.Gson
import com.itis.testapplication.utilities.PreferenceParser
import com.itis.testapplication.R
import com.itis.testapplication.model.Partita
import com.itis.testapplication.model.Storico
import kotlin.random.Random

class  MainActivity : AppCompatActivity() {

    private var contatore=0
    private lateinit var storico : Storico
    private val random = Random(System.currentTimeMillis())
    private var numberToGuess = random.nextInt(0,100)
    private val parser = PreferenceParser(Storico::class.java, this)


    //EditText
    private lateinit var editText : EditText
    //Button
    private lateinit var button : Button
    private lateinit var buttonNewGame : Button

    private lateinit var buttonHistory : Button
    //TextView
    private lateinit var esito : TextView
    private lateinit var textNumTentativi : TextView
    private lateinit var textAverage : TextView
    private lateinit var textPunteggio : TextView
    private lateinit var immagine : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeComponents()

        storico = parser.loadPreference()!!

        //Inizializzazione testi
        textAverage.text = getString(R.string.average, storico.totaleTentativi/storico.numPartite)
        textNumTentativi.text = getString(R.string.tentativi, contatore)
        textPunteggio.text = getString(R.string.punteggio, storico.points)

        buttonNewGame.visibility = View.GONE

        //OnClickListeners
        button.setOnClickListener {
            guessClicked()
        }
        buttonNewGame.setOnClickListener {
            newGame()
        }
        buttonHistory.setOnClickListener {
            val intent = Intent( this, HistoryActivity:: class.java)
            intent.putExtra("HISTORY", Gson().toJson(storico))
            startActivity(intent)
        }
    }

    private fun save(){
        parser.savePreference(storico)
    }

    private fun newGame() {
        contatore = 0
        numberToGuess = random.nextInt(0, 100)
        storico.numPartite++
        buttonNewGame.visibility = View.GONE
        save()
    }

    private fun initializeComponents() {
        //EditText
        editText = findViewById(R.id.editTextNumber)
        //Button
        button = findViewById(R.id.button_guess)
        buttonNewGame = findViewById(R.id.buttonNewGame)
        buttonHistory = findViewById(R.id.button_history)
        //TextView
        esito = findViewById(R.id.textViewEsito)
        textNumTentativi = findViewById(R.id.textNumTentativi)
        textAverage = findViewById(R.id.textMedia)
        textPunteggio = findViewById(R.id.textPunteggio)
        immagine = findViewById(R.id.imageView)

        parser.initialize()
    }

    private fun guessClicked() {
        val userNumber = editText.text.toString().toInt()
        if (userNumber == numberToGuess) {
            guessed()
        } else if (userNumber > numberToGuess) {
            esito.text = getString(R.string.too_high)
            contatore++
        } else {
            esito.text = getString(R.string.too_low)
            contatore++
        }
        textNumTentativi.text = getString(R.string.tentativi, contatore)
        checkTooMuchGuest()
    }

    private fun checkTooMuchGuest() {
        if (contatore > 5){
            immagine.visibility=View.VISIBLE
        } else {
            immagine.visibility=View.GONE
        }
    }


    private fun guessed() {
        esito.text = getString(R.string.winner)
        storico.totaleTentativi += contatore
        textAverage.text = getString(R.string.average, storico.totaleTentativi / storico.numPartite)
        storico.points += Math.max(0, 10 - contatore)
        textPunteggio.text = getString(R.string.punteggio, storico.points)
        buttonNewGame.visibility = View.VISIBLE

        val partita = Partita()
        partita.punti = Math.max(0, 10 - contatore)
        partita.tentativi = contatore
        storico.partite.add(partita)

        save()
    }
}