package com.example.quizmanagementapp

import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.findViewTreeViewModelStoreOwner

class ResolverQuestao : AppCompatActivity() {



    private lateinit var buttonResposta1: Button
    private lateinit var buttonResposta2: Button
    private lateinit var buttonResposta3: Button
    private lateinit var buttonResposta4: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.resolver_questao)


        val textViewEnunciado: TextView = findViewById(R.id.tv_enunciado_pergunta)
        buttonResposta1 = findViewById(R.id.btn_resposta_1)
        buttonResposta2 = findViewById(R.id.btn_resposta_2)
        buttonResposta3 = findViewById(R.id.btn_resposta_3)
        buttonResposta4 = findViewById(R.id.btn_resposta_4)
        val buttonCancelar: Button = findViewById(R.id.btn_cancelar_resposta)
        var numeroRespostaCorreta = -1
        val idQuestao = intent.getIntExtra("id_questao", -1)
        val questaoAtual = GereQuestoes.encontraQuestao(idQuestao)

        val numeroRespostas: Int = questaoAtual!!.numRespostas


        var resposta1 : String = ""
        var resposta2 : String = ""
        var resposta3 : String = ""
        var resposta4 : String = ""
        when(numeroRespostas){

            2 -> {
                resposta1 = questaoAtual.respostas[0]
                resposta2 = questaoAtual.respostas[1]
            }
            3 -> {
                resposta1 = questaoAtual.respostas[0]
                resposta2 = questaoAtual.respostas[1]
                resposta3 = questaoAtual.respostas[2]
            }
            4 -> {
                resposta1 = questaoAtual.respostas[0]
                resposta2 = questaoAtual.respostas[1]
                resposta3 = questaoAtual.respostas[2]
                resposta4 = questaoAtual.respostas[3]
            }
        }
        textViewEnunciado.setText(questaoAtual.pergunta)
        atualizaEditTextRespostas(numeroRespostas, resposta1, resposta2, resposta3, resposta4)
        numeroRespostaCorreta = questaoAtual.respostaCorreta
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