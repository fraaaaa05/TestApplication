package com.itis.testapplication.utilities

import android.app.Activity
import com.google.gson.Gson

class PreferenceParser<T : Any>(private val entityClass : Class<T>, activity : Activity) {

    private val KEY : String = entityClass.canonicalName!!
    private val manager = PreferenceManager(activity)

    fun initialize(){
        manager.initialize()
    }

    fun savePreference(preference : T){
        val json : String = Gson().toJson(preference)
        manager.save(KEY, json)
    }
    fun loadPreference() : T? {
        val element = manager.load(KEY)
        if(element == "[]")
            return entityClass.newInstance()
        return Gson().fromJson(element, entityClass)
    }

    fun clearPreferences(){
        savePreference(entityClass.newInstance())
    }

    inline fun <reified T : Any> getValue(): T? {
        val primaryConstructor = T::class.constructors.find {
            it.parameters.isEmpty()
        }
        return primaryConstructor?.call()
    }
}