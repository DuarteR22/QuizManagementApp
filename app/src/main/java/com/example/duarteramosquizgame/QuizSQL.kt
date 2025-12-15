package com.example.duarteramosquizgame

import android.content.ContentValues
import android.content.Context
import android.database.Cursor


class QuizSQL (context : Context){

    private val dbHelper : CriaDB = CriaDB(context)


    fun insereQuiz(titulo : String, descricao : String,  tempoMax : Int): Long {
        val db = dbHelper.writableDatabase
        val vals = ContentValues()
        vals.put("titulo", titulo)
        vals.put("descricao", descricao)
        vals.put("tempo_max", tempoMax)

        var newId: Long = -1
        db.beginTransaction()
        try {
            newId = db.insert(
                "quizzes",
                null,
                vals
            )
            if (newId > 0)
                db.setTransactionSuccessful()
        }catch (e: Exception){
            newId = -1
        }finally {
            db.endTransaction()
            db.close()
        }
     return newId
    }

    fun obterQuizzes(): Cursor {
        val db = dbHelper.readableDatabase
        return db.query(
            "quizzes", arrayOf("_id", "titulo", "descricao", "tempo_max"),
            null,
            null,
            null,
            null,
            "_id ASC"
        )
    }
    fun obterQuizId(id: Long): Cursor{
        val db = dbHelper.readableDatabase
        return db.query(
            "quizzes", arrayOf("_id", "titulo", "descricao", "tempo_max"),
            "_id=?", arrayOf(id.toString()),
            null,null,null
        )
    }
    fun eliminaQuiz(idQuiz: Long): Int {
        val db = dbHelper.writableDatabase
        val linhasAfetadas  = db.delete(
            "quizzes",
            "_id=?",
            arrayOf(idQuiz.toString())
        )
        db.close()
        return linhasAfetadas
    }
    fun alteraQuiz(id: Long, titulo: String, descricao: String, tempoMax: Int): Int{
        val db = dbHelper.writableDatabase
        val vals = ContentValues()
        vals.put("titulo", titulo)
        vals.put("descricao", descricao)
        vals.put("tempo_max", tempoMax)

        var linhasAfetadas = 0
        db.beginTransaction()
        try {
            linhasAfetadas = db.update(
                "quizzes", vals,
                "_id=?", arrayOf(id.toString())
            )
            if (linhasAfetadas > 0)
                db.setTransactionSuccessful()
        }finally {
            db.endTransaction()
            db.close()
        }
        return linhasAfetadas
    }
}