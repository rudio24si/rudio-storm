package com.example.rudio_storm

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.rudio_storm.databinding.ActivityMainBinding
import com.example.rudio_storm.pertemuan_2.SecondActivity
import com.example.rudio_storm.pertemuan_3.ThirdActivity
import com.example.rudio_storm.pertemuan_4.Custom1Activity
import com.example.rudio_storm.pertemuan_4.Custom2Activity
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Penanganan Padding agar tidak mepet (Menggabungkan XML Padding + System Bars)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            val originalPaddingLeft = v.paddingLeft
            val originalPaddingTop = v.paddingTop
            val originalPaddingRight = v.paddingRight
            val originalPaddingBottom = v.paddingBottom

            v.setPadding(
                originalPaddingLeft + systemBars.left,
                originalPaddingTop + systemBars.top,
                originalPaddingRight + systemBars.right,
                originalPaddingBottom + systemBars.bottom
            )
            insets
        }

        // 1. Tombol Rumus ke SecondActivity (Pertemuan 2)
        binding.btnRumus.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }

        // 2. Tombol Custom 1 ke Custom1Activity (Pertemuan 4)
        binding.btnCustom1.setOnClickListener {
            val intent = Intent(this, Custom1Activity::class.java)
            startActivity(intent)
        }

        // 3. Tombol Custom 2 ke Custom2Activity (Pertemuan 4)
        binding.btnCustom2.setOnClickListener {
            val intent = Intent(this, Custom2Activity::class.java)
            startActivity(intent)
        }

        // 4. Tombol Logout dengan Konfirmasi Dialog
        binding.btnLogout.setOnClickListener {
            showLogoutDialog()
        }

        binding.chipGroupFilter.setOnCheckedStateChangeListener { group, checkedIds ->
            val selectedChipId = checkedIds.firstOrNull() // Ambil ID chip yang dipilih
            if (selectedChipId != null) {
                val chip = group.findViewById<Chip>(selectedChipId)
                Toast.makeText(this, "Filter: ${chip.text}", Toast.LENGTH_SHORT).show()
                // Lakukan logika filter di sini
            }
        }
    }

    private fun showLogoutDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Konfirmasi Logout")
        builder.setMessage("Apakah Anda yakin ingin keluar dari aplikasi?")

        builder.setPositiveButton("Ya") { _, _ ->
            val intent = Intent(this, ThirdActivity::class.java)
            startActivity(intent)
            finish()
        }

        builder.setNegativeButton("Tidak") { dialog, _ ->
            dialog.dismiss()
            // Munculkan SnackBar di bagian bawah
            Snackbar.make(binding.main, "Logout dibatalkan", Snackbar.LENGTH_SHORT).show()
        }

        val alertDialog = builder.create()
        alertDialog.show()
    }
}