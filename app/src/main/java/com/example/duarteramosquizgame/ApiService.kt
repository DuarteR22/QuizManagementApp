package com.example.duarteramosquizgame

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST("login_utilizador")
    fun login(@Body req: UtilizadorRequest): Call<LoginResponse>

    @GET("quizzes")
    fun getQuizzes(): Call<List<Quiz>>

    @POST("inserir_utilizador")
    fun registar(@Body req: UtilizadorRequest): Call<RegistoResposta>

    @POST("inserir_quiz")
    fun inserirQuiz(@Body quiz: Quiz): Call<RegistoQuizResposta>

    @POST("inserir_questao")
    fun inserirQuestao(@Body questao: Questao): Call<RegistoQuestaoResposta>

    @POST("eliminar_quiz")
    fun eliminarQuiz(@Body req: EliminarQuizRequest) : Call<RegistoResposta>

}