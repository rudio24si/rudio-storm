package com.example.rudio_storm.pertemuan_7

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.rudio_storm.KamarActivity
import com.example.rudio_storm.adapter.KamarAdapter
import com.example.rudio_storm.databinding.FragmentHomeBinding
import com.example.rudio_storm.pertemuan_2.SecondActivity
import com.example.rudio_storm.pertemuan_4.Custom1Activity
import com.example.rudio_storm.pertemuan_4.Custom2Activity
import com.google.android.material.chip.Chip

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inisialisasi Binding
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Navigasi ke Activity (Jika masih ingin pindah Activity)
        binding.btnRumus.setOnClickListener {
            val intent = Intent(requireContext(), SecondActivity::class.java)
            startActivity(intent)
        }

        binding.btnCustom1.setOnClickListener {
            val intent = Intent(requireContext(), Custom1Activity::class.java)
            startActivity(intent)
        }

        binding.btnCustom2.setOnClickListener {
            val intent = Intent(requireContext(), Custom2Activity::class.java)
            startActivity(intent)
        }

        binding.btnCustom3.setOnClickListener {
            val intent = Intent(requireContext(), KamarActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogout.setOnClickListener {
            showLogoutConfirmation()
        }

        binding.chipGroupFilter.setOnCheckedStateChangeListener { group, checkedIds ->
            val selectedChipId = checkedIds.firstOrNull()
            if (selectedChipId != null) {
                val chip = group.findViewById<Chip>(selectedChipId)
                Toast.makeText(requireContext(), "Filter: ${chip.text}", Toast.LENGTH_SHORT).show()
            }
        }

        // Catatan: Jika ingin pindah antar FRAGMENT, gunakan FragmentManager
        // Contoh: parentFragmentManager.beginTransaction().replace(R.id.container, ProfileFragment()).commit()
    }

    private fun showLogoutConfirmation() {
        val builder = androidx.appcompat.app.AlertDialog.Builder(requireContext())
        builder.setTitle("Konfirmasi Logout")
        builder.setMessage("Apakah Anda yakin ingin keluar dari aplikasi?")

        // Jika user memilih "Ya"
        builder.setPositiveButton("Ya") { _, _ ->
            val intent = Intent(requireContext(), LoginActivity::class.java)
            // Bersihkan stack activity agar tidak bisa back ke Home
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            requireActivity().finish()
        }

        // Jika user memilih "Batal"
        builder.setNegativeButton("Batal") { dialog, _ ->
            dialog.dismiss()
        }

        // Membuat dan menampilkan dialog
        val alertDialog = builder.create()
        alertDialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}