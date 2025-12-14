package com.example.duarteramosquizgame

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.provider.BaseColumns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.compose.foundation.Image
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class QuestaoAdapter(
    private val context: Context,
    private var cursor: Cursor,
    private val questaoSQL: QuestaoSQL):RecyclerView.Adapter<QuestaoAdapter.QuestaoViewHolder>() {

    private val idColunaIndex = cursor.getColumnIndex(BaseColumns._ID)
    private val perguntaIndex = cursor.getColumnIndexOrThrow("pergunta")
    private val urlImagemIndex = cursor.getColumnIndexOrThrow("url_imagem")
    private val numRespostasIndex = cursor.getColumnIndexOrThrow("num_respostas")

    inner class QuestaoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val textViewPergunta: TextView = itemView.findViewById(R.id.tv_pergunta_questao)
        val textViewNumRespostas: TextView = itemView.findViewById(R.id.tv_num_respostas)
        val imageViewImagem: ImageView = itemView.findViewById(R.id.iv_imagem_questao)
        val cardViewQuestao: View = itemView.findViewById(R.id.cv_questao)
    }

    override fun getItemCount(): Int = cursor.count

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestaoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_questao, parent,false  )
        return QuestaoViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: QuestaoViewHolder, position: Int) {
        if (!cursor.moveToPosition(position))
            return

        val questaoId = cursor.getLong(idColunaIndex)
        val pergunta = cursor.getString(perguntaIndex)
        val urlImagem = cursor.getString(urlImagemIndex)
        val numRespostas = cursor.getInt(numRespostasIndex)

        holder.textViewPergunta.text = pergunta
        holder.textViewNumRespostas.text = "$numRespostas Respostas"
        holder.itemView.tag = questaoId


        if(urlImagem.isNullOrEmpty()){
            holder.imageViewImagem.scaleType = ImageView.ScaleType.CENTER_CROP
            Glide.with(context) //https://www.geeksforgeeks.org/android/image-loading-caching-library-android-set-2/
                .load(urlImagem)
                .placeholder(R.drawable.ic_resposta)
                .error(R.drawable.ic_resposta)
                .into(holder.imageViewImagem)
        }else{
            holder.imageViewImagem.scaleType = ImageView.ScaleType.CENTER_INSIDE
            holder.imageViewImagem.setImageResource(R.drawable.ic_resposta)
        }
        holder.cardViewQuestao.setOnClickListener{

        }
    }
    @SuppressLint("NotifyDataSetChanged")
    fun changeCursor(newCursor: Cursor) {
        if (cursor != newCursor) {
            cursor.close()
        }
        cursor = newCursor
        notifyDataSetChanged()
    }
}