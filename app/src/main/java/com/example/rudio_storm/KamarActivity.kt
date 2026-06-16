package com.example.rudio_storm

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.rudio_storm.adapter.KamarAdapter
import com.example.rudio_storm.data.AppDatabase
import com.example.rudio_storm.data.model.KamarEntity
import com.example.rudio_storm.databinding.ActivityKamarBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch

class KamarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityKamarBinding
    private lateinit var db: AppDatabase
    private lateinit var kamarAdapter: KamarAdapter
    private val kamarList = mutableListOf<KamarEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityKamarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        db = AppDatabase.getInstance(this)
        kamarAdapter = KamarAdapter(kamarList, this)

        binding.rvKamar.apply {
            layoutManager = GridLayoutManager(this@KamarActivity, 2)
            adapter = kamarAdapter
        }

        binding.fabAddKamar.setOnClickListener {
            showAddKamarDialog()
        }

        fetchKamar()
    }

    private fun fetchKamar() {
        lifecycleScope.launch {
            val data = db.kamarDao().getAll()
            kamarList.clear()
            kamarList.addAll(data)
            kamarAdapter.notifyDataSetChanged()
        }
    }

    fun deleteKamar(kamar: KamarEntity) {
        lifecycleScope.launch {
            db.kamarDao().delete(kamar)
            fetchKamar()
        }
    }

    private fun showAddKamarDialog() {
        val dialogView = layoutInflater.inflate(android.R.layout.activity_list_item, null)

        // Gunakan dialog input manual
        val etNama = TextInputEditText(this).apply { hint = "Nama Kamar" }
        val etHarga = TextInputEditText(this).apply { hint = "Harga (contoh: Rp 300.000)" }
        val etImageUrl = TextInputEditText(this).apply { hint = "URL Gambar (opsional)" }

        val container = android.widget.LinearLayout(this).apply {
            orientation = android.widget.LinearLayout.VERTICAL
            setPadding(48, 16, 48, 0)
            addView(etNama)
            addView(etHarga)
            addView(etImageUrl)
        }

        MaterialAlertDialogBuilder(this)
            .setTitle("Tambah Kamar")
            .setView(container)
            .setPositiveButton("Simpan") { _, _ ->
                val nama = etNama.text.toString()
                val harga = etHarga.text.toString()
                val imageUrl = etImageUrl.text.toString().ifBlank {
                    "https://images.unsplash.com/photo-1598928506311-c55ded91a20c?auto=format&fit=crop&w=400&q=80"
                }

                if (nama.isNotBlank() && harga.isNotBlank()) {
                    lifecycleScope.launch {
                        db.kamarDao().insert(KamarEntity(name = nama, price = harga, imageUrl = imageUrl))
                        fetchKamar()
                    }
                } else {
                    Toast.makeText(this, "Nama dan harga wajib diisi!", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Batal") { dialog, _ -> dialog.dismiss() }
            .show()
    }
}