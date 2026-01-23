package com.example.duarteramosquizgame

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Registo: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registo)
        val editTextUsername: EditText
        val editTextPassword: EditText
        val editTextConfirmar: EditText
        val buttonRegistar: Button
        val textViewRegressar: TextView

        editTextUsername = findViewById(R.id.et_registo_username)
        editTextPassword = findViewById(R.id.et_registo_password)
        editTextConfirmar = findViewById(R.id.et_confirmar_password)
        buttonRegistar = findViewById(R.id.btn_registar)
        textViewRegressar = findViewById(R.id.tv_regressar_login)

        buttonRegistar.setOnClickListener {
            val username = editTextUsername.text.toString()
            val password = editTextPassword.text.toString()
            val confirmar = editTextConfirmar.text.toString()
            if (username.isEmpty()){
                editTextUsername.error = "O username é obrigatório"
                return@setOnClickListener
            }
            if (password.isEmpty()){
                editTextUsername.error = "A password é obrigatória"
                return@setOnClickListener
            }
            if (password != confirmar){
                editTextPassword.error = "As passwords têm de ser iguais!"
                editTextConfirmar.error = "As passwords têm de ser iguais!"
                return@setOnClickListener
            }

            val registoRequest = UtilizadorRequest(username = username, password = password)

            ClienteRetrofit.instance.registar(registoRequest).enqueue(object : Callback<RegistoResposta>{
                override fun onResponse(
                    call: Call<RegistoResposta>,
                    response: Response<RegistoResposta>
                ) {
                    if (response.isSuccessful){
                        Toast.makeText(this@Registo, "Conta criada com sucesso!", Toast.LENGTH_LONG).show()
                        val intent = Intent(this@Registo, Login::class.java)
                        startActivity(intent)
                        finish()
                    }else{
                        Toast.makeText(this@Registo, "Erro ao efetuar o registo.", Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<RegistoResposta>, t: Throwable) {
                    Toast.makeText(this@Registo, "Falha de rede: ${t.message}", Toast.LENGTH_LONG).show()
                }
            })
        }
        textViewRegressar.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }
    }
}