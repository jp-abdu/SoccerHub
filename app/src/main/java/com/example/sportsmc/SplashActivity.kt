package com.example.sportsmc

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

// Activity de splash/tela inicial do app
class SplashActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash) // Define o layout da tela de splash

        // Ao clicar no botão, inicia a tela de cadastro e finaliza a splash
        findViewById<Button>(R.id.btnIniciarCadastro).setOnClickListener {
            startActivity(Intent(this, CadastroActivity::class.java))
            finish()
        }
    }
}