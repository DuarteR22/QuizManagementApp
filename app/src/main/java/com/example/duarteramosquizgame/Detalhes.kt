package com.example.duarteramosquizgame

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class Detalhes: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.informacoes)
        val buttonRegressar: Button = findViewById(R.id.btn_regressar)
        buttonRegressar.setOnClickListener {
            val intentRegressar = Intent(this, MainActivity::class.java)
            startActivity(intentRegressar)
        }
    }
}