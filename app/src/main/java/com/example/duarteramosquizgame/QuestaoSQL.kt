package com.example.duarteramosquizgame

import android.content.ContentValues
import android.content.Context
import android.database.Cursor

class QuestaoSQL(context : Context) {

    private val dbHelper: CriaDB = CriaDB(context)

    fun insereQuestao(questao : Questao): Long {
        val db = dbHelper.writableDatabase
        val vals = ContentValues()

        vals.put("id_quiz", questao.idQuiz)
        vals.put("questao", questao.pergunta)
        vals.put("num_respostas", questao.numRespostas)
        vals.put("repostas_opcoes", questao.respostas.joinToString {"|"})
        vals.put("resposta_correta", questao.respostaCorreta)
        vals.put("url_imagem", questao.urlImagem)

        val newId = db.insert("questoes", null, vals)
        db.close()
        return newId
    }

    fun obterQuestoesIdQuiz(quizId: Long): Cursor {
        val db = dbHelper.readableDatabase
        val selection = "id_quiz = ?"
        val selectionArgs = arrayOf(quizId.toString())

        return db.query(
            "questoes",
            null, // Retorna todas as colunas
            selection,
            selectionArgs,
            null,
            null,
            null
        )
    }
    fun eliminaQuestao(questaoId: Long):Int{
        val db = dbHelper.writableDatabase
        val rowsAffected = db.delete("questoes", "_id=?", arrayOf(questaoId.toString()))
        db.close()
        return rowsAffected
    }
}