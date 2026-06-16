package com.example.rudio_storm.pertemuan_7

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rudio_storm.KamarActivity
import com.example.rudio_storm.adapter.KamarAdapter
import com.example.rudio_storm.adapter.NoteAdapter
import com.example.rudio_storm.adapter.PhotoAdapter
import com.example.rudio_storm.data.AppDatabase
import com.example.rudio_storm.data.api.PhotoApiClient
import com.example.rudio_storm.data.model.NoteEntity
import com.example.rudio_storm.databinding.FragmentHomeBinding
import com.example.rudio_storm.pertemuan_2.SecondActivity
import com.example.rudio_storm.pertemuan_4.Custom1Activity
import com.example.rudio_storm.pertemuan_4.Custom2Activity
import com.google.android.material.chip.Chip
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    // Note
    private lateinit var db: AppDatabase
    private lateinit var noteAdapter: NoteAdapter
    private val notes = mutableListOf<NoteEntity>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Navigasi tombol menu
        binding.btnRumus.setOnClickListener {
            startActivity(Intent(requireContext(), SecondActivity::class.java))
        }
        binding.btnCustom1.setOnClickListener {
            startActivity(Intent(requireContext(), Custom1Activity::class.java))
        }
        binding.btnCustom2.setOnClickListener {
            startActivity(Intent(requireContext(), Custom2Activity::class.java))
        }
        binding.btnCustom3.setOnClickListener {
            startActivity(Intent(requireContext(), KamarActivity::class.java))
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

        loadPhoto()
        setupNote()
    }

    private fun setupNote() {
        db = AppDatabase.getInstance(requireContext())
        noteAdapter = NoteAdapter(notes, this)

        binding.rvNotes.layoutManager = LinearLayoutManager(requireContext())
        binding.rvNotes.adapter = noteAdapter

        binding.fabAddNote.setOnClickListener {
            startActivity(Intent(requireContext(), NoteFormActivity::class.java))
        }

        fetchNotes()
    }

    private fun fetchNotes() {
        lifecycleScope.launch {
            val data = db.noteDao().getAll()
            notes.clear()
            notes.addAll(data)
            noteAdapter.notifyDataSetChanged()
        }
    }

    override fun onResume() {
        super.onResume()
        if (::noteAdapter.isInitialized) {
            fetchNotes()
        }
    }

    fun deleteNote(note: NoteEntity) {
        lifecycleScope.launch {
            db.noteDao().delete(note)
            fetchNotes()
        }
    }

    private fun loadPhoto() {
        lifecycleScope.launch {
            try {
                val photos = PhotoApiClient.apiService.getPhotos()
                val adapter = PhotoAdapter(photos)
                binding.rvGallery.adapter = adapter
                binding.rvGallery.layoutManager = GridLayoutManager(requireContext(), 2)
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Gagal memuat gambar", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showLogoutConfirmation() {
        val builder = androidx.appcompat.app.AlertDialog.Builder(requireContext())
        builder.setTitle("Konfirmasi Logout")
        builder.setMessage("Apakah Anda yakin ingin keluar dari aplikasi?")
        builder.setPositiveButton("Ya") { _, _ ->
            val intent = Intent(requireContext(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            requireActivity().finish()
        }
        builder.setNegativeButton("Batal") { dialog, _ ->
            dialog.dismiss()
        }
        builder.create().show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}