package com.example.quizmanagementapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class InserirQuiz : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.inserir_quiz)

        val editTextTituloQuiz: EditText = findViewById(R.id.et_titulo_quiz)
        val editTextDescricaoQuiz: EditText = findViewById(R.id.et_descricao_quiz)
        val editTextTempoQuiz: EditText = findViewById(R.id.et_tempo_quiz)

        val btnGuardarQuiz: Button = findViewById(R.id.btn_guardar_quiz)
        val btnCancelarQuiz : Button = findViewById(R.id.btn_cancelar_quiz)

        val intentCancelar = Intent(this, MainActivity::class.java)

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
                val quiz = Quiz(tituloQuiz, descricaoQuiz, tempoQuizFinal)
                GereQuiz.adicionarQuiz(quiz)
                Toast.makeText(this, "Quiz guardado com sucesso!", Toast.LENGTH_SHORT).show()
                startActivity(intentCancelar)
            }
        }
        btnCancelarQuiz.setOnClickListener{
            startActivity(intentCancelar)
        }
    }



}