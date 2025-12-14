package com.example.duarteramosquizgame

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {


    private lateinit var recyclerViewQuizzes: RecyclerView
    private lateinit var quizSQL: QuizSQL
    private lateinit var quizAdapter: QuizAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        quizSQL = QuizSQL(this)

        recyclerViewQuizzes = findViewById(R.id.recyclerView_quizzes)
        recyclerViewQuizzes.layoutManager = LinearLayoutManager(this)
        val fabAdicionarQuiz: FloatingActionButton = findViewById(R.id.fab_adicionar_quiz)

        fabAdicionarQuiz.setOnClickListener{
            val intentAdicionarQuiz = Intent(this, InserirQuiz::class.java)
            startActivity(intentAdicionarQuiz)
        }

    }
    override fun onResume(){
        super.onResume()
        carregaQuizzesDB()
    }

    private fun carregaQuizzesDB(){

        val cursor: Cursor = quizSQL.obterQuizzes()

        if (!::quizAdapter.isInitialized){
            quizAdapter = QuizAdapter(this, cursor,quizSQL )
            recyclerViewQuizzes.adapter = quizAdapter
        }else
            quizAdapter.changeCursor(cursor)
    }
}
