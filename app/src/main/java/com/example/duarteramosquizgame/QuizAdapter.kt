package com.example.duarteramosquizgame

import android.content.Context
import android.database.Cursor
import android.provider.BaseColumns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class QuizAdapter(private val context: Context,
                  private var cursor: Cursor
): RecyclerView.Adapter<QuizAdapter.QuizViewHolder>() {


    inner class QuizViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val textViewTitulo: TextView = itemView.findViewById(R.id.textView_quiz_title)
        val textViewDescricao: TextView = itemView.findViewById(R.id.textView_quiz_description)
        val buttonMenu: ImageButton = itemView.findViewById(R.id.button_quiz_menu)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_quiz,parent,false)
        return QuizViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuizViewHolder, position: Int) {
        if(!cursor.moveToPosition(position))
            return
    }

    val idColuna = cursor.getColumnIndex(BaseColumns._ID)
    val quizId = if(idColuna >=0) cursor.getLong(idColuna) else 0


}