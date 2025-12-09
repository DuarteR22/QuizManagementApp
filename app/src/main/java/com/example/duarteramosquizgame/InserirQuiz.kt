package com.example.duarteramosquizgame

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.duarteramosquizgame.R

class InserirQuiz : AppCompatActivity() {

    private lateinit var quizHelper: QuizHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.inserir_quiz)

        quizHelper = QuizHelper(this)
        val editTextTituloQuiz: EditText = findViewById(R.id.et_titulo_quiz)
        val editTextDescricaoQuiz: EditText = findViewById(R.id.et_descricao_quiz)
        val editTextTempoQuiz: EditText = findViewById(R.id.et_tempo_quiz)

        val btnGuardarQuiz: Button = findViewById(R.id.btn_guardar_quiz)
        val btnCancelarQuiz : Button = findViewById(R.id.btn_cancelar_quiz)


        btnGuardarQuiz.setOnClickListener {
            val tituloQuiz = editTextTituloQuiz.text.toString().trim()
            val descricaoQuiz = editTextDescricaoQuiz.text.toString().trim()
            val tempoQuiz = editTextTempoQuiz.text.toString().trim()

            if(tituloQuiz.isEmpty() || descricaoQuiz.isEmpty() || tempoQuiz.isEmpty()){

                if (tituloQuiz.isEmpty())
                    editTextTituloQuiz.error = "todos os campos descritos são obrigatórios"
                if (descricaoQuiz.isEmpty())
                    editTextDescricaoQuiz.error = "todos os campos descritos são obrigatórios"
                if (tempoQuiz.isEmpty())
                    editTextTempoQuiz.error = "todos os campos descritos são obrigatórios"
            }
            else{
                val tempoQuizFinal = tempoQuiz.toInt()
                Toast.makeText(this, "Quiz guardado com sucesso!", Toast.LENGTH_SHORT).show()
                finish()
                val id = quizHelper.insereQuiz(tituloQuiz,descricaoQuiz,tempoQuizFinal)
                if (id > 0){
                    Toast.makeText(this, "O Quiz foi guardado com sucesso, ID: $id", Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(this, "Erro ao guardar quiz.", Toast.LENGTH_SHORT).show()
                }
                finish()
            }
        }
        btnCancelarQuiz.setOnClickListener{
            finish()
        }
    }
}