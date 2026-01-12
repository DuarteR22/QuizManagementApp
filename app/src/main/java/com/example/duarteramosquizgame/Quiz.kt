package com.example.duarteramosquizgame

import com.google.gson.annotations.SerializedName

data class Quiz(@SerializedName("qid") val id: Long? = null, val titulo: String, val descricao: String, val tempo_max: Int, val utilizador_uid: Int? = null, val em_execucao: Boolean = false, val username_criador: String? = null) {

}