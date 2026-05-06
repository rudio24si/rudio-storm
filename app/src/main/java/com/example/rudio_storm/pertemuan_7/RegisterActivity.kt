package com.example.rudio_storm.pertemuan_7

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.rudio_storm.databinding.ActivityRegisterBinding
import java.util.Calendar

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            // Menambahkan padding sistem ke padding asli (24dp)
            v.setPadding(
                v.paddingLeft + systemBars.left,
                v.paddingTop + systemBars.top,
                v.paddingRight + systemBars.right,
                v.paddingBottom + systemBars.bottom
            )
            insets
        }

        // 1. Setup Dropdown Agama (AutoCompleteTextView)
        val listAgama = arrayOf("Islam", "Kristen", "Katolik", "Hindu", "Budha", "Konghucu")
        val adapterAgama =
            ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, listAgama)
        binding.actvAgama.setAdapter(adapterAgama)

        // 2. Setup DatePicker untuk Tanggal Lahir
        binding.etTglLahir.setOnClickListener {
            showDatePicker()
        }

        // 3. Tombol Daftar
        binding.btnRegister.setOnClickListener {
            val nama = binding.etNama.text.toString()
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()
            val konfirmasi = binding.etConfirmPassword.text.toString()

            // Mengambil nilai Radio Button
            val selectedJKId = binding.rgJenisKelamin.checkedRadioButtonId

            if (nama.isEmpty() || username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Harap lengkapi data", Toast.LENGTH_SHORT).show()
            } else if (password != konfirmasi) {
                Toast.makeText(this, "Password tidak cocok!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Pendaftaran Berhasil!", Toast.LENGTH_SHORT).show()
                finish() // Kembali ke Login
            }

            val sharedPref = getSharedPreferences("UserAccount", MODE_PRIVATE)
            val editor = sharedPref.edit()

            editor.putString("username", username)
            editor.putString("password", password)
            editor.apply() // Simpan data

            Toast.makeText(this, "Data tersimpan di SharedPreferences!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            val date = "$selectedDay/${selectedMonth + 1}/$selectedYear"
            binding.etTglLahir.setText(date)
        }, year, month, day)

        datePickerDialog.show()
    }
}