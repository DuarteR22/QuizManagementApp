package com.example.duarteramosquizgame

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

class CriaDB (context: Context) : SQLiteOpenHelper(
    context, DATABASE_NAME, null, DATABASE_VERSION
){
    //Atributos da tabela quizzes
    private val NOME_TABELA_QUIZ = "quizzes"
    private val QUIZ_TITULO = "titulo"
    private val QUIZ_DESCRICAO = "descricao"
    private val QUIZ_TEMPO_MAX = "tempo_max"
    //Criação da tabela
    private val SQL_CREATE_TABLE_QUIZ =
         "CREATE TABLE $NOME_TABELA_QUIZ (" +
         "${BaseColumns._ID} INTEGER PRIMARY KEY," +
         "$QUIZ_TITULO TEXT NOT NULL," +
         "$QUIZ_DESCRICAO TEXT NOT NULL," +
         "$QUIZ_TEMPO_MAX INTEGER)"

    //Atributos da tabela questões
    private val NOME_TABELA_QUESTOES = "questoes"
    private val QUESTAO = "questao"
    private val QUESTAO_ID_QUIZ = "id_quiz"
    private val QUESTAO_NUM_RESPOSTAS = "num_respostas"
    private val QUESTAO_RESPOSTAS = "respostas_opcoes"
    private val QUESTAO_RESPOSTA_CORRETA = "resposta_correta"
    private val QUESTAO_URL_IMAGEM = "url_imagem"

    //Criação da tabela de questões
    private val SQL_CREATE_TABLE_QUESTAO =
        "CREATE TABLE $NOME_TABELA_QUESTOES ("+
        "${BaseColumns._ID} INTEGER PRIMARY KEY," +
        "$QUESTAO TEXT NOT NULL," +
        "$QUESTAO_ID_QUIZ INTEGER NOT NULL," +
        "$QUESTAO_NUM_RESPOSTAS INTEGER NOT NULL," +
        "$QUESTAO_RESPOSTAS TEXT NOT NULL," +
        "$QUESTAO_RESPOSTA_CORRETA INTEGER NOT NULL," +
        "$QUESTAO_URL_IMAGEM TEXT," +
        "FOREIGN KEY (${QUESTAO_ID_QUIZ}) REFERENCES $NOME_TABELA_QUIZ(${BaseColumns._ID}) ON DELETE CASCADE)"



    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_TABLE_QUIZ)
        db?.execSQL(SQL_CREATE_TABLE_QUESTAO)

    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $NOME_TABELA_QUESTOES")
        db.execSQL("DROP TABLE IF EXISTS $NOME_TABELA_QUIZ")
        onCreate(db)
    }
    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "QuizQuestionManagement.db"
    }

}