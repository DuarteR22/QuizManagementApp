package com.example.duarteramosquizgame

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Response

//https://www.behance.net/gallery/183237173/Onboarding-%28Login-Register%29-Mobile-App-UIUX-Design
class Login: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPref = getSharedPreferences("sessao", MODE_PRIVATE)
        val tokenExistente = sharedPref.getString("token", null)
        if (tokenExistente != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }
        setContentView(R.layout.login)
        val editTextUsername: EditText
        val editTextPassword: EditText
        val buttonLogin: Button
        val textViewRegistar: TextView

        editTextUsername = findViewById(R.id.et_username)
        editTextPassword = findViewById(R.id.et_password)
        buttonLogin = findViewById(R.id.btn_login)
        textViewRegistar = findViewById(R.id.tv_registar)

        buttonLogin.setOnClickListener {
            val username = editTextUsername.text.toString()
            val password = editTextPassword.text.toString()

            if (username.isEmpty() || password.isEmpty()){
                editTextUsername.error = "O username é obrigatório!"
                editTextPassword.error = "A password é obrigatória!"
                return@setOnClickListener
            }
            val loginRequest = UtilizadorRequest(username = username,password = password)
            ClienteRetrofit.instance.login(loginRequest).enqueue(object : retrofit2.Callback<LoginResponse>{
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    if (response.isSuccessful) {
                        val dados = response.body()
                        val sharedPref = getSharedPreferences("sessao", MODE_PRIVATE)
                        with(sharedPref.edit()) {
                            putString("token", dados?.token)
                            putInt("u_uid", dados?.u_uid ?: -1)
                            apply()
                        }
                        startActivity(Intent(this@Login, MainActivity::class.java))
                        finish()

                    }else{
                        Toast.makeText(this@Login, "Utilizador ou Password inválidos", Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: retrofit2.Call<LoginResponse>, t: Throwable) {
                    Toast.makeText(this@Login, "Erro de ligação: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
        textViewRegistar.setOnClickListener {
            val intent = Intent(this, Registo::class.java)
            startActivity(intent)
        }
    }
}