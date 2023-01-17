package com.itis.testapplication.model

class Storico (var totaleTentativi : Int = 0, var numPartite : Int = 1, var points : Int = 0){

    var partite = mutableListOf<Partita>()
}

class Partita(){

    var punti : Int = 0
    var tentativi : Int = 0

    override fun toString(): String {
        return "Punti: " + punti + " - Tentativi: " + tentativi
    }
}
