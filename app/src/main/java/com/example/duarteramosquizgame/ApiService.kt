package com.example.duarteramosquizgame

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    @POST("login_utilizador")
    fun login(@Body req: UtilizadorRequest): Call<LoginResponse>

    @GET("listar_quizzes")
    fun listarQuizzes(@Header("Authorization") token: String): Call<List<Quiz>>

    @POST("inserir_utilizador")
    fun registar(@Body req: UtilizadorRequest): Call<RegistoResposta>

    @POST("inserir_quiz")
    fun inserirQuiz(@Body quiz: Quiz): Call<RegistoQuizResposta>

    @POST("inserir_questao")
    fun inserirQuestao(@Body questao: Questao): Call<RegistoQuestaoResposta>

    @POST("eliminar_quiz")
    fun eliminarQuiz(@Body req: EliminarQuizRequest) : Call<RegistoResposta>

    @POST("listar_questoes")
    fun listarQuestoes(@Body req: ListaQuestaoRequest): Call<List<Questao>>

    @POST("eliminar_questao")
    fun eliminarQuestao(@Body req: EliminarQuestaoRequest): Call<RegistoResposta>

    @POST("alterar_questao")
    fun alterarQuestao(@Body questao: Questao): Call<RegistoResposta>

    @POST("listar_questao_id")
    fun listarQuestaoId(@Body req: ListarQuestaoIdRequest):  Call<Questao>

    @POST("alterar_quiz")
    fun alterarQuiz(@Body quiz: Quiz): Call<RegistoResposta>

    @POST("listar_quiz_id")
    fun listarQuizId(@Body request: QuizIdRequest): Call<Quiz>

    @POST("executar_quiz")
    fun executarQuiz(@Body req: ExecutarQuizRequest): Call<ExecutarQuizResponse>

    @POST("terminar_quiz")
    fun terminarQuiz(@Body req: ExecutarQuizRequest): Call<ExecutarQuizResponse>
}