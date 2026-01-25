package com.example.duarteramosquizgame

import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {


    private lateinit var recyclerViewQuizzes: RecyclerView
    private lateinit var quizAdapter: QuizAdapter
    private var listaCompleta: List<Quiz> = emptyList()
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        recyclerViewQuizzes = findViewById(R.id.recyclerView_quizzes)
        recyclerViewQuizzes.layoutManager = LinearLayoutManager(this)
        val btnAdicionarQuiz: ImageButton = findViewById(R.id.btn_adicionar_quiz)
        val btnInformacoes: ImageButton = findViewById(R.id.btn_detalhes)
        val switchFiltrar: SwitchCompat = findViewById(R.id.sw_filtrar)

        switchFiltrar.setOnCheckedChangeListener { _, isChecked ->
            aplicarFiltro(isChecked)
        }

        btnAdicionarQuiz.setOnClickListener{
            val intentAdicionarQuiz = Intent(this, InserirQuiz::class.java)
            startActivity(intentAdicionarQuiz)
        }
        btnInformacoes.setOnClickListener {
            val intentPerfil = Intent(this, Perfil::class.java)
            startActivity(intentPerfil)
        }
    }
    override fun onResume(){
        super.onResume()
        carregaQuizzes()
    }
    fun carregaQuizzes() {
        val sharedPref = getSharedPreferences("sessao", MODE_PRIVATE)
        val uidLogado = sharedPref.getInt("u_uid", -1)
        val tokenGuardado = sharedPref.getString("token", "")
        val authHeader = "Bearer $tokenGuardado"
        ClienteRetrofit.instance.listarQuizzes(authHeader).enqueue(object : Callback<List<Quiz>> {
            override fun onResponse(call: Call<List<Quiz>>, response: Response<List<Quiz>>) {
                if (response.isSuccessful) {
                    listaCompleta = response.body() ?: emptyList()

                        quizAdapter = QuizAdapter(this@MainActivity, listaCompleta, uidLogado)
                        recyclerViewQuizzes.adapter = quizAdapter
                } else if(response.code() == 401) {
                    val sharedPref = getSharedPreferences("sessao", MODE_PRIVATE)
                    with(sharedPref.edit()) {
                        clear()
                        apply()
                    }
                    Toast.makeText(this@MainActivity, "Sessão expirada", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@MainActivity, Login::class.java))
                    finish()
                }
            }
            override fun onFailure(call: Call<List<Quiz>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Falha de rede: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
    private fun aplicarFiltro(apenasMenus: Boolean){
        val sharedPref = getSharedPreferences("sessao", MODE_PRIVATE)
        val uidLogado = sharedPref.getInt("u_uid", -1)

        if (apenasMenus){
            val listaFiltrada = mutableListOf<Quiz>()

            for (quiz in listaCompleta){
                if (quiz.utilizador_uid == uidLogado){
                    listaFiltrada.add(quiz)
                }
            }
            quizAdapter.atualizarDados(listaFiltrada)
        }else
            quizAdapter.atualizarDados(listaCompleta)
    }
}
