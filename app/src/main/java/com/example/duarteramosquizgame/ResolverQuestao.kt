package com.example.duarteramosquizgame

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ResolverQuestao : AppCompatActivity() {


    private lateinit var questaoSQL: QuestaoSQL
    private lateinit var buttonResposta1: ToggleButton
    private lateinit var buttonResposta2: ToggleButton
    private lateinit var buttonResposta3: ToggleButton
    private lateinit var buttonResposta4: ToggleButton
    private lateinit var textViewEnunciado: TextView
    private lateinit var imagemQuestao: ImageView
    private var idQuestao: Long = -1
    private var numeroRespostaCorreta = -1
    private var urlImagem: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.resolver_questao)


        textViewEnunciado = findViewById(R.id.tv_titulo_pergunta)
        buttonResposta1 = findViewById(R.id.btn_resposta_1)
        buttonResposta2 = findViewById(R.id.btn_resposta_2)
        buttonResposta3 = findViewById(R.id.btn_resposta_3)
        buttonResposta4 = findViewById(R.id.btn_resposta_4)
        imagemQuestao = findViewById(R.id.iv_imagem_questao)
        val buttonCancelar: Button = findViewById(R.id.btn_cancelar)
        questaoSQL = QuestaoSQL(this)
        idQuestao = intent.getLongExtra("id_questao", -1)
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
                    finish()
                }
                buttonResposta3.setOnClickListener {
                    Toast.makeText(this, "Resposta Errada!", Toast.LENGTH_SHORT).show()
                    finish()
                }
                buttonResposta4.setOnClickListener {
                    Toast.makeText(this, "Resposta Errada!", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
            2 -> {
                buttonResposta1.setOnClickListener{
                    Toast.makeText(this, "Resposta Errada!", Toast.LENGTH_SHORT).show()
                    finish()
                }
                buttonResposta2.setOnClickListener{
                    Toast.makeText(this, "Resposta Correta!", Toast.LENGTH_SHORT).show()
                    finish()                }
                buttonResposta3.setOnClickListener {
                    Toast.makeText(this, "Resposta Errada!", Toast.LENGTH_SHORT).show()
                    finish()
                }
                buttonResposta4.setOnClickListener {
                    Toast.makeText(this, "Resposta Errada!", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
            3 -> {
                buttonResposta1.setOnClickListener{
                    Toast.makeText(this, "Resposta Errada!", Toast.LENGTH_SHORT).show()
                    finish()
                }
                buttonResposta2.setOnClickListener {
                    Toast.makeText(this, "Resposta Errada!", Toast.LENGTH_SHORT).show()
                    finish()
                }
                buttonResposta3.setOnClickListener{
                    Toast.makeText(this, "Resposta Correta!", Toast.LENGTH_SHORT).show()
                    finish()                }
                buttonResposta4.setOnClickListener {
                    Toast.makeText(this, "Resposta Errada!", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
            4 -> {
                buttonResposta1.setOnClickListener{
                    Toast.makeText(this, "Resposta Errada!", Toast.LENGTH_SHORT).show()
                    finish()
                }
                buttonResposta2.setOnClickListener {
                    Toast.makeText(this, "Resposta Errada!", Toast.LENGTH_SHORT).show()
                    finish()
                }
                buttonResposta3.setOnClickListener {
                    Toast.makeText(this, "Resposta Errada!", Toast.LENGTH_SHORT).show()
                    finish()
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
        urlImagem = questao.urlImagem
        if (!urlImagem.isNullOrEmpty()) {
            imagemQuestao.scaleType = ImageView.ScaleType.CENTER_CROP
            Glide.with(this)
                .load(urlImagem)
                .placeholder(R.drawable.ic_resposta)
                .error(R.drawable.ic_resposta)
                .into(imagemQuestao)
        }
        else{
            imagemQuestao.scaleType = ImageView.ScaleType.CENTER_INSIDE
            imagemQuestao.setImageResource(R.drawable.ic_resposta)
        }

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
            buttonResposta1.setTextOn(resposta1)
            buttonResposta2.setText(resposta2)
            buttonResposta2.setTextOn(resposta2)
        }
        else if (count == 3){
            buttonResposta1.visibility = VISIBLE
            buttonResposta2.visibility = VISIBLE
            buttonResposta3.visibility = VISIBLE
            buttonResposta4.visibility = GONE
            buttonResposta1.setText(resposta1)
            buttonResposta1.setTextOn(resposta1)
            buttonResposta2.setText(resposta2)
            buttonResposta2.setTextOn(resposta2)
            buttonResposta3.setText(resposta3)
            buttonResposta3.setTextOn(resposta3)

        }
        else if (count == 4){
            buttonResposta1.visibility = VISIBLE
            buttonResposta2.visibility = VISIBLE
            buttonResposta3.visibility = VISIBLE
            buttonResposta4.visibility = VISIBLE
            buttonResposta1.setText(resposta1)
            buttonResposta1.setTextOn(resposta1)
            buttonResposta2.setText(resposta2)
            buttonResposta2.setTextOn(resposta2)
            buttonResposta3.setText(resposta3)
            buttonResposta3.setTextOn(resposta3)
            buttonResposta4.setText(resposta4)
            buttonResposta4.setTextOn(resposta4)

        }
    }

}