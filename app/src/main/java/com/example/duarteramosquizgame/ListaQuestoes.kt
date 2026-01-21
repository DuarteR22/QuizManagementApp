package com.example.duarteramosquizgame

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListaQuestoes: AppCompatActivity() {

    private lateinit var recyclerViewQuestoes: RecyclerView
    private lateinit var questaoAdapter: QuestaoAdapter
    private var quizId: Long = -1
    private var criadorId: Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lista_questoes)

        quizId = intent.getLongExtra("id_quiz", -1)
        criadorId = intent.getIntExtra("utilizador_uid", -1)
        recyclerViewQuestoes = findViewById(R.id.recyclerView_questoes)
        recyclerViewQuestoes.layoutManager = LinearLayoutManager(this)

        if(quizId == -1L){
            finish()
            return
        }
        val buttonInserirQuestao: ImageButton = findViewById(R.id.btn_adicionar_questao)

        buttonInserirQuestao.setOnClickListener{
            val intent = Intent(this, InserirQuestao::class.java)
            intent.putExtra("id_quiz", quizId)
            startActivity(intent)
        }
        val fabResolverQuiz: FloatingActionButton = findViewById(R.id.fab_resolver_quiz)
        fabResolverQuiz.setOnClickListener {
            val intent = Intent(this, ResolverQuiz::class.java)
            intent.putExtra("id_quiz", quizId)
            startActivity(intent)
        }
    }
    override fun onResume() {
        super.onResume()
        carregaQuestoesDB()
    }
    private fun carregaQuestoesDB(){
        val sharedPref = getSharedPreferences("sessao", MODE_PRIVATE)
        val uidLogado = sharedPref.getInt("u_uid", -1)
        val request = ListaQuestaoRequest(qid = quizId)
        ClienteRetrofit.instance.listarQuestoes(request).enqueue(object : Callback<List<Questao>> {
            override fun onResponse(call: Call<List<Questao>>, response: Response<List<Questao>>) {
                if (response.isSuccessful) {
                    val lista = response.body() ?: emptyList()
                    questaoAdapter = QuestaoAdapter(this@ListaQuestoes, lista, uidLogado, criadorId)
                    recyclerViewQuestoes.adapter = questaoAdapter
                } else {
                    Toast.makeText(this@ListaQuestoes, "Erro ao listar questões", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<List<Questao>>, t: Throwable) {
                Toast.makeText(this@ListaQuestoes, "Falha de rede: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}