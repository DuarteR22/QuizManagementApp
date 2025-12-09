package com.example.duarteramosquizgame

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.duarteramosquizgame.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var textViewQuizzes: TextView
    private lateinit var textViewQuestoes: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        val fabAdicionarQuiz: FloatingActionButton = findViewById(R.id.fab_add_quiz)
        val intentAdicionarQuiz = Intent(this, InserirQuiz::class.java)

        fabAdicionarQuiz.setOnClickListener{
            startActivity(intentAdicionarQuiz)
        }
        /**btnInserirQuiz.setOnClickListener {

            val intentQuiz = Intent(this, InserirQuiz::class.java)
            startActivity(intentQuiz)
        }
        btnInserirPergunta.setOnClickListener{
            val intentPergunta = Intent(this, InserirQuestao::class.java)
            startActivity(intentPergunta)
        }

        btnResolverPergunta.setOnClickListener{
            var editTextIndiceFinal = editTextIndice.text.toString().trim()
            if (editTextIndiceFinal.isNullOrEmpty())
                editTextIndice.error = "todos os campos são obrigatórios!"
            else {
                val id = editTextIndiceFinal.toInt()
                if (!editTextIndiceFinal.isEmpty()) {
                    if (id > 0) {

                        val questaoEncontrada = GereQuestoes.encontraQuestao(id)
                        if (questaoEncontrada != null) {
                            val intentResolver = Intent(this, ResolverQuestao::class.java)
                            intentResolver.putExtra("id_questao", id)
                            startActivity(intentResolver)
                        } else
                            Toast.makeText(
                                this,
                                "ID da questão não existe ou erro de escrita!",
                                Toast.LENGTH_SHORT
                            ).show()
                    }
                }
            }
        }**/
    }
    override fun onResume(){
        super.onResume()
    }
}