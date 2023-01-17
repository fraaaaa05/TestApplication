package com.itis.testapplication.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import com.google.gson.Gson
import com.itis.testapplication.R
import com.itis.testapplication.model.Storico

class HistoryActivity : AppCompatActivity() {

    private lateinit var storico : Storico
    private lateinit var listView : ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        val extras = intent.extras?.getString("HISTORY")
        storico = Gson().fromJson(extras, Storico::class.java)
        initializeComponents()
    }

    fun initializeComponents(){
        listView = findViewById(R.id.listView)
        var adapter = ArrayAdapter(this, R.layout.single_row, storico.partite)
        listView.adapter = adapter
    }
}