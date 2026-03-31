package com.example.rudio_storm.pertemuan_2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.rudio_storm.R

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_second)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // --- Inisialisasi Komponen Bangun Datar (Persegi Panjang) ---
        val etPanjang = findViewById<EditText>(R.id.et_panjang)
        val etLebar = findViewById<EditText>(R.id.et_lebar)
        val btnHitungLuas = findViewById<Button>(R.id.btn_hitung_luas)
        val tvHasilLuas = findViewById<TextView>(R.id.tv_hasil_luas)

        btnHitungLuas.setOnClickListener {
            val p = etPanjang.text.toString().toDoubleOrNull()
            val l = etLebar.text.toString().toDoubleOrNull()

            if (p != null && l != null) {
                val luas = p * l
                tvHasilLuas.text = "Hasil Luas: $luas"
            } else {
                Toast.makeText(this, "Masukkan angka yang valid", Toast.LENGTH_SHORT).show()
            }
        }

        // --- Inisialisasi Komponen Bangun Ruang (Volume Kubus) ---
        val etSisi = findViewById<EditText>(R.id.et_sisi)
        val btnHitungVolume = findViewById<Button>(R.id.btn_hitung_volume)
        val tvHasilVolume = findViewById<TextView>(R.id.tv_hasil_volume)

        btnHitungVolume.setOnClickListener {
            val s = etSisi.text.toString().toDoubleOrNull()

            if (s != null) {
                val volume = s * s * s
                tvHasilVolume.text = "Hasil Volume: $volume"
            } else {
                Toast.makeText(this, "Masukkan angka yang valid", Toast.LENGTH_SHORT).show()
            }
        }
    }
}