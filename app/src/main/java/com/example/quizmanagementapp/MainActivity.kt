package com.example.quizmanagementapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

import com.example.quizmanagementapp.ui.theme.QuizManagementAppTheme
import java.sql.Types.NULL

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        val btnInserirQuiz: Button = findViewById(R.id.btn_inserir_quiz)
        val btnInserirPergunta: Button = findViewById(R.id.btn_inserir_questao)

        val editTextIndice: EditText = findViewById(R.id.et_indice_questao)
        val btnResolverPergunta: Button = findViewById(R.id.btn_resolve_questao)

        val textViewQuizzes: TextView = findViewById(R.id.tv_numero_quizzes)
        val textViewQuestoes: TextView = findViewById(R.id.tv_numero_questoes)

        textViewQuizzes.setText("Numero de Quizzes: "+ GereQuiz.numeroQuizzes().toString())
        textViewQuestoes.setText("Numero de Questões: " + GereQuestoes.numeroQuestoes().toString())
        btnInserirQuiz.setOnClickListener {

            val intentQuiz = Intent(this, InserirQuiz::class.java)
            startActivity(intentQuiz)
        }
        btnInserirPergunta.setOnClickListener{
            val intentPergunta = Intent(this, InserirQuestao::class.java)
            startActivity(intentPergunta)
        }

        btnResolverPergunta.setOnClickListener{
            var editTextIndiceFinal = editTextIndice.text.toString().trim()
            val id = editTextIndiceFinal.toInt()
            if (!editTextIndiceFinal.isEmpty()){
                if (id > 0 ){

                    val questaoEncontrada = GereQuestoes.encontraQuestao(id)
                    if (questaoEncontrada != null){
                        val intentResolver = Intent(this, ResolverQuestao::class.java)
                        intentResolver.putExtra("id_questao",id)
                        startActivity(intentResolver)
                    }
                    else
                        Toast.makeText(this, "ID da questão não existe ou erro de escrita!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}