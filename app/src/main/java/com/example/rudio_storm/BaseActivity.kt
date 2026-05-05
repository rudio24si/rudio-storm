package com.example.rudio_storm

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.rudio_storm.databinding.ActivityBaseBinding
import com.example.rudio_storm.databinding.ActivityMainBinding
import com.example.rudio_storm.pertemuan_7.AboutFragment
import com.example.rudio_storm.pertemuan_7.HomeFragment
import com.example.rudio_storm.pertemuan_7.ProfileFragment

class BaseActivity : AppCompatActivity() {
    private lateinit var binding : ActivityBaseBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        setContentView(R.layout.activity_base)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

//        binding.bottomNavView.setOnItemSelectedListener {
//            when (it.itemId) {
//                R.id.home -> {
//                    Toast.makeText(this, "Home Clicked", Toast.LENGTH_SHORT).show()
//                    true
//                }
//                R.id.about -> {
//                    Toast.makeText(this, "About Clicked", Toast.LENGTH_SHORT).show()
//                    true
//                }
//                R.id.profie -> {
//                    Toast.makeText(this, "Profie Clicked", Toast.LENGTH_SHORT).show()
//                    true
//                }
//                else -> false // return false jika item tidak ada yang di klik
//            }
//        }

        /** FragmentHome sebagai fragment default */
        replaceFragment(HomeFragment())

        binding.bottomNavView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.about -> {
                    replaceFragment(AboutFragment())
                    true
                }
                R.id.profie -> {
                    replaceFragment(ProfileFragment())
                    true
                }
                else ->false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, fragment)
            //.addToBackStack(null) -> ini kita nonaktifkan agar saat back langsung keluar aplikasi
            .commit()
    }
}