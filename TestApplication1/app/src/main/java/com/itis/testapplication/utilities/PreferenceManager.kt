package com.itis.testapplication.utilities

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

class PreferenceManager (val activity : Activity) {

    //Otteniamo il file di preferenze di default per l'activity MainActivity
    private lateinit var preferences : SharedPreferences

    fun initialize(){
        preferences = activity.getPreferences(Context.MODE_PRIVATE)
    }

    fun save(key : String, value : String){
        //Otteniamo un editor direttamente dal file di preferenze
        val editor : SharedPreferences.Editor = preferences.edit()

        //Salviamo il valore "valore" nel file di preferenze utilizzando il tag "CHIAVE"
        editor.putString(key, value)
        editor.apply()
    }

    fun load(key : String) : String? {
        return preferences.getString(key, "[]")
    }
}
