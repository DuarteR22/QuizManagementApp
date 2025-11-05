package com.example.quizmanagementapp
import android.widget.Toast

object GereQuestoes {

    val listaQuestoes: MutableList<Questao> = mutableListOf()

    fun adicionarQuestao(questao: Questao){
        listaQuestoes.add(questao)
    }
    fun encontraQuestao(id: Int): Questao?{

        if(id - 1 >= 0 && id-1 < listaQuestoes.size)
            return listaQuestoes[id-1]
        else
            return null
    }
}