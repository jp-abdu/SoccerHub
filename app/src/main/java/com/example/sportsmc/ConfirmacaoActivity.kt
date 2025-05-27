package com.example.sportsmc

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ConfirmacaoActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
// ConfirmacaoActivity.kt
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmacao)

        val nomeUsuario = intent.getStringExtra("NOME_USUARIO") ?: ""
        val txtSaudacao = findViewById<TextView>(R.id.txtSaudacao)
        txtSaudacao.text = "Olá, $nomeUsuario!"

        findViewById<Button>(R.id.btnSeguirParaApp).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}