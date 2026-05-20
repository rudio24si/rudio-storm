package com.example.rudio_storm

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.rudio_storm.adapter.KamarAdapter // Pastikan path import adapter Anda benar
import com.example.rudio_storm.databinding.ActivityKamarBinding
import com.example.rudio_storm.model.KamarModel

class KamarActivity : AppCompatActivity() {
    private lateinit var binding: ActivityKamarBinding

    private val kamarList = listOf(
        KamarModel("Kamar Standard Single", "Rp 250.000", "https://images.unsplash.com/photo-1598928506311-c55ded91a20c?auto=format&fit=crop&w=400&q=80"),
        KamarModel("Kamar Standard Twin", "Rp 350.000", "https://images.unsplash.com/photo-1566665797739-1674de7a421a?auto=format&fit=crop&w=400&q=80"),
        KamarModel("Kamar Superior Double", "Rp 450.000", "https://images.unsplash.com/photo-1590490360182-c33d57733427?auto=format&fit=crop&w=400&q=80"),
        KamarModel("Kamar Deluxe King", "Rp 600.000", "https://images.unsplash.com/photo-1591088398332-8a7791972843?auto=format&fit=crop&w=400&q=80"),
        KamarModel("Kamar Executive Suite", "Rp 1.200.000", "https://images.unsplash.com/photo-1631049307264-da0ec9d70304?auto=format&fit=crop&w=400&q=80"),
        KamarModel("Kamar Family Suite XL", "Rp 1.500.000", "https://images.unsplash.com/photo-1578683010236-d716f9a3f461?auto=format&fit=crop&w=400&q=80"),
        KamarModel("Kamar Presidential Suite", "Rp 3.500.000", "https://images.unsplash.com/photo-1540518614846-7eded433c457?auto=format&fit=crop&w=400&q=80"),
        KamarModel("Kamar Studio Apartment", "Rp 750.000", "https://images.unsplash.com/photo-1522708323590-d24dbb6b0267?auto=format&fit=crop&w=400&q=80"),
        KamarModel("Kamar Single Minimalis (Kos)", "Rp 150.000", "https://images.unsplash.com/photo-1505693416388-ac5ce068fe85?auto=format&fit=crop&w=400&q=80"),
        KamarModel("Kamar Capsule Executive", "Rp 180.000", "https://images.unsplash.com/photo-1554118811-1e0d58224f24?auto=format&fit=crop&w=400&q=80"),

        KamarModel("Kamar Deluxe Ocean View", "Rp 850.000", "https://images.unsplash.com/photo-1582719508461-905c673771fd?auto=format&fit=crop&w=400&q=80"),
        KamarModel("Kamar Deluxe Pool View", "Rp 700.000", "https://images.unsplash.com/photo-1571896349842-33c89424de2d?auto=format&fit=crop&w=400&q=80"),
        KamarModel("Kamar Deluxe Garden View", "Rp 650.000", "https://images.unsplash.com/photo-1549294413-26f195afcbce?auto=format&fit=crop&w=400&q=80"),
        KamarModel("Kamar Suite Balkon Pribadi", "Rp 1.350.000", "https://images.unsplash.com/photo-1596394516093-501ba68a0ba6?auto=format&fit=crop&w=400&q=80"),
        KamarModel("Kamar VIP rudio_storm", "Rp 2.000.000", "https://images.unsplash.com/photo-1618773928121-c32242e63f39?auto=format&fit=crop&w=400&q=80"),
        KamarModel("Kamar Standard Queen", "Rp 400.000", "https://images.unsplash.com/photo-1568495248636-6432b97bd949?auto=format&fit=crop&w=400&q=80"),
        KamarModel("Kamar Ekonomi Bersama", "Rp 95.000", "https://images.unsplash.com/photo-1555854877-bab0e564b8d5?auto=format&fit=crop&w=400&q=80"),
        KamarModel("Kamar Premium Transit (6 Jam)", "Rp 220.000", "https://images.unsplash.com/photo-1512918728675-ed5a9ecdebfd?auto=format&fit=crop&w=400&q=80"),
        KamarModel("Kamar Honeymoon Romantic Suite", "Rp 1.800.000", "https://images.unsplash.com/photo-1611048267451-e6ed903d4a38?auto=format&fit=crop&w=400&q=80"),
        KamarModel("Kamar Loft Penthouse", "Rp 4.500.000", "https://images.unsplash.com/photo-1600210492486-724fe5c67fb0?auto=format&fit=crop&w=400&q=80"),

        KamarModel("Kamar Isoman AC", "Rp 300.000", "https://images.unsplash.com/photo-1629140727571-9b5c6f6267b4?auto=format&fit=crop&w=400&q=80"),
        KamarModel("Kamar Isoman Non-AC", "Rp 175.000", "https://images.unsplash.com/photo-1513694203232-719a280e022f?auto=format&fit=crop&w=400&q=80"),
        KamarModel("Kamar Kos Eksklusif Kamar Mandi Dalam", "Rp 250.000", "https://images.unsplash.com/photo-1598928636135-d146006ff4be?auto=format&fit=crop&w=400&q=80"),
        KamarModel("Kamar Kos Putra Standar", "Rp 100.000", "https://images.unsplash.com/photo-1536376072261-38c75010e6c9?auto=format&fit=crop&w=400&q=80"),
        KamarModel("Kamar Kos Putri Standar", "Rp 100.000", "https://images.unsplash.com/photo-1583847268964-b28dc8f51f92?auto=format&fit=crop&w=400&q=80"),
        KamarModel("Kamar Villa Studio Wood", "Rp 950.000", "https://images.unsplash.com/photo-1542314831-068cd1dbfeeb?auto=format&fit=crop&w=400&q=80"),
        KamarModel("Kamar Villa Family 2 Bed", "Rp 2.100.000", "https://images.unsplash.com/photo-1564013799919-ab600027ffc6?auto=format&fit=crop&w=400&q=80"),
        KamarModel("Kamar Cottage Bamboo Cozy", "Rp 550.000", "https://images.unsplash.com/photo-1439066615861-d1af74d74000?auto=format&fit=crop&w=400&q=80"),
        KamarModel("Kamar Bungalow Private Beach", "Rp 2.800.000", "https://images.unsplash.com/photo-1499793983690-e29da59ef1c2?auto=format&fit=crop&w=400&q=80"),
        KamarModel("Kamar Glamping Luxury Dome", "Rp 1.100.000", "https://images.unsplash.com/photo-1470071459604-3b5ec3a7fe05?auto=format&fit=crop&w=400&q=80"),

        KamarModel("Kamar Asrama Bunk Bed", "Rp 85.000", "https://images.unsplash.com/photo-1507652313519-d4e9174996dd?auto=format&fit=crop&w=400&q=80"),
        KamarModel("Kamar Backpacker Cabin", "Rp 120.000", "https://images.unsplash.com/photo-1510798831971-661eb04b3739?auto=format&fit=crop&w=400&q=80"),
        KamarModel("Kamar Klasik Tradisional", "Rp 380.000", "https://images.unsplash.com/photo-1565183997392-2f6f122e5912?auto=format&fit=crop&w=400&q=80"),
        KamarModel("Kamar Jepang Tatami Style", "Rp 800.000", "https://images.unsplash.com/photo-1504280390367-361c6d9f38f4?auto=format&fit=crop&w=400&q=80"),
        KamarModel("Kamar Modern Minimalis Nordic", "Rp 420.000", "https://images.unsplash.com/photo-1522771739844-6a9f6d5f14af?auto=format&fit=crop&w=400&q=80"),
        KamarModel("Kamar Vintage Retro", "Rp 390.000", "https://images.unsplash.com/photo-1560448204-e02f11c3d0e2?auto=format&fit=crop&w=400&q=80"),
        KamarModel("Kamar Industrial Urban", "Rp 460.000", "https://images.unsplash.com/photo-1534595038511-9f219fe0c979?auto=format&fit=crop&w=400&q=80"),
        KamarModel("Kamar Suite Business Class", "Rp 1.650.000", "https://images.unsplash.com/photo-1551882547-ff40c63fe5fa?auto=format&fit=crop&w=400&q=80"),
        KamarModel("Kamar Grand Master Suite", "Rp 2.700.000", "https://images.unsplash.com/photo-1544161515-4ab6ce6db874?auto=format&fit=crop&w=400&q=80"),
        KamarModel("Kamar Smart Room Voice Control", "Rp 990.000", "https://images.unsplash.com/photo-1558002038-1055907df827?auto=format&fit=crop&w=400&q=80"),

        KamarModel("Kamar Soundproof Music Studio", "Rp 600.000", "https://images.unsplash.com/photo-1598488035139-bdbb2231ce04?auto=format&fit=crop&w=400&q=80"),
        KamarModel("Kamar Gaming Streamer Layout", "Rp 750.000", "https://images.unsplash.com/photo-1542751371-adc38448a05e?auto=format&fit=crop&w=400&q=80"),
        KamarModel("Kamar Standar Modular", "Rp 230.000", "https://images.unsplash.com/photo-1517541621753-44ff9a26fa5f?auto=format&fit=crop&w=400&q=80"),
        KamarModel("Kamar Transit Bandara VIP", "Rp 500.000", "https://images.unsplash.com/photo-1444212477490-ca407925329e?auto=format&fit=crop&w=400&q=80"),
        KamarModel("Kamar Heritage Keraton", "Rp 1.400.000", "https://images.unsplash.com/photo-1584132967334-10e028bd69f7?auto=format&fit=crop&w=400&q=80"),
        KamarModel("Kamar Eco-Green Nature Lodge", "Rp 880.000", "https://images.unsplash.com/photo-1445019980597-93fa8acb246c?auto=format&fit=crop&w=400&q=80"),
        KamarModel("Kamar Alpine Cabin Attic", "Rp 1.250.000", "https://images.unsplash.com/photo-1518780664697-55e3ad937233?auto=format&fit=crop&w=400&q=80"),
        KamarModel("Kamar Hollywood Twin Bed", "Rp 490.000", "https://images.unsplash.com/photo-1463620910506-d0458a43143e?auto=format&fit=crop&w=400&q=80"),
        KamarModel("Kamar Khusus Lansia & Difabel", "Rp 350.000", "https://images.unsplash.com/photo-1573164713714-d95e436ab8d6?auto=format&fit=crop&w=400&q=80"),
        KamarModel("Kamar Royal Penthouse Sky View", "Rp 5.500.000", "https://images.unsplash.com/photo-1576013551627-0cc20b96c2a7?auto=format&fit=crop&w=400&q=80")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityKamarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ganti findViewById(R.id.main) menjadi binding.root
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 1. Inisialisasi KamarAdapter dengan data kamarList Anda
        val kamarAdapter = KamarAdapter(kamarList) { selectedKamar ->
            Toast.makeText(this, "Anda memilih ${selectedKamar.name}", Toast.LENGTH_SHORT).show()
        }

        binding.rvKamar.apply {
            /** Mode Grid (2 Kolom) **/
            layoutManager = GridLayoutManager(this@KamarActivity, 2)

            /** Jika ingin model Linear (Vertikal Tunggal), hapus komentar dibawah **/
            // layoutManager = LinearLayoutManager(this@KamarActivity)

            adapter = kamarAdapter
        }
    }
}