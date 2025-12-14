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

class QuizAdapter(private val context: Context,
                  private var cursor: Cursor,
    private val quizSQL: QuizSQL

): RecyclerView.Adapter<QuizAdapter.QuizViewHolder>() {
    private val idColunaIndex = cursor.getColumnIndex(BaseColumns._ID)
    private val tituloColunaIndex = cursor.getColumnIndex("titulo")
    private val descricaoColunaIndex = cursor.getColumnIndex("descricao")

    inner class QuizViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val textViewTitulo: TextView = itemView.findViewById(R.id.tv_titulo_quiz)
        val textViewDescricao: TextView = itemView.findViewById(R.id.tv_descricao_quiz)
        val buttonEditarQuiz: ImageButton = itemView.findViewById(R.id.btn_editar_quiz)
        val buttonDelete: ImageButton = itemView.findViewById(R.id.btn_apagar_quiz)
        val cardViewQuiz: View = itemView.findViewById(R.id.cv_quiz)
    }

    override fun getItemCount(): Int {

        return cursor.count
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_quiz,parent,false)
        return QuizViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuizViewHolder, position: Int) {
        if (!cursor.moveToPosition(position))
            return


        var quizId: Long

        if (idColunaIndex >=0)
            quizId = cursor.getLong(idColunaIndex)
        else
            quizId = 0

        var titulo: String
        if (idColunaIndex >= 0)
            titulo = cursor.getString(tituloColunaIndex)
        else
            titulo = "Titulo nao encontrado"

        var descricao: String
        if(descricaoColunaIndex >=0)
            descricao = cursor.getString(descricaoColunaIndex)
        else
            descricao = "Descricao nao encontrada"

        holder.textViewTitulo.text = titulo
        holder.textViewDescricao.text = descricao
        holder.itemView.tag = quizId

        holder.buttonDelete.setOnClickListener{
            removerQuiz(quizId)
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
    fun changeCursor (newCursor: Cursor){

        if (cursor != newCursor){
            cursor.close()
        }
        else {
            cursor = newCursor
            notifyDataSetChanged()
        }
    }

    fun removerQuiz(idQuiz: Long){

        val linhasAfetadas = quizSQL.eliminaQuiz(idQuiz)
        if(linhasAfetadas > 0){
            Toast.makeText(context, "Quiz ID $idQuiz removido com sucesso.", Toast.LENGTH_SHORT).show()
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }else
            Toast.makeText(context, "Erro ao remover Quiz ID $idQuiz.", Toast.LENGTH_SHORT).show()
    }



}