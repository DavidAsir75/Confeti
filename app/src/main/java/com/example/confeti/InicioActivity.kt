package com.example.confeti

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class InicioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)

        val etJugadorX = findViewById<EditText>(R.id.etJugadorX)
        val etJugadorO = findViewById<EditText>(R.id.etJugadorO)
        val btnComenzar = findViewById<Button>(R.id.btnComenzar)

        btnComenzar.setOnClickListener {
            val nombreX = etJugadorX.text.toString()
            val nombreO = etJugadorO.text.toString()

            val intent = Intent(this, MainActivity::class.java).apply {
                putExtra("NOMBRE_X", nombreX)
                putExtra("NOMBRE_O", nombreO)
            }
            startActivity(intent)
        }
    }
}