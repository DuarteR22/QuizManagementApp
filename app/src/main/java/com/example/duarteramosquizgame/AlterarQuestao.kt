package com.example.duarteramosquizgame

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AlterarQuestao: AppCompatActivity() {

    private var questaoId: Long = -1
    private var quizId: Long = -1
    private lateinit var editTextTituloQuestao: EditText
    private lateinit var editTextUrlImagem : EditText
    private lateinit var editTextResposta1: EditText
    private lateinit var editTextResposta2: EditText
    private lateinit var editTextResposta3: EditText
    private lateinit var editTextResposta4: EditText

    private lateinit var textViewTituloQuestao: TextView
    private lateinit var textViewUrlImagem: TextView
    private lateinit var textViewNumRespostas: TextView
    private lateinit var textViewRespostaCorreta: TextView
    private lateinit var textViewResposta1: TextView
    private lateinit var textViewResposta2: TextView
    private lateinit var textViewResposta3: TextView
    private lateinit var textViewResposta4: TextView

    private lateinit var textViewLabelResposta1: TextView
    private lateinit var textViewLabelResposta2: TextView
    private lateinit var textViewLabelResposta3: TextView
    private lateinit var textViewLabelResposta4: TextView

    private lateinit var rgNumeroRespostas: RadioGroup
    private lateinit var rgRespostaCorreta: RadioGroup
    private lateinit var radioButtonResposta1: RadioButton
    private lateinit var radioButtonResposta2: RadioButton
    private lateinit var radioButtonResposta3: RadioButton
    private lateinit var radioButtonResposta4: RadioButton


    private var tituloOriginal: String = ""
    private var urlOriginal: String = ""
    private var respostasOriginais: List<String> = emptyList()
    private var corretaOriginal: Int = -1

    private var numeroRespostasFinal: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.editar_questao)

        questaoId = intent.getLongExtra("id_questao", -1)

        editTextTituloQuestao = findViewById(R.id.et_pergunta_nova)
        editTextUrlImagem = findViewById(R.id.et_url_nova)

        editTextResposta1 = findViewById(R.id.et_resposta_1)
        editTextResposta2 = findViewById(R.id.et_resposta_2)
        editTextResposta3 = findViewById(R.id.et_resposta_3)
        editTextResposta4 = findViewById(R.id.et_resposta_4)


        textViewTituloQuestao = findViewById(R.id.tv_pergunta_antiga)
        textViewUrlImagem = findViewById(R.id.tv_url_antiga)
        textViewNumRespostas = findViewById(R.id.tv_num_respostas_antigo)
        textViewRespostaCorreta = findViewById(R.id.tv_resposta_correta_antiga)

        textViewResposta1 = findViewById(R.id.tv_resp1_antiga)
        textViewResposta2 = findViewById(R.id.tv_resp2_antiga)
        textViewResposta3 = findViewById(R.id.tv_resp3_antiga)
        textViewResposta4 = findViewById(R.id.tv_resp4_antiga)

        textViewLabelResposta1 = findViewById(R.id.tv_resp1_antiga_label)
        textViewLabelResposta2 = findViewById(R.id.tv_resp2_antiga_label)
        textViewLabelResposta3 = findViewById(R.id.tv_resp3_antiga_label)
        textViewLabelResposta4 = findViewById(R.id.tv_resp4_antiga_label)


        radioButtonResposta1 = findViewById(R.id.rb_correta_1)
        radioButtonResposta2 = findViewById(R.id.rb_correta_2)
        radioButtonResposta3 = findViewById(R.id.rb_correta_3)
        radioButtonResposta4 = findViewById(R.id.rb_correta_4)

        val btnAlterarQuestao: Button = findViewById(R.id.btn_alterar_questao)
        val btnCancelarQuestao: Button = findViewById(R.id.btn_cancelar_alteracoes_pergunta)


        rgNumeroRespostas = findViewById(R.id.rg_numero_respostas)
        rgRespostaCorreta= findViewById(R.id.rg_respostas_corretas)

        rgNumeroRespostas.setOnCheckedChangeListener{group, checkedId ->
            val rgSelecionadoId: Int = rgNumeroRespostas.checkedRadioButtonId

            if(rgSelecionadoId == R.id.rb_2_respostas)
                numeroRespostasFinal = 2
            else if (rgSelecionadoId == R.id.rb_3_respostas)
                numeroRespostasFinal = 3
            else if (rgSelecionadoId == R.id.rb_4_respostas)
                numeroRespostasFinal = 4
            else
                numeroRespostasFinal = -1

            atualizaEditTextRespostas(numeroRespostasFinal)

        }
        carregarQuestoes()
        btnAlterarQuestao.setOnClickListener{
            guardarAlteracoes()
        }
        btnCancelarQuestao.setOnClickListener{
            finish()
        }
    }
    //Carrega a página com o número de respostas que possui a questão a alterar
    @SuppressLint("SetTextI18n")
    private fun carregarQuestoes() {
        val request = ListarQuestaoIdRequest(quid = questaoId)
        ClienteRetrofit.instance.listarQuestaoId(request).enqueue(object : Callback<Questao> {
            override fun onResponse(call: Call<Questao>, response: Response<Questao>) {
                if (response.isSuccessful) {
                    val questao = response.body()
                    if (questao != null) {
                        tituloOriginal = questao.pergunta
                        urlOriginal = questao.urlImagem ?: ""
                        quizId = questao.quizId
                        respostasOriginais = questao.respostas
                        corretaOriginal = questao.respostaCorreta
                        textViewTituloQuestao.text = questao.pergunta
                        textViewUrlImagem.text = questao.urlImagem ?: "Não foi escolhida uma imagem"
                        textViewNumRespostas.text = "${questao.numRespostas} Respostas"
                        textViewRespostaCorreta.text = "Resposta ${questao.respostaCorreta}"
                        editTextTituloQuestao.setText(questao.pergunta)
                        editTextUrlImagem.setText(questao.urlImagem)
                        val respostas = questao.respostas
                        textViewResposta1.text = respostas.getOrNull(0) ?: ""
                        editTextResposta1.setText(respostas.getOrNull(0) ?: "")
                        textViewLabelResposta1.visibility = VISIBLE
                        editTextResposta1.visibility = VISIBLE
                        if (questao.numRespostas >= 2) {
                            textViewResposta2.text = respostas.getOrNull(1) ?: ""
                            editTextResposta2.setText(respostas.getOrNull(1) ?: "")
                            textViewResposta2.visibility = VISIBLE
                            textViewLabelResposta2.visibility = VISIBLE
                            editTextResposta2.visibility = VISIBLE

                        } else {
                            textViewResposta2.visibility = GONE
                            textViewLabelResposta2.visibility = GONE
                            editTextResposta2.visibility = GONE
                        }
                        if (questao.numRespostas >= 3) {
                            textViewResposta3.text = respostas.getOrNull(2) ?: ""
                            editTextResposta3.setText(respostas.getOrNull(2) ?: "")
                            textViewResposta3.visibility = VISIBLE
                            textViewLabelResposta3.visibility = VISIBLE
                            editTextResposta3.visibility = VISIBLE
                        } else {
                            textViewResposta3.visibility = GONE
                            textViewLabelResposta3.visibility = GONE
                            editTextResposta3.visibility = GONE
                        }
                        if (questao.numRespostas >= 4) {
                            textViewResposta4.text = respostas.getOrNull(3) ?: ""
                            editTextResposta4.setText(respostas.getOrNull(3) ?: "")
                            textViewResposta4.visibility = VISIBLE
                            textViewLabelResposta4.visibility = VISIBLE
                            editTextResposta4.visibility = VISIBLE
                        } else {
                            textViewResposta4.visibility = GONE
                            textViewLabelResposta4.visibility = GONE
                            editTextResposta4.visibility = GONE
                        }
                        when (questao.numRespostas) {
                            2 -> rgNumeroRespostas.check(R.id.rb_2_respostas)
                            3 -> rgNumeroRespostas.check(R.id.rb_3_respostas)
                            4 -> rgNumeroRespostas.check(R.id.rb_4_respostas)
                        }
                        when (questao.respostaCorreta) {
                            1 -> rgRespostaCorreta.check(R.id.rb_correta_1)
                            2 -> rgRespostaCorreta.check(R.id.rb_correta_2)
                            3 -> rgRespostaCorreta.check(R.id.rb_correta_3)
                            4 -> rgRespostaCorreta.check(R.id.rb_correta_4)
                        }
                    }
                } else {
                    Toast.makeText(
                        this@AlterarQuestao,
                        "Erro ao carregar dados",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }

            override fun onFailure(call: Call<Questao>, t: Throwable) {
                Toast.makeText(
                    this@AlterarQuestao,
                    "Falha de rede: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
        //Altera o número de respostas em função da atualização do número de respostas
        fun atualizaEditTextRespostas(count: Int) {


            if (count == 2) {
                editTextResposta1.visibility = VISIBLE
                editTextResposta2.visibility = VISIBLE
                editTextResposta3.visibility = GONE
                editTextResposta4.visibility = GONE
                textViewResposta1.visibility = VISIBLE
                textViewResposta2.visibility = VISIBLE
                textViewResposta3.visibility = GONE
                textViewResposta4.visibility = GONE
                textViewLabelResposta1.visibility = VISIBLE
                textViewLabelResposta2.visibility = VISIBLE
                textViewLabelResposta3.visibility = GONE
                textViewLabelResposta4.visibility = GONE

            } else if (count == 3) {
                editTextResposta1.visibility = VISIBLE
                editTextResposta2.visibility = VISIBLE
                editTextResposta3.visibility = VISIBLE
                editTextResposta4.visibility = GONE
                textViewResposta1.visibility = VISIBLE
                textViewResposta2.visibility = VISIBLE
                textViewResposta3.visibility = VISIBLE
                textViewResposta4.visibility = GONE
                textViewLabelResposta1.visibility = VISIBLE
                textViewLabelResposta2.visibility = VISIBLE
                textViewLabelResposta3.visibility = VISIBLE
                textViewLabelResposta4.visibility = GONE
            } else if (count == 4) {
                editTextResposta1.visibility = VISIBLE
                editTextResposta2.visibility = VISIBLE
                editTextResposta3.visibility = VISIBLE
                editTextResposta4.visibility = VISIBLE
                textViewResposta1.visibility = VISIBLE
                textViewResposta2.visibility = VISIBLE
                textViewResposta3.visibility = VISIBLE
                textViewResposta4.visibility = VISIBLE
                textViewLabelResposta1.visibility = VISIBLE
                textViewLabelResposta2.visibility = VISIBLE
                textViewLabelResposta3.visibility = VISIBLE
                textViewLabelResposta4.visibility = VISIBLE
            }
        }

        fun guardarAlteracoes() {
            var novoTituloQuestao = editTextTituloQuestao.text.toString().trim()
            var novoUrl = editTextUrlImagem.text.toString().trim()
            val numRespostasNovo = when (rgNumeroRespostas.checkedRadioButtonId) {
                R.id.rb_2_respostas -> 2
                R.id.rb_3_respostas -> 3
                R.id.rb_4_respostas -> 4
                else -> -1
            }
            val novaRespostaCorreta = when (rgRespostaCorreta.checkedRadioButtonId) {
                R.id.rb_correta_1 -> 1
                R.id.rb_correta_2 -> 2
                R.id.rb_correta_3 -> 3
                R.id.rb_correta_4 -> 4
                else -> -1
            }

            val respostasEditTexts =
                listOf(editTextResposta1, editTextResposta2, editTextResposta3, editTextResposta4)
            val novasRespostas = mutableListOf<String>()

            for (i in 0 until numRespostasNovo) {
                var resposta = respostasEditTexts[i].text.toString().trim()
                if (resposta.isEmpty()) {
                    resposta = respostasOriginais.getOrNull(i) ?: ""
                }
                if (resposta.isEmpty()) {
                    Toast.makeText(
                        this,
                        "A Resposta ${i + 1} não pode estar vazia",
                        Toast.LENGTH_LONG
                    ).show()
                    return
                }
                novasRespostas.add(resposta)
            }
            if (novoTituloQuestao.isEmpty()) {
                novoTituloQuestao = tituloOriginal
            }
            if (novoUrl.isEmpty()) {
                novoUrl = urlOriginal
            }
            if (novaRespostaCorreta == -1 || novaRespostaCorreta > numRespostasNovo) {
                Toast.makeText(
                    this,
                    "Selecione a resposta correta e verifique o número de respostas.",
                    Toast.LENGTH_SHORT
                ).show()
                return
            }
            val questaoAtualizada = Questao(
                id = questaoId,
                quizId = quizId,
                pergunta = novoTituloQuestao,
                numRespostas = numRespostasNovo,
                respostas = novasRespostas,
                respostaCorreta = novaRespostaCorreta,
                urlImagem = novoUrl.ifEmpty { null }
            )

            ClienteRetrofit.instance.alterarQuestao(questaoAtualizada).enqueue(object : Callback<RegistoResposta>{
                override fun onResponse(
                    call: Call<RegistoResposta>,
                    response: Response<RegistoResposta>
                ) {
                    if (response.isSuccessful){
                        Toast.makeText(this@AlterarQuestao, "Questão alterada com sucesso!", Toast.LENGTH_SHORT).show()
                        finish()
                    }else
                        Toast.makeText(this@AlterarQuestao, "Erro ao alterar a questão no servidor.", Toast.LENGTH_SHORT).show()
                }
                override fun onFailure(call: Call<RegistoResposta>, t: Throwable) {
                    Toast.makeText(this@AlterarQuestao, "Falha de rede: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })

        }
}