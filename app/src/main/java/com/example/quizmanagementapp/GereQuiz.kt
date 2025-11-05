package com.example.quizmanagementapp

object GereQuiz{

    val listaQuizzes: MutableList<Quiz> = mutableListOf()

    fun adicionarQuiz(quiz: Quiz){
        listaQuizzes.add(quiz)
    }
    fun encontraQuiz(id: Int): Quiz?{

        if(id - 1 >= 0 && id-1 < listaQuizzes.size)
            return listaQuizzes[id-1]
        else
            return null
    }

}