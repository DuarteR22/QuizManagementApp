package com.example.duarteramosquizgame

import com.google.gson.annotations.SerializedName

data class Questao(@SerializedName("quid") val id: Long? = null, val pergunta : String, @SerializedName("num_respostas") val numRespostas : Int, val respostas: List<String>, @SerializedName("resposta_correta") val respostaCorreta: Int, @SerializedName("url_imagem") val urlImagem: String?=null, @SerializedName("quiz_qid") val quizId: Long) {

}