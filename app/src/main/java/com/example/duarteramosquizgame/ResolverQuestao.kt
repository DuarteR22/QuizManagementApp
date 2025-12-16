package com.example.duarteramosquizgame

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ResolverQuestao : AppCompatActivity() {


    private lateinit var questaoSQL: QuestaoSQL
    private lateinit var buttonResposta1: Button
    private lateinit var buttonResposta2: Button
    private lateinit var buttonResposta3: Button
    private lateinit var buttonResposta4: Button
    private lateinit var textViewEnunciado: TextView
    private var idQuestao: Long = -1
    private var numeroRespostaCorreta = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.resolver_questao)


        textViewEnunciado = findViewById(R.id.tv_enunciado_pergunta)
        buttonResposta1 = findViewById(R.id.btn_resposta_1)
        buttonResposta2 = findViewById(R.id.btn_resposta_2)
        buttonResposta3 = findViewById(R.id.btn_resposta_3)
        buttonResposta4 = findViewById(R.id.btn_resposta_4)
        val buttonCancelar: Button = findViewById(R.id.btn_cancelar_resposta)
        idQuestao = intent.getLongExtra("id_questao", -1)
        questaoSQL = QuestaoSQL(this)
        if (idQuestao != -1L)
            carregarQuestao()
        else{
            finish()
            return
        }
        when(numeroRespostaCorreta){

            1 -> {
                buttonResposta1.setOnClickListener{
                    Toast.makeText(this, "Resposta Correta!", Toast.LENGTH_SHORT).show()
                    finish()
                }
                buttonResposta2.setOnClickListener {
                    Toast.makeText(this, "Resposta Errada!", Toast.LENGTH_SHORT).show()
                }
                buttonResposta3.setOnClickListener {
                    Toast.makeText(this, "Resposta Errada!", Toast.LENGTH_SHORT).show()
                }
                buttonResposta4.setOnClickListener {
                    Toast.makeText(this, "Resposta Errada!", Toast.LENGTH_SHORT).show()
                }
            }
            2 -> {
                buttonResposta1.setOnClickListener{
                    Toast.makeText(this, "Resposta Errada!", Toast.LENGTH_SHORT).show()
                }
                buttonResposta2.setOnClickListener{
                    Toast.makeText(this, "Resposta Correta!", Toast.LENGTH_SHORT).show()
                    finish()                }
                buttonResposta3.setOnClickListener {
                    Toast.makeText(this, "Resposta Errada!", Toast.LENGTH_SHORT).show()
                }
                buttonResposta4.setOnClickListener {
                    Toast.makeText(this, "Resposta Errada!", Toast.LENGTH_SHORT).show()
                }
            }
            3 -> {
                buttonResposta1.setOnClickListener{
                    Toast.makeText(this, "Resposta Errada!", Toast.LENGTH_SHORT).show()
                }
                buttonResposta2.setOnClickListener {
                    Toast.makeText(this, "Resposta Errada!", Toast.LENGTH_SHORT).show()
                }
                buttonResposta3.setOnClickListener{
                    Toast.makeText(this, "Resposta Correta!", Toast.LENGTH_SHORT).show()
                    finish()                }
                buttonResposta4.setOnClickListener {
                    Toast.makeText(this, "Resposta Errada!", Toast.LENGTH_SHORT).show()
                }
            }
            4 -> {
                buttonResposta1.setOnClickListener{
                    Toast.makeText(this, "Resposta Errada!", Toast.LENGTH_SHORT).show()
                }
                buttonResposta2.setOnClickListener {
                    Toast.makeText(this, "Resposta Errada!", Toast.LENGTH_SHORT).show()
                }
                buttonResposta3.setOnClickListener {
                    Toast.makeText(this, "Resposta Errada!", Toast.LENGTH_SHORT).show()
                }
                buttonResposta4.setOnClickListener{
                    Toast.makeText(this, "Resposta Correta!", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
        buttonCancelar.setOnClickListener {
            finish()
        }

    }

    private fun carregarQuestao(){
        val questao = questaoSQL.obterQuestaoId(idQuestao)
        if (questao == null) {
            finish()
            return
        }
        val numeroRespostas: Int = questao.numRespostas
        val respostas = questao.respostas
        numeroRespostaCorreta = questao.respostaCorreta

        textViewEnunciado.text = questao.pergunta

        val resposta1 = respostas.getOrNull(0) ?: ""
        val resposta2 = respostas.getOrNull(1)?: ""
        val resposta3 = respostas.getOrNull(2)?: ""
        val resposta4 = respostas.getOrNull(3)?: ""
        atualizaEditTextRespostas(numeroRespostas,resposta1,resposta2,resposta3,resposta4)

    }
    fun atualizaEditTextRespostas(count: Int, resposta1: String, resposta2: String, resposta3: String, resposta4: String){

        if(count == 2){
            buttonResposta1.visibility = VISIBLE
            buttonResposta2.visibility = VISIBLE
            buttonResposta3.visibility = GONE
            buttonResposta4.visibility = GONE
            buttonResposta1.setText(resposta1)
            buttonResposta2.setText(resposta2)
        }
        else if (count == 3){
            buttonResposta1.visibility = VISIBLE
            buttonResposta2.visibility = VISIBLE
            buttonResposta3.visibility = VISIBLE
            buttonResposta4.visibility = GONE
            buttonResposta1.setText(resposta1)
            buttonResposta2.setText(resposta2)
            buttonResposta3.setText(resposta3)
        }
        else if (count == 4){
            buttonResposta1.visibility = VISIBLE
            buttonResposta2.visibility = VISIBLE
            buttonResposta3.visibility = VISIBLE
            buttonResposta4.visibility = VISIBLE
            buttonResposta1.setText(resposta1)
            buttonResposta2.setText(resposta2)
            buttonResposta3.setText(resposta3)
            buttonResposta4.setText(resposta4)
        }
    }
}