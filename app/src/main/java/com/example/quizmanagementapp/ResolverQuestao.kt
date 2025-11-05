package com.example.quizmanagementapp

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
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

        val rgRespotaCorreta: RadioGroup = findViewById(R.id.rg_respostas_corretas)
        var numeroRespostaCorreta = -1
        val idQuestao = intent.getIntExtra("id_questao", -1)
        val questaoAtual = GereQuestoes.encontraQuestao(idQuestao)

        val numeroRespostas: Int = questaoAtual!!.numRespostas

        atualizaEditTextRespostas(numeroRespostas)
        
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

    }
    fun atualizaEditTextRespostas(count: Int){

        if(count == 2){
            textViewResposta1.visibility = VISIBLE
            textViewResposta2.visibility = VISIBLE
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
            radioButtonResposta1.visibility = VISIBLE
            radioButtonResposta2.visibility = VISIBLE
            radioButtonResposta3.visibility = VISIBLE
            radioButtonResposta4.visibility = VISIBLE
        }
    }
}