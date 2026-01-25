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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResolverQuestao : AppCompatActivity() {


    private lateinit var buttonResposta1: ToggleButton
    private lateinit var buttonResposta2: ToggleButton
    private lateinit var buttonResposta3: ToggleButton
    private lateinit var buttonResposta4: ToggleButton
    private lateinit var textViewEnunciado: TextView
    private lateinit var imagemQuestao: ImageView
    private var idQuestao: Long = -1
    private var numeroRespostaCorreta = -1
    private var urlImagem: String? = ""
    private var resposta1: String = ""
    private var resposta2: String = ""
    private var resposta3: String = ""
    private var resposta4: String = ""
    private var enunciadoPergunta: String = ""

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
        if (savedInstanceState != null){
            idQuestao = savedInstanceState.getLong("id_questao")
            numeroRespostaCorreta = savedInstanceState.getInt("correta")
            urlImagem = savedInstanceState.getString("url")
            textViewEnunciado.text = savedInstanceState.getString("pergunta")
            val r1 = savedInstanceState.getString("r1") ?: ""
            val r2 = savedInstanceState.getString("r2") ?: ""
            val r3 = savedInstanceState.getString("r3") ?: ""
            val r4 = savedInstanceState.getString("r4") ?: ""
            var count= -1
            if (r4.isNotEmpty())
                count = 4
            else if(r3.isNotEmpty())
                count = 3
            else
                count = 2
            atualizaEditTextRespostas(count,r1,r2,r3,r4)
        }else{
            idQuestao = intent.getLongExtra("id_questao", -1)
        }
        if (idQuestao != -1L)
            carregarQuestao()
        else{
            finish()
            return
        }
        buttonCancelar.setOnClickListener {
            finish()
        }
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong("id_questao", idQuestao)
        outState.putString("pergunta", textViewEnunciado.text.toString())
        outState.putString("r1", buttonResposta1.text.toString())
        outState.putString("r2", buttonResposta2.text.toString())
        outState.putString("r3", buttonResposta3.text.toString())
        outState.putString("r4", buttonResposta4.text.toString())
        outState.putInt("correta", numeroRespostaCorreta)
        outState.putString("url", urlImagem)
    }
    private fun carregarQuestao(){

        val request = ListarQuestaoIdRequest(quid = idQuestao)
        ClienteRetrofit.instance.listarQuestaoId(request).enqueue(object : Callback<Questao>{
            override fun onResponse(call: Call<Questao>, response: Response<Questao>) {
                if(response.isSuccessful){
                    val questao = response.body()
                    if (questao != null){
                        val numeroRespostas: Int = questao.numRespostas
                        val respostas = questao.respostas
                        numeroRespostaCorreta = questao.respostaCorreta
                        textViewEnunciado.text = questao.pergunta
                        urlImagem = questao.urlImagem
                        if (!urlImagem.isNullOrEmpty()) {
                            imagemQuestao.scaleType = ImageView.ScaleType.CENTER_CROP
                            Glide.with(this@ResolverQuestao)
                                .load(urlImagem)
                                .placeholder(R.drawable.ic_resposta)
                                .error(R.drawable.ic_resposta)
                                .into(imagemQuestao)
                        }
                        else{
                            imagemQuestao.visibility = GONE
                        }
                        val resposta1 = respostas.getOrNull(0) ?: ""
                        val resposta2 = respostas.getOrNull(1)?: ""
                        val resposta3 = respostas.getOrNull(2)?: ""
                        val resposta4 = respostas.getOrNull(3)?: ""
                        atualizaEditTextRespostas(numeroRespostas,resposta1,resposta2,resposta3,resposta4)
                        when(numeroRespostaCorreta){
                            1 -> {
                                buttonResposta1.setOnClickListener{
                                    Toast.makeText(this@ResolverQuestao, "Resposta Correta!", Toast.LENGTH_SHORT).show()
                                    finish()
                                }
                                buttonResposta2.setOnClickListener {
                                    Toast.makeText(this@ResolverQuestao, "Resposta Errada!", Toast.LENGTH_SHORT).show()
                                    finish()
                                }
                                buttonResposta3.setOnClickListener {
                                    Toast.makeText(this@ResolverQuestao, "Resposta Errada!", Toast.LENGTH_SHORT).show()
                                    finish()
                                }
                                buttonResposta4.setOnClickListener {
                                    Toast.makeText(this@ResolverQuestao, "Resposta Errada!", Toast.LENGTH_SHORT).show()
                                    finish()
                                }
                            }
                            2 -> {
                                buttonResposta1.setOnClickListener{
                                    Toast.makeText(this@ResolverQuestao, "Resposta Errada!", Toast.LENGTH_SHORT).show()
                                    finish()
                                }
                                buttonResposta2.setOnClickListener{
                                    Toast.makeText(this@ResolverQuestao, "Resposta Correta!", Toast.LENGTH_SHORT).show()
                                    finish()                }
                                buttonResposta3.setOnClickListener {
                                    Toast.makeText(this@ResolverQuestao, "Resposta Errada!", Toast.LENGTH_SHORT).show()
                                    finish()
                                }
                                buttonResposta4.setOnClickListener {
                                    Toast.makeText(this@ResolverQuestao, "Resposta Errada!", Toast.LENGTH_SHORT).show()
                                    finish()
                                }
                            }
                            3 -> {
                                buttonResposta1.setOnClickListener{
                                    Toast.makeText(this@ResolverQuestao, "Resposta Errada!", Toast.LENGTH_SHORT).show()
                                    finish()
                                }
                                buttonResposta2.setOnClickListener {
                                    Toast.makeText(this@ResolverQuestao, "Resposta Errada!", Toast.LENGTH_SHORT).show()
                                    finish()
                                }
                                buttonResposta3.setOnClickListener{
                                    Toast.makeText(this@ResolverQuestao, "Resposta Correta!", Toast.LENGTH_SHORT).show()
                                    finish()                }
                                buttonResposta4.setOnClickListener {
                                    Toast.makeText(this@ResolverQuestao, "Resposta Errada!", Toast.LENGTH_SHORT).show()
                                    finish()
                                }
                            }
                            4 -> {
                                buttonResposta1.setOnClickListener{
                                    Toast.makeText(this@ResolverQuestao, "Resposta Errada!", Toast.LENGTH_SHORT).show()
                                    finish()
                                }
                                buttonResposta2.setOnClickListener {
                                    Toast.makeText(this@ResolverQuestao, "Resposta Errada!", Toast.LENGTH_SHORT).show()
                                    finish()
                                }
                                buttonResposta3.setOnClickListener {
                                    Toast.makeText(this@ResolverQuestao, "Resposta Errada!", Toast.LENGTH_SHORT).show()
                                    finish()
                                }
                                buttonResposta4.setOnClickListener{
                                    Toast.makeText(this@ResolverQuestao, "Resposta Correta!", Toast.LENGTH_SHORT).show()
                                    finish()
                                }
                            }
                        }

                    }else
                        finish()
                }
            }

            override fun onFailure(call: Call<Questao>, t: Throwable) {
                finish()
            }
        })
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