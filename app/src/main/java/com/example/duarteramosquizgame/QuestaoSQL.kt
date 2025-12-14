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
        vals.put("repostas_opcoes", questao.respostas.joinToString {"|"})
        vals.put("resposta_correta", questao.respostaCorreta)
        vals.put("url_imagem", questao.urlImagem)

        val newId = db.insert("questoes", null, vals)
        db.close()
        return newId
    }
    fun eliminaQuestao(questaoId: Long):Int{
        val db = dbHelper.writableDatabase
        val rowsAffected = db.delete("questoes", "_id=?", arrayOf(questaoId.toString()))
        db.close()
        return rowsAffected
    }
    fun obterQuestoesQuizId(quizId: Long):Cursor{
        val db = dbHelper.readableDatabase
        val sql = "SELECT  q._id, q.pergunta, q.url_imagem, COUNT(r._id) " +
                "AS num_respostas " +
                "FROM questoes q " +
                "LEFT JOIN respostas r ON q._id = r.id_questao " +
                "WHERE q.id_quiz = ? " +
                "GROUP BY q._id, q.pergunta, q.url_imagem " +
                "ORDER BY q._id ASC"

        return db.rawQuery(sql, arrayOf(quizId.toString()))
    }
}