package com.example.duarteramosquizgame
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Response

class InserirQuiz : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.inserir_quiz)

        val editTextTituloQuiz: EditText = findViewById(R.id.et_titulo_quiz)
        val editTextDescricaoQuiz: EditText = findViewById(R.id.et_descricao_quiz)
        val editTextTempoQuiz: EditText = findViewById(R.id.et_tempo_quiz)

        val btnGuardarQuiz: Button = findViewById(R.id.btn_guardar_quiz)
        val btnCancelarQuiz : Button = findViewById(R.id.btn_cancelar_quiz)


        btnGuardarQuiz.setOnClickListener {
            val tituloQuiz = editTextTituloQuiz.text.toString().trim()
            val descricaoQuiz = editTextDescricaoQuiz.text.toString().trim()
            var tempoQuiz = editTextTempoQuiz.text.toString().trim()

            if(tituloQuiz.isEmpty() || descricaoQuiz.isEmpty() || tempoQuiz.isEmpty()){

                if (tituloQuiz.isEmpty())
                    editTextTituloQuiz.error = "todos os campos descritos são obrigatórios"
                if (descricaoQuiz.isEmpty())
                    editTextDescricaoQuiz.error = "todos os campos descritos são obrigatórios"
                if (tempoQuiz.isEmpty())
                    editTextTempoQuiz.error = "todos os campos descritos são obrigatórios"
                return@setOnClickListener
            }
            val sharedPref = getSharedPreferences("sessao", MODE_PRIVATE)
            val u_uid = sharedPref.getInt("u_uid", -1)
            if (u_uid == -1){
                Toast.makeText(this, "Erro: Utilizador não autenticado", Toast.LENGTH_SHORT).show()
            }
            val novoQuiz = Quiz(titulo = tituloQuiz, descricao = descricaoQuiz, tempo_max = tempoQuiz.toInt(), utilizador_uid = u_uid)
            ClienteRetrofit.instance.inserirQuiz(novoQuiz).enqueue(object : retrofit2.Callback<RegistoQuizResposta>{
                override fun onResponse(
                    call: Call<RegistoQuizResposta>,
                    response: Response<RegistoQuizResposta>
                ) {
                    if (response.isSuccessful){
                        val qidRetornado = response.body()?.qid ?: -1
                        when(qidRetornado){
                            -2 ->{
                                editTextTituloQuiz.error = "Já existe um quiz com este título!"
                            }-1 ->{
                                Toast.makeText(this@InserirQuiz, "Erro ao inserir quiz", Toast.LENGTH_SHORT).show()
                            }
                            else -> {
                                Toast.makeText(this@InserirQuiz, "Quiz inserido com sucesso! ID: $qidRetornado", Toast.LENGTH_SHORT).show()
                                finish()
                            }
                        }
                    }else
                        Toast.makeText(this@InserirQuiz, "Erro ao inserir quiz", Toast.LENGTH_SHORT).show()
                }
                override fun onFailure(call: Call<RegistoQuizResposta>, t: Throwable) {
                    Toast.makeText(this@InserirQuiz, "Falha de rede: ${t.message}", Toast.LENGTH_LONG).show()
                }
            })
        }
        btnCancelarQuiz.setOnClickListener{
            finish()
        }
    }
}