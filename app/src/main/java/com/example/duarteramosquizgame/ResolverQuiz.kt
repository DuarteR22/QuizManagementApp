package com.example.duarteramosquizgame

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity
import android.graphics.Color
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Callback
import retrofit2.Response

class ResolverQuiz: AppCompatActivity() {

    private var quizId: Long = -1
    private var listaQuestoes: List<Questao> = emptyList()
    private var pontuacao: Int = 0
    private var indiceQuestao: Int = 0
    private var respostaSelecionada: Int = 0
    private var estadoSubmetido: Boolean = false

    private lateinit var buttonResposta1: ToggleButton
    private lateinit var buttonResposta2: ToggleButton
    private lateinit var buttonResposta3: ToggleButton
    private lateinit var buttonResposta4: ToggleButton
    private lateinit var textViewTitulo: TextView
    private lateinit var textViewEnunciado: TextView
    private lateinit var buttonSubmeter: Button
    private lateinit var buttonSeguinte: Button
    private lateinit var buttonCancelar: Button
    private lateinit var textViewTempo: TextView
    private val corDefault = Color.parseColor("#FF8C00")
    private val corVerde = Color.parseColor("#4CAF50")
    private val corVermelho = Color.parseColor("#F44336")
    private var tempoRestante = 60
    private var cronometroJob: Job? = null
    private lateinit var imageViewQuestao: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.resolver_questao)

        textViewEnunciado = findViewById(R.id.tv_titulo_pergunta)
        textViewTitulo = findViewById(R.id.tv_titulo)
        textViewTempo = findViewById(R.id.tv_cronometro)
        buttonResposta1 = findViewById(R.id.btn_resposta_1)
        buttonResposta2 = findViewById(R.id.btn_resposta_2)
        buttonResposta3 = findViewById(R.id.btn_resposta_3)
        buttonResposta4 = findViewById(R.id.btn_resposta_4)
        imageViewQuestao = findViewById(R.id.iv_imagem_questao)
        quizId = intent.getLongExtra("id_quiz", -1)

        buttonSubmeter = findViewById(R.id.btn_submeter)
        buttonSeguinte = findViewById(R.id.btn_seguinte)
        buttonCancelar = findViewById(R.id.btn_cancelar)

        if (quizId == -1L)
            finish()

        val request = ListaQuestaoRequest(qid = quizId)
        ClienteRetrofit.instance.listarQuestoes(request).enqueue(object : Callback<List<Questao>>{
            override fun onResponse(
                call: retrofit2.Call<List<Questao>>,
                response: Response<List<Questao>>
            ) {
                if(response.isSuccessful){
                    listaQuestoes = response.body() ?: emptyList()
                    if (listaQuestoes.isEmpty()) {
                        finish()
                    } else {
                        configurarListeners()
                        carregarQuestao(indiceQuestao)
                        iniciarTempo()
                    }
                }else {
                    Toast.makeText(
                        this@ResolverQuiz,
                        "Erro ao carregar questões",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }
            }

            override fun onFailure(call: retrofit2.Call<List<Questao>>, t: Throwable) {
                Toast.makeText(this@ResolverQuiz, "Falha de rede: ${t.message}", Toast.LENGTH_LONG).show()
                finish()
            }
        })
    }
    fun configurarListeners(){

        buttonResposta1.setOnClickListener{
            if(estadoSubmetido == false){
                if (buttonResposta1.isChecked){

                    buttonResposta2.isChecked = false
                    buttonResposta3.isChecked = false
                    buttonResposta4.isChecked = false
                    respostaSelecionada = 1
                }else
                    respostaSelecionada = 0
            }else
                buttonResposta1.isChecked = !buttonResposta1.isChecked
        }
        buttonResposta2.setOnClickListener{
            if (!estadoSubmetido) {
                if (buttonResposta2.isChecked) {
                    buttonResposta1.isChecked = false
                    buttonResposta3.isChecked = false
                    buttonResposta4.isChecked = false
                    respostaSelecionada = 2
                } else
                    respostaSelecionada = 0
            } else
                buttonResposta2.isChecked = !buttonResposta2.isChecked
        }
        buttonResposta3.setOnClickListener {
            if (!estadoSubmetido) {
                if (buttonResposta3.isChecked) {
                    buttonResposta1.isChecked = false
                    buttonResposta2.isChecked = false
                    buttonResposta4.isChecked = false
                    respostaSelecionada = 3
                } else
                    respostaSelecionada = 0
            } else
                buttonResposta3.isChecked = !buttonResposta3.isChecked
        }
        buttonResposta4.setOnClickListener {
            if (!estadoSubmetido) {
                if (buttonResposta4.isChecked) {
                    buttonResposta1.isChecked = false
                    buttonResposta2.isChecked = false
                    buttonResposta3.isChecked = false
                    respostaSelecionada = 4
                } else
                    respostaSelecionada = 0
            } else
                buttonResposta4.isChecked = !buttonResposta4.isChecked

        }
        buttonSubmeter.setOnClickListener{
            if (respostaSelecionada != 0)
                submeterResposta()
            else
                Toast.makeText(this, "Selecione uma resposta", Toast.LENGTH_SHORT).show()
        }
        buttonSeguinte.setOnClickListener {
            indiceQuestao++
            if (indiceQuestao < listaQuestoes.size)
                carregarQuestao(indiceQuestao)
            else
                pontuacaoFinal()
        }
        buttonCancelar.setOnClickListener {
            pontuacaoFinal()
        }
    }
    private fun submeterResposta(){
        val questao = listaQuestoes[indiceQuestao]
        estadoSubmetido = true
        var correta: Boolean
        if (respostaSelecionada == questao.respostaCorreta)
            correta = true
        else
            correta = false

        buttonSubmeter.visibility = View.GONE
        buttonResposta1.isEnabled = false
        buttonResposta2.isEnabled = false
        buttonResposta3.isEnabled = false
        buttonResposta4.isEnabled = false

        if (correta == true){
            pontuacao++
            when (respostaSelecionada) {
                1 -> buttonResposta1.setBackgroundColor(corVerde)
                2 -> buttonResposta2.setBackgroundColor(corVerde)
                3 -> buttonResposta3.setBackgroundColor(corVerde)
                4 -> buttonResposta4.setBackgroundColor(corVerde)
            }
        }
        else{
            buttonResposta1.setBackgroundColor(corVermelho)
            buttonResposta2.setBackgroundColor(corVermelho)

            if (questao.numRespostas >=3)
                buttonResposta3.setBackgroundColor(corVermelho)
            if (questao.numRespostas >=4)
                buttonResposta4.setBackgroundColor(corVermelho)
            when (respostaSelecionada) {
                1 -> buttonResposta1.setBackgroundColor(corVermelho)
                2 -> buttonResposta2.setBackgroundColor(corVermelho)
                3 -> buttonResposta3.setBackgroundColor(corVermelho)
                4 -> buttonResposta4.setBackgroundColor(corVermelho)
            }
            when (questao.respostaCorreta) {
                1 -> buttonResposta1.setBackgroundColor(corVerde)
                2 -> buttonResposta2.setBackgroundColor(corVerde)
                3 -> buttonResposta3.setBackgroundColor(corVerde)
                4 -> buttonResposta4.setBackgroundColor(corVerde)
            }
        }
        buttonSeguinte.visibility = View.VISIBLE
    }
    @SuppressLint("SetTextI18n")
    private fun carregarQuestao(indice: Int){
        val questao = listaQuestoes[indice]
        estadoSubmetido = false
        respostaSelecionada = 0
        buttonSeguinte.visibility = View.GONE
        buttonSubmeter.visibility = View.VISIBLE

        buttonResposta1.setBackgroundResource(R.drawable.botao_resposta_background)
        buttonResposta2.setBackgroundResource(R.drawable.botao_resposta_background)
        buttonResposta3.setBackgroundResource(R.drawable.botao_resposta_background)
        buttonResposta4.setBackgroundResource(R.drawable.botao_resposta_background)
        buttonResposta1.isChecked = false
        buttonResposta2.isChecked = false
        buttonResposta3.isChecked = false
        buttonResposta4.isChecked = false
        buttonResposta1.isEnabled = true
        buttonResposta2.isEnabled = true
        buttonResposta3.isEnabled = true
        buttonResposta4.isEnabled = true

        textViewTitulo.text = "Questao ${indice+1} de ${listaQuestoes.size}"
        textViewEnunciado.text = questao.pergunta
        val respostas = questao.respostas
        val numRespostas = questao.numRespostas
        if (!questao.urlImagem.isNullOrEmpty()){
            imageViewQuestao.visibility = View.VISIBLE
            Glide.with(this)
                .load(questao.urlImagem)
                .placeholder(R.drawable.ic_resposta)
                .error(R.drawable.ic_resposta)
                .into(imageViewQuestao)
        }else{
            imageViewQuestao.visibility = View.VISIBLE
            imageViewQuestao.setImageResource(R.drawable.ic_resposta)
        }
        val resposta1 = respostas.getOrNull(0) ?: "Erro"
        val resposta2 = respostas.getOrNull(1) ?: "Erro"
        val resposta3 = respostas.getOrNull(2) ?: "Erro"
        val resposta4 = respostas.getOrNull(3) ?: "Erro"

        if (numRespostas == 2){
            buttonResposta1.textOn = resposta1
            buttonResposta1.textOff = resposta1
            buttonResposta1.text = resposta1
            buttonResposta1.visibility = View.VISIBLE
            buttonResposta2.textOn = resposta2
            buttonResposta2.textOff = resposta2
            buttonResposta2.text = resposta2
            buttonResposta2.visibility = View.VISIBLE
            buttonResposta3.visibility = View.GONE
            buttonResposta4.visibility = View.GONE
        }
        else if (numRespostas == 3){
            buttonResposta1.textOn = resposta1
            buttonResposta1.textOff = resposta1
            buttonResposta1.text = resposta1
            buttonResposta1.visibility = View.VISIBLE
            buttonResposta2.textOn = resposta2
            buttonResposta2.textOff = resposta2
            buttonResposta2.text = resposta2
            buttonResposta2.visibility = View.VISIBLE
            buttonResposta3.textOn = resposta3
            buttonResposta3.textOff = resposta3
            buttonResposta3.text = resposta3
            buttonResposta3.visibility = View.VISIBLE
            buttonResposta4.visibility = View.GONE

        }
        else if (numRespostas == 4){
            buttonResposta1.textOn = resposta1
            buttonResposta1.textOff = resposta1
            buttonResposta1.text = resposta1
            buttonResposta1.visibility = View.VISIBLE
            buttonResposta2.textOn = resposta2
            buttonResposta2.textOff = resposta2
            buttonResposta2.text = resposta2
            buttonResposta2.visibility = View.VISIBLE
            buttonResposta3.textOn = resposta3
            buttonResposta3.textOff = resposta3
            buttonResposta3.text = resposta3
            buttonResposta3.visibility = View.VISIBLE
            buttonResposta4.textOn = resposta4
            buttonResposta4.textOff = resposta4
            buttonResposta4.text = resposta4
            buttonResposta4.visibility = View.VISIBLE
        }
    }
    private fun pontuacaoFinal(){ //https://kotlinlang.org/docs/coroutines-overview.html#coroutine-context-and-behavior
        val titulo: String = ("Quiz Terminado")
        cronometroJob?.cancel()
        val builder = AlertDialog.Builder(this)
        builder.setTitle(titulo)
        builder.setMessage("Resultado: $pontuacao/${listaQuestoes.size} - $pontuacao acertos em ${listaQuestoes.size} questoes")
        builder.setCancelable(false)
        builder.setPositiveButton("Confirmar"){ _, _ ->
            finish()
        }
        val dialog = builder.create()
        dialog.show()
    }
    private fun iniciarTempo(){
        cronometroJob?.cancel()

        cronometroJob = lifecycleScope.launch {
            while (tempoRestante > 0){
                textViewTempo.text = "Tempo: $tempoRestante s"
                delay(1000)
                tempoRestante--
            }
            if(tempoRestante == 0){
                textViewTempo. text = "Tempo: 0s"
                pontuacaoFinal()
            }
        }
    }

}