package com.example.duarteramosquizgame

import com.google.gson.annotations.SerializedName

data class Quiz(@SerializedName("qid") val id: Long? = null, @SerializedName("titulo") val titulo: String,@SerializedName("descricao") val descricao: String,@SerializedName("tempo_max") val tempo_max: Int, @SerializedName("utilizador_uid") val utilizador_uid: Int? = null,@SerializedName("em_execucao") val em_execucao: Boolean = false,@SerializedName("username_criador") val username_criador: String? = null) {

}