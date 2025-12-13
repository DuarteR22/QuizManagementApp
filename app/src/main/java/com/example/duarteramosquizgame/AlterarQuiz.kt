package com.example.duarteramosquizgame

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AlterarQuiz : AppCompatActivity() {

    private lateinit var quizSQL: QuizSQL
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

        quizSQL = QuizSQL(this)

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
            carregarDadosQuiz(quizId)
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
    private fun carregarDadosQuiz(id: Long){

        val cursor = quizSQL.obterQuizId(id)
        if (cursor.moveToFirst()){
            val titulo = cursor.getString(cursor.getColumnIndexOrThrow("titulo"))
            val descricao = cursor.getString(cursor.getColumnIndexOrThrow("descricao"))
            val tempo = cursor.getInt(cursor.getColumnIndexOrThrow("tempo_max"))

            tituloOriginal = titulo
            descricaoOriginal = descricao
            tempoOriginal = tempo

            tvTituloAntigo.setText(titulo)
            tvDescricaoAntiga.setText(descricao)
            tvTempoAntigo.setText(tempo.toString())
        }else{
            Toast.makeText(this, "Quiz nao encontrado", Toast.LENGTH_LONG).show()
        }
        cursor.close()
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
        val linhasAfetadas = quizSQL.alteraQuiz(quizId,tituloFinalQuiz,descricaoFinalQuiz,tempoFinalQuiz.toInt())

        if (linhasAfetadas > 0){
            Toast.makeText(this, "Quiz alterado com sucesso", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }else {
            Toast.makeText(this, "Erro: Nenhuma alteração guardada ou quiz não encontrado.", Toast.LENGTH_SHORT).show()
        }
    }
}