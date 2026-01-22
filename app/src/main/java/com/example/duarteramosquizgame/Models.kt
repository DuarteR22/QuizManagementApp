package com.example.duarteramosquizgame

import com.google.gson.annotations.SerializedName

data class UtilizadorRequest (val username: String, val password: String)

data class LoginResponse(val mensagem: String, val token: String, @SerializedName("u_uid") val u_uid: Int)

data class RegistoResposta(val mensagem: String, val qid: Int? = null)

data class RegistoQuizResposta(val mensagem: String, val qid: Int? = null)

data class RegistoQuestaoResposta(val mensagem: String, val qid: Int? = null, val quid: Int? = null)

data class EliminarQuizRequest(val qid: Int, val u_uid: Int)

data class ListaQuestaoRequest(val qid: Long)

data class EliminarQuestaoRequest(val quid: Long)

data class ListarQuestaoIdRequest(val quid: Long)

data class QuizIdRequest(@SerializedName("qid") val qid: Long)

data class ExecutarQuizRequest(val qid: Long)

data class ExecutarQuizResponse(val mensagem: String, val estado: Boolean)
