package com.example.duarteramosquizgame

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListaQuestoes: AppCompatActivity() {

    private lateinit var questaoSQL: QuestaoSQL
    private lateinit var recyclerViewQuestoes: RecyclerView
    private lateinit var questaoAdapter: QuestaoAdapter
    private var quizId: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lista_questoes)
        questaoSQL = QuestaoSQL(this)

        quizId = intent.getLongExtra("id_quiz", -1)
        recyclerViewQuestoes = findViewById(R.id.recyclerView_questoes)
        recyclerViewQuestoes.layoutManager = LinearLayoutManager(this)

        if(quizId == -1L){
            finish()
            return
        }
        val fabInserirQuestao: FloatingActionButton = findViewById(R.id.fab_adicionar_questao)

        fabInserirQuestao.setOnClickListener{
            val intent = Intent(this, InserirQuestao::class.java)
            intent.putExtra("id_quiz", quizId)
            startActivity(intent)
        }
    }
    override fun onResume() {
        super.onResume()
        carregaQuestoesDB()
    }
    private fun carregaQuestoesDB(){
        val cursor : Cursor = questaoSQL.obterQuestoesQuizId(quizId)

        if(!::questaoAdapter.isInitialized){
            questaoAdapter = QuestaoAdapter(this,cursor,questaoSQL)
            recyclerViewQuestoes.adapter = questaoAdapter
        }else{
            questaoAdapter.changeCursor(cursor)
        }
    }
}