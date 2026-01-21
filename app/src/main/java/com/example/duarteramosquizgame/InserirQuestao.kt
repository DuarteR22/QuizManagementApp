package com.example.duarteramosquizgame

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
import retrofit2.Call
import retrofit2.Response

class InserirQuestao : AppCompatActivity(){

    private lateinit var editTextPergunta: EditText
    private lateinit var editTextResposta1: EditText
    private lateinit var editTextResposta2: EditText
    private lateinit var editTextResposta3: EditText
    private lateinit var editTextResposta4: EditText
    private lateinit var editTextUrl: EditText
    private lateinit var radioButtonResposta1: RadioButton
    private lateinit var radioButtonResposta2: RadioButton
    private lateinit var radioButtonResposta3: RadioButton
    private lateinit var radioButtonResposta4: RadioButton

    private  var quizId: Long = -1
    private var numeroRespostasFinal: Int = -1
    private var numeroRespostaCorreta: Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.inserir_questao)

        quizId = intent.getLongExtra("id_quiz", -1)
        if (quizId == -1L)
            finish()
        val rgNumeroRespostas: RadioGroup = findViewById(R.id.rg_numero_repostas)

        val rgRespostaCorreta: RadioGroup = findViewById(R.id.rg_respostas_corretas)
        editTextPergunta= findViewById(R.id.et_pergunta)
        editTextUrl = findViewById(R.id.et_url_imagem)
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
            var editTextUrlFinal = editTextUrl.text.toString().toString()
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
                        inserirQuestao(editTextPerguntaFinal, respostasLista,2,numeroRespostaCorreta, editTextUrlFinal)
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
                        val respostasLista = listOf(editTextResposta1Final,editTextResposta2Final, editTextResposta3Final)
                        inserirQuestao(editTextPerguntaFinal, respostasLista, 3, numeroRespostaCorreta,editTextUrlFinal)
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
                        inserirQuestao(editTextPerguntaFinal, respostasLista, 4, numeroRespostaCorreta,editTextUrlFinal)

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
    private fun inserirQuestao(pergunta: String, respostas: List<String>, numRespostas: Int, respostaCorreta: Int , urlImagem: String){

        val questao = Questao(pergunta = pergunta, respostas = respostas, numRespostas = numRespostas, respostaCorreta = respostaCorreta, urlImagem = if (urlImagem.isEmpty())null else urlImagem, quizId = quizId)
        ClienteRetrofit.instance.inserirQuestao(questao).enqueue(object : retrofit2.Callback<RegistoQuestaoResposta>{
            override fun onResponse(
                call: Call<RegistoQuestaoResposta>,
                response: Response<RegistoQuestaoResposta>
            ) {
                if(response.isSuccessful){
                    val quidInserido = response.body()?.quid
                    Toast.makeText(this@InserirQuestao, "Questão $quidInserido inserida com sucesso!", Toast.LENGTH_SHORT).show()
                    finish()
                }else
                    Toast.makeText(this@InserirQuestao, "Erro ao guardar no servidor remoto", Toast.LENGTH_SHORT).show()
            }
            override fun onFailure(call: Call<RegistoQuestaoResposta>, t: Throwable) {
                Toast.makeText(this@InserirQuestao, "Falha de rede: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

}