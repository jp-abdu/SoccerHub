package com.example.sportsmc

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class CadastroActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        findViewById<Button>(R.id.btnEnviarCadastro).setOnClickListener {
            // Aqui você pode validar e salvar os dados, depois ir para a tela de confirmação
            startActivity(Intent(this, ConfirmacaoActivity::class.java))
            finish()
        }

        findViewById<Button>(R.id.btnFaleConosco).setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:suporte@sportsmc.com")
                putExtra(Intent.EXTRA_SUBJECT, "Dúvida ou Sugestão")
            }
            startActivity(intent)
        }
    }
}