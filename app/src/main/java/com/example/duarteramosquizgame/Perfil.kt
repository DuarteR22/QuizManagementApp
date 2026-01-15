package com.example.duarteramosquizgame

import android.annotation.SuppressLint
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

class Perfil : AppCompatActivity() {


        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.perfil)
            val textViewUsername: TextView
            val textViewPassword: TextView
            val buttonLogout: Button
            val textViewRegressar: TextView

            textViewUsername = findViewById(R.id.tv_username)
            textViewPassword = findViewById(R.id.tv_password)
            buttonLogout = findViewById(R.id.btn_logout)
            textViewRegressar = findViewById(R.id.tv_regressar)
            val sharedPref = getSharedPreferences("sessao", MODE_PRIVATE)
            val usernameGuardado = sharedPref.getString("username", "Utilizador")
            textViewUsername.text = usernameGuardado
            textViewPassword.text = "••••••••••••"

            buttonLogout.setOnClickListener {
                val prefs = getSharedPreferences("sessao", MODE_PRIVATE)
                with(prefs.edit()) {
                    clear()
                    apply()
                }

                val intent = Intent(this, Login::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
            textViewRegressar.setOnClickListener {
                finish()
            }
        }
}
