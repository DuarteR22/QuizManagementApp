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

    private lateinit var textViewResposta1: TextView
    private lateinit var textViewResposta2: TextView
    private lateinit var textViewResposta3: TextView
    private lateinit var textViewResposta4: TextView

    private lateinit var radioButtonResposta1: RadioButton
    private lateinit var radioButtonResposta2: RadioButton
    private lateinit var radioButtonResposta3: RadioButton
    private lateinit var radioButtonResposta4: RadioButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.resolver_questao)


        val textViewEnunciado: TextView = findViewById(R.id.tv_enunciado_pergunta)
        textViewResposta1 = findViewById(R.id.tv_resposta_correta_1)
        textViewResposta2 = findViewById(R.id.tv_resposta_correta_2)
        textViewResposta3 = findViewById(R.id.tv_resposta_correta_3)
        textViewResposta4 = findViewById(R.id.tv_resposta_correta_4)

        radioButtonResposta1 = findViewById(R.id.rb_resposta_1)
        radioButtonResposta2 = findViewById(R.id.rb_resposta_2)
        radioButtonResposta3 = findViewById(R.id.rb_resposta_3)
        radioButtonResposta4 = findViewById(R.id.rb_resposta_4)


        val btnResponder: Button = findViewById(R.id.btn_responder)
        val btnCancelar: Button = findViewById(R.id.btn_cancelar)

        val rgRespotaCorreta: RadioGroup = findViewById(R.id.rg_respostas_corretas)
        var numeroRespostaCorreta = -1
        val idQuestao = intent.getIntExtra("id_questao", -1)
        val questaoAtual = GereQuestoes.encontraQuestao(idQuestao)

        val numeroRespostas: Int = questaoAtual!!.numRespostas

        val intentResposta = Intent(this, MainActivity::class.java)

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

        rgRespotaCorreta.setOnCheckedChangeListener{group, checkedId ->
            val respostaSelecionadaId: Int = rgRespotaCorreta.checkedRadioButtonId

            if(respostaSelecionadaId == R.id.rb_resposta_1)
                numeroRespostaCorreta = 1
            else if(respostaSelecionadaId == R.id.rb_resposta_2)
                numeroRespostaCorreta = 2
            else if(respostaSelecionadaId == R.id.rb_resposta_3)
                numeroRespostaCorreta = 3
            else if(respostaSelecionadaId == R.id.rb_resposta_4)
                numeroRespostaCorreta = 4
        }



        btnResponder.setOnClickListener{
            if(numeroRespostaCorreta == questaoAtual.respostaCorreta){

                Toast.makeText(this, "Resposta Correta!", Toast.LENGTH_SHORT).show()
                startActivity(intentResposta)
            }
        }
        btnCancelar.setOnClickListener{
            startActivity(intentResposta)
        }

    }
    fun atualizaEditTextRespostas(count: Int, resposta1: String, resposta2: String, resposta3: String, resposta4: String){

        if(count == 2){
            textViewResposta1.visibility = VISIBLE
            textViewResposta2.visibility = VISIBLE
            textViewResposta1.setText(resposta1)
            textViewResposta2.setText(resposta2)
            textViewResposta3.visibility = GONE
            textViewResposta4.visibility = GONE
            radioButtonResposta1.visibility = VISIBLE
            radioButtonResposta2.visibility = VISIBLE
            radioButtonResposta3.visibility = GONE
            radioButtonResposta4.visibility = GONE
        }
        else if (count == 3){
            textViewResposta1.visibility = VISIBLE
            textViewResposta2.visibility = VISIBLE
            textViewResposta3.visibility = VISIBLE
            textViewResposta1.setText(resposta1)
            textViewResposta2.setText(resposta2)
            textViewResposta3.setText(resposta3)
            textViewResposta4.visibility = GONE
            radioButtonResposta1.visibility = VISIBLE
            radioButtonResposta2.visibility = VISIBLE
            radioButtonResposta3.visibility = VISIBLE
            radioButtonResposta4.visibility = GONE
        }
        else if (count == 4){
            textViewResposta1.visibility = VISIBLE
            textViewResposta2.visibility = VISIBLE
            textViewResposta3.visibility = VISIBLE
            textViewResposta4.visibility = VISIBLE
            textViewResposta1.setText(resposta1)
            textViewResposta2.setText(resposta2)
            textViewResposta3.setText(resposta3)
            textViewResposta4.setText(resposta4)
            radioButtonResposta1.visibility = VISIBLE
            radioButtonResposta2.visibility = VISIBLE
            radioButtonResposta3.visibility = VISIBLE
            radioButtonResposta4.visibility = VISIBLE
        }
    }
}