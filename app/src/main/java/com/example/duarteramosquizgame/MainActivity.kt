package com.example.duarteramosquizgame

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {


    private lateinit var recyclerViewQuizzes: RecyclerView
    private lateinit var quizAdapter: QuizAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        recyclerViewQuizzes = findViewById(R.id.recyclerView_quizzes)
        recyclerViewQuizzes.layoutManager = LinearLayoutManager(this)
        val btnAdicionarQuiz: ImageButton = findViewById(R.id.btn_adicionar_quiz)
        val btnInformacoes: ImageButton = findViewById(R.id.btn_detalhes)
        btnAdicionarQuiz.setOnClickListener{
            val intentAdicionarQuiz = Intent(this, InserirQuiz::class.java)
            startActivity(intentAdicionarQuiz)
        }
        btnInformacoes.setOnClickListener {
            val intentDetalhes = Intent(this, Detalhes::class.java)
            startActivity(intentDetalhes)
        }
    }
    override fun onResume(){
        super.onResume()
        carregaQuizzes()
    }
    private fun carregaQuizzes() {
        val sharedPref = getSharedPreferences("sessao", MODE_PRIVATE)
        val uidLogado = sharedPref.getInt("u_uid", -1)

        ClienteRetrofit.instance.getQuizzes().enqueue(object : Callback<List<Quiz>> {
            override fun onResponse(call: Call<List<Quiz>>, response: Response<List<Quiz>>) {
                if (response.isSuccessful) {
                    val listaQuizzes = response.body() ?: emptyList()
                    if (!::quizAdapter.isInitialized) {
                        quizAdapter = QuizAdapter(this@MainActivity, listaQuizzes, uidLogado)
                        recyclerViewQuizzes.adapter = quizAdapter
                    } else {
                        quizAdapter.atualizarDados(listaQuizzes)
                    }
                } else {
                    Toast.makeText(this@MainActivity, "Erro ao carregar os quizzes", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<List<Quiz>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Falha de rede: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}
