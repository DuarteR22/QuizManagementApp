package com.example.duarteramosquizgame

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.provider.BaseColumns
import java.io.File.separator

class QuestaoSQL(context : Context) {

    private val dbHelper: CriaDB = CriaDB(context)

    fun insereQuestao(questao: Questao): Long {
        val db = dbHelper.writableDatabase
        val vals = ContentValues()

        vals.put("id_quiz", questao.idQuiz)
        vals.put("questao", questao.pergunta)
        vals.put("num_respostas", questao.numRespostas)
        vals.put("respostas_opcoes", questao.respostas.joinToString(separator = "|"))
        vals.put("resposta_correta", questao.respostaCorreta)
        vals.put("url_imagem", questao.urlImagem)

        val newId = db.insert("questoes", null, vals)
        db.close()
        return newId
    }

    fun eliminaQuestao(questaoId: Long): Int {
        val db = dbHelper.writableDatabase
        val rowsAffected = db.delete(
            "questoes",
            "_id=?",
            arrayOf(questaoId.toString())
        )
        db.close()
        return rowsAffected
    }

    fun obterQuestoesQuizId(quizId: Long): Cursor {
        val db = dbHelper.readableDatabase
        val sql = "SELECT  _id, questao, url_imagem, num_respostas " +
                "FROM questoes " +
                "WHERE id_quiz = ? " +
                "ORDER BY _id ASC"

        return db.rawQuery(sql, arrayOf(quizId.toString()))
    }



    fun obterQuestaoId(questaoId: Long): Questao? {

        val db = dbHelper.readableDatabase
        var questao: Questao? = null
        var cursor: Cursor? = null

        val colunas = arrayOf(
            BaseColumns._ID,
            "id_quiz",
            "questao",
            "num_respostas",
            "respostas_opcoes",
            "resposta_correta",
            "url_imagem"
        )
        cursor = db.query(
            "questoes", colunas,
            "${BaseColumns._ID} = ?", arrayOf(questaoId.toString()),
            null, null, null,
            "1"
        )
        if (cursor.moveToFirst()) {

            val idQuizIndex = cursor.getColumnIndexOrThrow("id_quiz")
            val perguntaIndex= cursor.getColumnIndexOrThrow("questao")
            val numRespostasIndex = cursor.getColumnIndexOrThrow("num_respostas")
            val respostasOpcoesIndex = cursor.getColumnIndexOrThrow("respostas_opcoes")
            val respostaCorretaIndex = cursor.getColumnIndexOrThrow("resposta_correta")
            val urlImagemIndex = cursor.getColumnIndexOrThrow("url_imagem")

            val quizId = cursor.getLong(idQuizIndex)
            val pergunta = cursor.getString(perguntaIndex)
            val numRespostas = cursor.getInt(numRespostasIndex)
            val respostaCorreta = cursor.getInt(respostaCorretaIndex)
            val respostasOpcoes = cursor.getString(respostasOpcoesIndex)
            val respostasLista = respostasOpcoes.split("|")
            val urlImagem: String?
            if (cursor.isNull(urlImagemIndex))
                urlImagem = null
            else
                urlImagem = cursor.getString(urlImagemIndex)


            questao = Questao(
                id = questaoId,
                idQuiz = quizId,
                pergunta = pergunta,
                numRespostas = numRespostas,
                respostas = respostasLista,
                respostaCorreta = respostaCorreta,
                urlImagem = urlImagem
            )
        }
        cursor.close()
        db.close()
        return questao
    }
    fun alteraQuestao(questao: Questao): Int{
        val db = dbHelper.writableDatabase
        val vals = ContentValues()

        vals.put("questao", questao.pergunta)
        vals.put("num_respostas", questao.numRespostas)
        vals.put("respostas_opcoes", questao.respostas.joinToString (separator = "|"))
        vals.put("resposta_correta", questao.respostaCorreta)
        vals.put("url_imagem", questao.urlImagem)
        val selection = "${BaseColumns._ID} = ?"
        val selectionArgs = arrayOf(questao.id.toString())

        var linhasAfetadas = 0
        db.beginTransaction()
        try {
            linhasAfetadas = db.update(
                "questoes", vals,
                selection, selectionArgs
            )
            if (linhasAfetadas > 0)
                db.setTransactionSuccessful()

        }finally {
            db.endTransaction()
            db.close()
        }
        return linhasAfetadas
    }
        fun obterQuestoesQuizIdLista(quizId: Long): List<Questao> {
        val db = dbHelper.readableDatabase
        val listaQuestoes = mutableListOf<Questao>()
        var cursor: Cursor? = null
        val colunas = arrayOf(
            BaseColumns._ID,
            "id_quiz",
            "questao",
            "num_respostas",
            "respostas_opcoes",
            "resposta_correta",
            "url_imagem"
        )
        cursor = db.query(
            "questoes", colunas,
            "id_quiz = ?", arrayOf(quizId.toString()),
            null,
            null,
            "_ID ASC"
        )
        if(cursor.moveToFirst()){
            val idIndex = cursor.getColumnIndexOrThrow(BaseColumns._ID)
            val idQuizIndex = cursor.getColumnIndexOrThrow("id_quiz")
            val perguntaIndex = cursor.getColumnIndexOrThrow("questao")
            val numRespostasIndex = cursor.getColumnIndexOrThrow("num_respostas")
            val respostasOpcoesIndex = cursor.getColumnIndexOrThrow("respostas_opcoes")
            val respostaCorretaIndex = cursor.getColumnIndexOrThrow("resposta_correta")
            val urlImagemIndex = cursor.getColumnIndexOrThrow("url_imagem")

            do {
                val id = cursor.getLong(idIndex)
                val idQuiz = cursor.getLong(idQuizIndex)
                val pergunta = cursor.getString(perguntaIndex)
                val numRespostas = cursor.getInt(numRespostasIndex)
                val respostaCorreta = cursor.getInt(respostaCorretaIndex)
                val respostasOpcoes = cursor.getString(respostasOpcoesIndex)

                val listaRespostas = respostasOpcoes.split("|").filter { it.isNotBlank() }
                val urlImagem: String?
                if (cursor.isNull(urlImagemIndex))
                    urlImagem = null
                else
                    urlImagem = cursor.getString(urlImagemIndex)

                val questao = Questao(id,idQuiz,pergunta,numRespostas,listaRespostas,respostaCorreta,urlImagem)
                listaQuestoes.add(questao)
            }while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return listaQuestoes
    }
}