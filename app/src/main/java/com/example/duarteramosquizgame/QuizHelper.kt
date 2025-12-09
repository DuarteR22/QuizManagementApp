package com.example.duarteramosquizgame

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

class QuizHelper (context : Context){

    private val dbHelper : QuizOpenHelper = QuizOpenHelper(context)

    private val db : SQLiteDatabase by lazy {dbHelper.writableDatabase}

    fun insereQuiz(titulo : String, descricao : String,  tempoMax : Int): Long {
        val vals = ContentValues()
        vals.put("titulo", titulo)
        vals.put("descricao", descricao)
        vals.put("tempo_max", tempoMax)

        return db.insert(
            "quizzes",
            null,
            vals
        )
    }

    fun obterQuizzes(): Cursor {
        return db.query(
            "quizzes",
            arrayOf("_id", "titulo", "descricao", "tempo_max"),
            null,
            null,
            null,
            null,
            "_id ASC"
        )
    }
    fun eliminaQuiz(idQuiz: Long): Int{
        return db.delete(
            "quizzes",
            ":id=?",
            arrayOf(idQuiz.toString())
        )
    }
}