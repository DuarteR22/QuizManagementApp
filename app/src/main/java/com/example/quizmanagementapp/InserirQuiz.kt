package com.example.quizmanagementapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
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


        btnGuardarQuiz.setOnClickListener {
            val tituloQuiz = editTextTituloQuiz.text.toString().trim()
            val descricaoQuiz = editTextDescricaoQuiz.text.toString().trim()
            val tempoQuiz = editTextTempoQuiz.text.toString().trim()



        }


    }

    fun validarCampos(tituloQuiz: String, descricaoQuiz: String, tempoQuiz: Int) : Boolean{

        if ((tituloQuiz.isEmpty() || descricaoQuiz.isEmpty() || tempoQuiz.()))
            if (tituloQuiz.isEmpty())
                editTextTituloQuiz.error = "todos os campos descritos são obrigatórios"
        if (descricaoQuiz.isEmpty())
            editTextDescricaoQuiz.error = "todos os campos descritos são obrigatórios"
        if (tempoQuiz.isEmpty())
            editTextTempoQuiz.error = "todos os campos descritos são obrigatórios"
        else
        return false
    }
}