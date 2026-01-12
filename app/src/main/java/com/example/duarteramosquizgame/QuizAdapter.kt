package com.example.duarteramosquizgame

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.provider.BaseColumns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QuizAdapter(private val context: Context,
                  private var quizzes: List<Quiz>,
                  private val uidLogado: Int

): RecyclerView.Adapter<QuizAdapter.QuizViewHolder>() {

    inner class QuizViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val textViewTitulo: TextView = itemView.findViewById(R.id.tv_titulo_quiz)
        val textViewDescricao: TextView = itemView.findViewById(R.id.tv_descricao_quiz)
        val buttonEditarQuiz: ImageButton = itemView.findViewById(R.id.btn_editar_quiz)
        val buttonDelete: ImageButton = itemView.findViewById(R.id.btn_apagar_quiz)
        val cardViewQuiz: View = itemView.findViewById(R.id.cv_quiz)
    }

    override fun getItemCount(): Int = quizzes.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_quiz,parent,false)
        return QuizViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuizViewHolder, position: Int) {

        val quiz = quizzes[position]
        val quizId = quiz.id ?: 0L

        holder.textViewTitulo.text = quiz.titulo
        holder.textViewDescricao.text = quiz.descricao
        holder.itemView.tag = quizId

        if (quiz.utilizador_uid == uidLogado) {
            holder.buttonDelete.visibility = View.VISIBLE
            holder.buttonEditarQuiz.visibility = View.VISIBLE
        }else{
            holder.buttonDelete.visibility = View.GONE
            holder.buttonEditarQuiz.visibility = View.GONE
        }

        holder.buttonDelete.setOnClickListener{
            removerQuiz(quizId, position)
        }
        holder.buttonEditarQuiz.setOnClickListener{
            val intent = Intent(context, AlterarQuiz::class.java)
            intent.putExtra("id_quiz", quizId)
            context.startActivity(intent)
        }
        holder.cardViewQuiz.setOnClickListener{
            val intent = Intent(context, ListaQuestoes::class.java)
            intent.putExtra("id_quiz", quizId)
            context.startActivity(intent)
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    fun atualizarDados (novaLista: List<Quiz>){

        this.quizzes = novaLista
        notifyDataSetChanged()
    }

    fun removerQuiz(idQuiz: Long, position: Int) {

        val request = EliminarQuizRequest(
            qid = idQuiz.toInt(),
            u_uid = uidLogado
        )
        ClienteRetrofit.instance.eliminarQuiz(request).enqueue(object: Callback<RegistoResposta>{
            override fun onResponse(
                call: Call<EliminarQuizRequest>,
                response: Response<EliminarQuizRequest>
            ) {
                if(response.isSuccessful){
                    Toast.makeText(context, "Quiz eliminado com sucesso!", Toast.LENGTH_SHORT).show()
                    val novaLista = quizzes.toMutableList()
                    novaLista.removeAt(position)
                    atualizarDados(novaLista)
                }else{
                    Toast.makeText(context, "Não tens permissão para apagar este quiz", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<EliminarQuizRequest>, t: Throwable) {
                Toast.makeText(context, "Erro de ligação", Toast.LENGTH_SHORT).show()
            }
        })
    }
}