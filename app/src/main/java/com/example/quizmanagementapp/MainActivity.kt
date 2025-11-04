package com.example.quizmanagementapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

import com.example.quizmanagementapp.ui.theme.QuizManagementAppTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        val btnInserirQuiz: Button = findViewById(R.id.btn_inserir_quiz)
        val btnInserirPergunta: Button = findViewById(R.id.btn_inserir_questao)

        val editTextIndice: EditText = findViewById(R.id.et_indice_questao)

        btnInserirQuiz.setOnClickListener {

            val intentQuiz = Intent(this, InserirQuiz::class.java)
            startActivity(intentQuiz)
        }


    }
}