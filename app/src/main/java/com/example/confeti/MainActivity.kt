package com.example.confeti

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var tablero: Array<Array<Button>>
    private lateinit var tvTurno: TextView

    private var jugadorActual = 'X'
    private var movimientos = 0
    private var nombreJugadorX = ""
    private var nombreJugadorO = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvTurno = findViewById(R.id.tvTurno)
        val gridLayout = findViewById<GridLayout>(R.id.tablero)
        val btnReiniciar = findViewById<Button>(R.id.btnReiniciar)

        nombreJugadorX = intent.getStringExtra("NOMBRE_X") ?: "Jugador X"
        nombreJugadorO = intent.getStringExtra("NOMBRE_O") ?: "Jugador O"
        actualizarTurno()

        tablero = Array(3) { fila ->
            Array(3) { columna ->
                Button(this).apply {
                    text = ""
                    textSize = 24f
                    setOnClickListener { realizarMovimiento(this, fila, columna) }
                    gridLayout.addView(this, GridLayout.LayoutParams().apply {
                        width = 0
                        height = 0
                        rowSpec = GridLayout.spec(fila, 1f)
                        columnSpec = GridLayout.spec(columna, 1f)
                    })
                }
            }
        }

        btnReiniciar.setOnClickListener { reiniciarJuego() }
    }

    private fun realizarMovimiento(boton: Button, fila: Int, columna: Int) {
        if (boton.text.isNotEmpty()) return

        boton.text = jugadorActual.toString()
        movimientos++

        if (verificarGanador()) {
            mostrarGanadorDialog(if (jugadorActual == 'X') nombreJugadorX else nombreJugadorO)
            deshabilitarTablero()
            return
        }

        if (movimientos == 9) {
            mostrarEmpateDialog()
            return
        }

        jugadorActual = if (jugadorActual == 'X') 'O' else 'X'
        actualizarTurno()
    }

    private fun verificarGanador(): Boolean {
        for (i in 0..2) {
            if (tablero[i].all { it.text == jugadorActual.toString() }) return true
            if (tablero.map { it[i] }.all { it.text == jugadorActual.toString() }) return true
        }
        if (tablero[0][0].text == jugadorActual.toString() &&
            tablero[1][1].text == jugadorActual.toString() &&
            tablero[2][2].text == jugadorActual.toString()) return true
        if (tablero[0][2].text == jugadorActual.toString() &&
            tablero[1][1].text == jugadorActual.toString() &&
            tablero[2][0].text == jugadorActual.toString()) return true

        return false
    }

    private fun deshabilitarTablero() {
        tablero.flatten().forEach { it.isEnabled = false }
    }

    private fun reiniciarJuego() {
        tablero.flatten().forEach {
            it.text = ""
            it.isEnabled = true
        }
        jugadorActual = 'X'
        movimientos = 0
        actualizarTurno()
    }

    private fun actualizarTurno() {
        tvTurno.text = "Turno de ${if (jugadorActual == 'X') nombreJugadorX else nombreJugadorO}"
    }

    private fun mostrarGanadorDialog(nombreGanador: String) {
        val confettiView = findViewById<View>(R.id.confettiView)
        confettiView.visibility = View.VISIBLE  // Mostrar la animación de confeti

        val builder = AlertDialog.Builder(this)
        builder.setTitle("¡Tenemos un ganador!")
        builder.setMessage("$nombreGanador ha ganado la partida.")
        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
            confettiView.visibility = View.GONE  // Ocultar la animación después del diálogo
            reiniciarJuego()
        }
        builder.setCancelable(false)
        builder.show()
    }

    private fun mostrarEmpateDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("¡Empate!")
        builder.setMessage("La partida ha terminado en empate.")
        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
            reiniciarJuego()
        }
        builder.setCancelable(false)
        builder.show()
    }
}
