package com.example.duarteramosquizgame

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AlterarQuiz : AppCompatActivity() {

    private var quizId: Long = -1

    private lateinit var editTextTituloQuiz: EditText
    private lateinit var editTextDescricaoQuiz: EditText
    private lateinit var editTextTempoQuiz: EditText

    private lateinit var tvTituloAntigo: TextView
    private lateinit var tvDescricaoAntiga: TextView
    private lateinit var tvTempoAntigo: TextView

    private var tituloOriginal: String = ""
    private var descricaoOriginal: String = ""
    private var tempoOriginal: Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.editar_quiz)

        quizId = intent.getLongExtra("id_quiz", -1)
        editTextTituloQuiz = findViewById(R.id.et_titulo_quiz)
        editTextDescricaoQuiz = findViewById(R.id.et_descricao_quiz)
        editTextTempoQuiz = findViewById(R.id.et_tempo_quiz)

        tvTituloAntigo = findViewById(R.id.tv_titulo_antigo)
        tvDescricaoAntiga = findViewById(R.id.tv_descricao_antiga)
        tvTempoAntigo = findViewById(R.id.tv_tempo_antigo)

        val btnAlterarQuiz: Button = findViewById(R.id.btn_alterar_quiz)
        val btnCancelarQuiz : Button = findViewById(R.id.btn_cancelar_alteracoes)
        if (quizId.toInt() != -1){
            carregarQuizzes(quizId)
        }else{
            Toast.makeText(this, "Erro: ID do Quiz ausente.", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        btnAlterarQuiz.setOnClickListener {
            guardarAlteracoes()
        }
        btnCancelarQuiz.setOnClickListener{
            finish()
        }

    }
    @SuppressLint("SetTextI18n")
    private fun carregarQuizzes(id: Long){

        val request = QuizIdRequest(qid = id)
        ClienteRetrofit.instance.listarQuizId(request).enqueue(object : Callback<Quiz>{
            override fun onResponse(call: Call<Quiz>, response: Response<Quiz>) {
                if (response.isSuccessful){
                    val quiz = response.body()
                    if (quiz != null){
                        tituloOriginal = quiz.titulo
                        descricaoOriginal = quiz.descricao
                        tempoOriginal = quiz.tempo_max
                        tvTituloAntigo.setText(quiz.titulo)
                        tvDescricaoAntiga.setText(quiz.descricao)
                        tvTempoAntigo.setText(quiz.tempo_max.toString())
                        editTextTituloQuiz.setText(quiz.titulo)
                        editTextDescricaoQuiz.setText(quiz.descricao)
                        editTextTempoQuiz.setText(quiz.tempo_max.toString())
                    }
                }else{
                    Toast.makeText(this@AlterarQuiz, "Erro ao obter dados do servidor", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Quiz>, t: Throwable) {
                Toast.makeText(this@AlterarQuiz, "Falha de rede: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })

    }
    private fun guardarAlteracoes(){
        var novoTituloQuiz = editTextTituloQuiz.text.toString().trim()
        var novaDescricaoQuiz = editTextDescricaoQuiz.text.toString().trim()
        var novoTempoQuiz = editTextTempoQuiz.text.toString().trim()

        var tituloFinalQuiz: String
        var descricaoFinalQuiz: String
        var tempoFinalQuiz: String
        if (novoTituloQuiz.isEmpty())
            tituloFinalQuiz = tituloOriginal
        else
            tituloFinalQuiz = novoTituloQuiz
        if (novaDescricaoQuiz.isEmpty())
            descricaoFinalQuiz = descricaoOriginal
        else
            descricaoFinalQuiz = novaDescricaoQuiz
        if(novoTempoQuiz.isEmpty())
            tempoFinalQuiz = tempoOriginal.toString()
        else
            tempoFinalQuiz = novoTempoQuiz
        if (tituloFinalQuiz == tituloOriginal && descricaoFinalQuiz == descricaoOriginal && tempoFinalQuiz == tempoOriginal.toString()) {
            Toast.makeText(this, "Nenhuma alteração detectada.", Toast.LENGTH_SHORT).show()
            finish()
            return
        }
        val novoQuiz = Quiz(
            id = quizId,
            titulo = tituloFinalQuiz,
            descricao = descricaoFinalQuiz,
            tempo_max = tempoFinalQuiz.toInt()
        )
        ClienteRetrofit.instance.alterarQuiz(novoQuiz).enqueue(object : Callback<RegistoResposta> {
            override fun onResponse(call: Call<RegistoResposta>, response: Response<RegistoResposta>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@AlterarQuiz, "Quiz alterado com sucesso!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@AlterarQuiz, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    when(response.code()){
                        400 -> {
                            Toast.makeText(this@AlterarQuiz, "Este quiz está em execução!", Toast.LENGTH_LONG).show()
                        }
                        403 ->{
                            Toast.makeText(this@AlterarQuiz, "Houve um erro ao eliminar este quiz!", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
            override fun onFailure(call: Call<RegistoResposta>, t: Throwable) {
                Toast.makeText(this@AlterarQuiz, "Falha de rede: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}