package com.example.sportsmc

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

// Activity responsável por exibir a tela de confirmação após o cadastro
class ConfirmacaoActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmacao)

        // Recupera o nome do usuário passado pela Intent
        val nomeUsuario = intent.getStringExtra("NOME_USUARIO") ?: ""
        // Exibe uma saudação personalizada com o nome do usuário
        val txtSaudacao = findViewById<TextView>(R.id.txtSaudacao)
        txtSaudacao.text = "Saudações, $nomeUsuario!"

        // Define ação do botão para seguir para a tela principal do app
        findViewById<Button>(R.id.btnSeguirParaApp).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}