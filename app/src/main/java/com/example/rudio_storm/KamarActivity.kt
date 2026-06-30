package com.example.rudio_storm

import android.Manifest
import android.app.AlertDialog
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.rudio_storm.adapter.KamarAdapter
import com.example.rudio_storm.data.AppDatabase
import com.example.rudio_storm.data.model.KamarEntity
import com.example.rudio_storm.databinding.ActivityKamarBinding
import com.example.rudio_storm.utils.PermissionHelper
import com.example.rudio_storm.utils.ReminderHelper
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch

class KamarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityKamarBinding
    private lateinit var db: AppDatabase
    private lateinit var kamarAdapter: KamarAdapter
    private val kamarList = mutableListOf<KamarEntity>()

    // Launcher untuk meminta izin notifikasi (wajib di Android 13 / Tiramisu ke atas)
    private val notificationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            val pesan = if (isGranted) "Notifikasi diizinkan" else "Notifikasi ditolak"
            Toast.makeText(this, pesan, Toast.LENGTH_SHORT).show()
        }

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

        // Minta izin notifikasi saat halaman dibuka
        android.util.Log.d("PermissionDebug", "isRequired=${PermissionHelper.isNotificationPermissionRequired()}, sdkInt=${android.os.Build.VERSION.SDK_INT}")
        if (PermissionHelper.isNotificationPermissionRequired()) {
            val permission = Manifest.permission.POST_NOTIFICATIONS
            val sudahPunyaIzin = PermissionHelper.hasPermission(this, permission)
            android.util.Log.d("PermissionDebug", "sudahPunyaIzin=$sudahPunyaIzin")
            if (!sudahPunyaIzin) {
                android.util.Log.d("PermissionDebug", "Memanggil requestPermission...")
                PermissionHelper.requestPermission(notificationPermissionLauncher, permission)
            }
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

        // SEMENTARA untuk testing reminder: long-press fabAddKamar
        // akan memunculkan dialog input menit reminder, pakai data dummy.
        // Nanti setelah KamarAdapter disambungkan, ini bisa dihapus
        // dan diganti trigger per-item (misal long-click item kamar).
        binding.fabAddKamar.setOnLongClickListener {
            val dummyKamar = KamarEntity(name = "Kamar Test", price = "Rp 0", imageUrl = "")
            showSetReminderDialog(dummyKamar)
            true
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

    /**
     * Studi kasus: reminder "masa sewa kamar akan segera berakhir".
     * Dipanggil dari KamarAdapter, misal lewat tombol/ikon "Set Reminder" atau long-click
     * pada item kamar, dengan mengirim objek KamarEntity yang bersangkutan.
     *
     * Contoh pemanggilan dari KamarAdapter:
     *   itemView.setOnLongClickListener {
     *       (activity as KamarActivity).showSetReminderDialog(kamar)
     *       true
     *   }
     */
    fun showSetReminderDialog(kamar: KamarEntity) {
        val input = EditText(this).apply {
            hint = "Contoh: 30"
            inputType = android.text.InputType.TYPE_CLASS_NUMBER
        }

        AlertDialog.Builder(this)
            .setTitle("Atur Reminder Sewa Kamar")
            .setMessage("Ingatkan saya berapa menit lagi sebelum masa sewa \"${kamar.name}\" berakhir?")
            .setView(input)
            .setPositiveButton("Set Reminder") { _, _ ->
                val minutes = input.text.toString().toIntOrNull()

                if (minutes == null || minutes <= 0) {
                    Toast.makeText(this, "Masukkan jumlah menit yang valid", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                if (PermissionHelper.isNotificationPermissionRequired() &&
                    !PermissionHelper.hasPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                ) {
                    Toast.makeText(
                        this,
                        "Izinkan notifikasi terlebih dahulu agar reminder bisa muncul",
                        Toast.LENGTH_LONG
                    ).show()
                    return@setPositiveButton
                }

                // Notifikasi yang muncul nanti akan mengarah kembali ke KamarActivity saat diklik
                ReminderHelper.setReminderInMinutes(
                    context = this,
                    minutesFromNow = minutes,
                    title = "Sewa Kamar Akan Berakhir",
                    message = "Masa sewa kamar \"${kamar.name}\" akan berakhir sebentar lagi. Segera lakukan perpanjangan!",
                    targetActivity = KamarActivity::class.java,
                    requestCode = kamar.name.hashCode()
                )

                Toast.makeText(
                    this,
                    "Reminder diatur, akan muncul dalam $minutes menit",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .setNegativeButton("Batal", null)
            .show()
    }
}git