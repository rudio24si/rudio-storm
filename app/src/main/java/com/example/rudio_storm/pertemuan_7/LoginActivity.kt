package com.example.rudio_storm.pertemuan_7

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.rudio_storm.BaseActivity
import com.example.rudio_storm.MainActivity
import com.example.rudio_storm.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnLogin.setOnClickListener {
            val inputUser = binding.etUsername.text.toString()
            val inputPass = binding.etPassword.text.toString()

            // Ambil data dari SharedPreferences
            val sharedPref = getSharedPreferences("UserAccount", MODE_PRIVATE)
            val savedUser = sharedPref.getString("username", "")
            val savedPass = sharedPref.getString("password", "")

            if (inputUser.isNotEmpty() && inputPass.isNotEmpty()) {

                // RULE 1: Username = Password
                val isRule1Met = inputUser == inputPass

                // RULE 2: Cocok dengan SharedPreferences
                val isRule2Met = (inputUser == savedUser && inputPass == savedPass)

                if (isRule1Met || isRule2Met) {
                    Toast.makeText(this, "Login Berhasil!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, BaseActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Akun tidak ditemukan atau data salah", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Isi semua kolom", Toast.LENGTH_SHORT).show()
            }
        }

        // 4. Navigasi ke RegisterActivity
        binding.tvRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}