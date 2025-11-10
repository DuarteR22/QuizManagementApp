package com.example.quizmanagementapp

import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class InserirQuestao : AppCompatActivity(){

    private lateinit var editTextPergunta: EditText
    private lateinit var editTextResposta1: EditText
    private lateinit var editTextResposta2: EditText
    private lateinit var editTextResposta3: EditText
    private lateinit var editTextResposta4: EditText

    private lateinit var radioButtonResposta1: RadioButton
    private lateinit var radioButtonResposta2: RadioButton
    private lateinit var radioButtonResposta3: RadioButton
    private lateinit var radioButtonResposta4: RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.inserir_questao)

        val rgNumeroRespostas: RadioGroup = findViewById(R.id.rg_numero_repostas)
        var numeroRespostasFinal: Int = -1

        val rgRespostaCorreta: RadioGroup = findViewById(R.id.rg_respostas_corretas)
        var numeroRespostaCorreta: Int = -1
        editTextPergunta= findViewById(R.id.et_pergunta)
        editTextResposta1 = findViewById(R.id.et_resposta_1)
        editTextResposta2 = findViewById(R.id.et_resposta_2)
        editTextResposta3 = findViewById(R.id.et_resposta_3)
        editTextResposta4 = findViewById(R.id.et_resposta_4)

        radioButtonResposta1 = findViewById(R.id.rb_resposta_1)
        radioButtonResposta2 = findViewById(R.id.rb_resposta_2)
        radioButtonResposta3 = findViewById(R.id.rb_resposta_3)
        radioButtonResposta4 = findViewById(R.id.rb_resposta_4)

        val btnGuardarPergunta: Button = findViewById(R.id.btn_guardar_questao)
        val btnCancelarPergunta: Button = findViewById(R.id.btn_cancelar_pergunta)
        rgNumeroRespostas.setOnCheckedChangeListener{group, checkedId ->
            val rgSelecionadoId: Int = rgNumeroRespostas.checkedRadioButtonId

            if(rgSelecionadoId == R.id.rb_2_respostas)
                numeroRespostasFinal = 2
            else if (rgSelecionadoId == R.id.rb_3_repostas)
                numeroRespostasFinal = 3
            else if (rgSelecionadoId == R.id.rb_4_respostas)
                numeroRespostasFinal = 4
            else
                numeroRespostasFinal = -1

            atualizaEditTextRespostas(numeroRespostasFinal)

        }
        val intentGuardar = Intent(this, MainActivity::class.java)

        rgRespostaCorreta.setOnCheckedChangeListener{group,checkedId ->
            val rgSelecionadoIg: Int = rgRespostaCorreta.checkedRadioButtonId

            if (rgSelecionadoIg == R.id.rb_resposta_1)
                numeroRespostaCorreta = 1
            else if(rgSelecionadoIg == R.id.rb_resposta_2)
                numeroRespostaCorreta = 2
            else if(rgSelecionadoIg == R.id.rb_resposta_3)
                numeroRespostaCorreta = 3
            else if(rgSelecionadoIg == R.id.rb_resposta_4)
                numeroRespostaCorreta = 4
            else
                numeroRespostaCorreta = -1
        }
        btnGuardarPergunta.setOnClickListener(){
            var editTextPerguntaFinal = editTextPergunta.text.toString().trim()
            var editTextResposta1Final = editTextResposta1.text.toString().trim()
            var editTextResposta2Final = editTextResposta2.text.toString().trim()
            var editTextResposta3Final = editTextResposta3.text.toString().trim()
            var editTextResposta4Final = editTextResposta4.text.toString().trim()
            if (numeroRespostasFinal == -1)
                Toast.makeText(this, "Por favor selecione um radiobutton que defina o número de perguntas!", Toast.LENGTH_SHORT).show()
            else if(numeroRespostaCorreta == -1)
                Toast.makeText(this, "Por favor selecione um radiobutton que defina a resposta correta!", Toast.LENGTH_SHORT).show()
            else{
                when(numeroRespostasFinal){

                    2 -> if (editTextResposta1Final.isEmpty() || editTextResposta2Final.isEmpty()||editTextPerguntaFinal.isEmpty() || numeroRespostaCorreta == -1){
                        if (editTextPerguntaFinal.isEmpty())
                            editTextPergunta.error = "todos os campos são obrigatórios"
                        if (editTextResposta1Final.isEmpty())
                            editTextResposta1.error = "todos os campos são obrigatórios"
                        if (editTextResposta2Final.isEmpty())
                            editTextResposta2.error = "todos os campos são obrigatórios"
                    }
                    else{
                        val respostasLista = listOf(editTextResposta1Final,editTextResposta2Final)
                        val questao = Questao(editTextPerguntaFinal, 2,respostasLista, numeroRespostaCorreta)
                        GereQuestoes.adicionarQuestao(questao)
                        Toast.makeText(this, "Questao guardada com sucesso!", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    3 -> if (editTextResposta1Final.isEmpty() || editTextResposta2Final.isEmpty()||editTextPerguntaFinal.isEmpty() || numeroRespostaCorreta == -1 || editTextResposta3Final.isEmpty()){
                        if (editTextPerguntaFinal.isEmpty())
                            editTextPergunta.error = "todos os campos são obrigatórios"
                        if (editTextResposta1Final.isEmpty())
                            editTextResposta1.error = "todos os campos são obrigatórios"
                        if (editTextResposta2Final.isEmpty())
                            editTextResposta2.error = "todos os campos são obrigatórios"
                        if (editTextResposta3Final.isEmpty())
                            editTextResposta3.error = "todos os campos são obrigatórios"
                    }
                    else{
                        val respostasLista = listOf(editTextResposta1Final,editTextResposta2Final, editTextResposta3Final, editTextResposta4Final)
                        val questao = Questao(editTextPerguntaFinal, 3,respostasLista, numeroRespostaCorreta)
                        GereQuestoes.adicionarQuestao(questao)
                        Toast.makeText(this, "Questao guardada com sucesso!", Toast.LENGTH_SHORT).show()

                        finish()

                    }
                    4 -> if (editTextResposta1Final.isEmpty() || editTextResposta2Final.isEmpty()||editTextPerguntaFinal.isEmpty() || numeroRespostaCorreta == -1 || editTextResposta3Final.isEmpty() || editTextResposta4Final.isEmpty()){
                        if (editTextPerguntaFinal.isEmpty())
                            editTextPergunta.error = "todos os campos são obrigatórios"
                        if (editTextResposta1Final.isEmpty())
                            editTextResposta1.error = "todos os campos são obrigatórios"
                        if (editTextResposta2Final.isEmpty())
                            editTextResposta2.error = "todos os campos são obrigatórios"
                        if (editTextResposta3Final.isEmpty())
                            editTextResposta3.error = "todos os campos são obrigatórios"
                        if(editTextResposta4Final.isEmpty())
                            editTextResposta4.error = "todos os campos são obrigatórios"
                    }
                    else{
                        val respostasLista = listOf(editTextResposta1Final,editTextResposta2Final, editTextResposta3Final, editTextResposta4Final)
                        val questao = Questao(editTextPerguntaFinal, 3,respostasLista, numeroRespostaCorreta)
                        GereQuestoes.adicionarQuestao(questao)
                        Toast.makeText(this, "Questao guardada com sucesso!", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            }
        }
        btnCancelarPergunta.setOnClickListener(){
            finish()
        }
    }

    fun atualizaEditTextRespostas(count: Int){

        if(count == 2){
            editTextResposta3.visibility = GONE
            editTextResposta4.visibility = GONE
            editTextResposta2.visibility = VISIBLE
            editTextResposta1.visibility = VISIBLE
            radioButtonResposta1.visibility = VISIBLE
            radioButtonResposta2.visibility = VISIBLE
            radioButtonResposta3.visibility = GONE
            radioButtonResposta4.visibility = GONE
        }
        else if (count == 4){
            editTextResposta1.visibility = VISIBLE
            editTextResposta2.visibility = VISIBLE
            editTextResposta3.visibility = VISIBLE
            editTextResposta4.visibility = VISIBLE
            radioButtonResposta1.visibility = VISIBLE
            radioButtonResposta2.visibility = VISIBLE
            radioButtonResposta3.visibility = VISIBLE
            radioButtonResposta4.visibility = VISIBLE
        }
        else if (count == 3){
            editTextResposta4.visibility = GONE
            editTextResposta3.visibility = VISIBLE
            editTextResposta2.visibility = VISIBLE
            editTextResposta1.visibility = VISIBLE
            radioButtonResposta1.visibility = VISIBLE
            radioButtonResposta2.visibility = VISIBLE
            radioButtonResposta3.visibility = VISIBLE
            radioButtonResposta4.visibility = GONE
        }
        else{
            editTextResposta4.visibility = VISIBLE
            editTextResposta3.visibility = VISIBLE
            editTextResposta1.visibility = VISIBLE
            editTextResposta2.visibility = VISIBLE
            radioButtonResposta1.visibility = VISIBLE
            radioButtonResposta2.visibility = VISIBLE
            radioButtonResposta3.visibility = VISIBLE
            radioButtonResposta4.visibility = VISIBLE
        }
    }
}