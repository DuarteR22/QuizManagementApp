package com.example.duarteramosquizgame

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.media.Image
import android.provider.BaseColumns
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.compose.ui.util.unpackFloat1
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QuestaoAdapter(
    private val context: Context,
    private var questoes: List<Questao>,
    private val uidLogado: Int,
    private val uidCriador: Int
    ):RecyclerView.Adapter<QuestaoAdapter.QuestaoViewHolder>() {

    inner class QuestaoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val textViewPergunta: TextView = itemView.findViewById(R.id.tv_pergunta_questao)
        val textViewNumRespostas: TextView = itemView.findViewById(R.id.tv_num_respostas)
        val imageViewImagem: ImageView = itemView.findViewById(R.id.iv_imagem_questao)
        val cardViewQuestao: View = itemView.findViewById(R.id.cv_questao)
        val buttonDelete: ImageButton = itemView.findViewById(R.id.btn_apagar_questao)
        val buttonEditarQuestao: ImageButton = itemView.findViewById(R.id.btn_alterar_questao)
    }

    override fun getItemCount(): Int = questoes.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestaoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_questao, parent,false  )
        return QuestaoViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: QuestaoViewHolder, position: Int) {
        val questao = questoes[position]
        val questaoId = questao.id ?: 0L

        holder.textViewPergunta.text = questao.pergunta
        holder.textViewNumRespostas.text = "${questao.numRespostas} Respostas"
        holder.itemView.tag = questaoId
        Log.d("DEBUG_PERMISSAO", "Comparando -> Logado: $uidLogado | Criador: $uidCriador")
        if (uidLogado == uidCriador) {
            Log.d("log","UID LOGADO == UID CRIADOR")
            holder.buttonDelete.visibility = View.VISIBLE
            holder.buttonEditarQuestao.visibility = View.VISIBLE
        }
        else {
            Log.d("log","UID LOGADO != UID CRIADOR")

            holder.buttonDelete.visibility = View.GONE
            holder.buttonEditarQuestao.visibility = View.GONE
        }

        if(!questao.urlImagem.isNullOrEmpty()){
            holder.imageViewImagem.scaleType = ImageView.ScaleType.CENTER_CROP
            Glide.with(context) //https://www.geeksforgeeks.org/android/image-loading-caching-library-android-set-2/
                .load(questao.urlImagem)
                .centerCrop()
                .placeholder(R.drawable.ic_resposta)
                .error(R.drawable.ic_resposta)
                .into(holder.imageViewImagem)
        }else{
            holder.imageViewImagem.scaleType = ImageView.ScaleType.CENTER_INSIDE
            holder.imageViewImagem.setImageResource(R.drawable.ic_resposta)
        }
        holder.cardViewQuestao.setOnClickListener{
            val intent = Intent(context, ResolverQuestao::class.java)
            intent.putExtra("id_questao", questaoId)
            context.startActivity(intent)
        }

        holder.buttonDelete.setOnClickListener {
            dialogConfirmar(questaoId, position, questao.pergunta)
        }
        holder.buttonEditarQuestao.setOnClickListener{
            val intent = Intent(context, AlterarQuestao::class.java)
            intent.putExtra("id_questao", questaoId)
            context.startActivity(intent)
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    fun atualizarDados(novaLista: List<Questao>) {
        this.questoes = novaLista
        notifyDataSetChanged()
    }
    fun removerQuestao(idQuestao: Long, position: Int){

        val request = EliminarQuestaoRequest(quid = idQuestao)
        ClienteRetrofit.instance.eliminarQuestao(request).enqueue(object : Callback<RegistoResposta> {
            override fun onResponse(
                call: Call<RegistoResposta>,
                response: Response<RegistoResposta>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(context, "Questão eliminada com sucesso!", Toast.LENGTH_SHORT)
                        .show()

                    val novaLista = questoes.toMutableList()
                    novaLista.removeAt(position)
                    atualizarDados(novaLista)
                } else
                    Toast.makeText(context, "Não foi possível eliminar a questão", Toast.LENGTH_SHORT).show()
            }
            override fun onFailure(call: Call<RegistoResposta>, t: Throwable) {
                Toast.makeText(context, "Falha de rede: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
    private fun dialogConfirmar(idQuestao: Long, position: Int, pergunta: String){
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Confirmar eliminação")
        builder.setMessage("Tem a certeza que deseja eliminar a pergunta '$pergunta'?")

        builder.setPositiveButton("Sim") { _, _ ->
            removerQuestao(idQuestao, position)
        }
        builder.setNegativeButton("Não") { dialog, _ ->
            dialog.dismiss()
        }
        val alertDialog = builder.create()
        alertDialog.show()
    }
}