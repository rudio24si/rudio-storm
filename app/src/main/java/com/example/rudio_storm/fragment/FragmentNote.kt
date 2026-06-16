//package com.example.rudio_storm.fragment
//
//import android.content.Intent
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.Fragment
//import androidx.lifecycle.lifecycleScope
//import androidx.recyclerview.widget.DividerItemDecoration
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.example.rudio_storm.adapter.NoteAdapter
//import com.example.rudio_storm.data.AppDatabase
//import com.example.rudio_storm.data.model.NoteEntity
//import com.example.rudio_storm.databinding.FragmentNoteBinding
//import com.example.rudio_storm.pertemuan_7.NoteFormActivity
//import kotlinx.coroutines.launch
//
//class FragmentNote : Fragment() {
//
//    private lateinit var binding: FragmentNoteBinding
//    private lateinit var adapter: NoteAdapter
//    private lateinit var db: AppDatabase
//    private val notes = mutableListOf<NoteEntity>()
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        binding = FragmentNoteBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        db = AppDatabase.getInstance(requireContext())
//        adapter = NoteAdapter(notes, this)
//
//        binding.rvNotes.layoutManager = LinearLayoutManager(requireContext())
//        binding.rvNotes.adapter = adapter
//
//        val dividerItemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
//        binding.rvNotes.addItemDecoration(dividerItemDecoration)
//
//        fetchNotes()
//
//        binding.fabAddNote.setOnClickListener {
//            startActivity(Intent(requireContext(), NoteFormActivity::class.java))
//        }
//    }
//
//    private fun fetchNotes() {
//        lifecycleScope.launch {
//            val data = db.noteDao().getAll()
//            notes.clear()
//            notes.addAll(data)
//            adapter.notifyDataSetChanged()
//        }
//    }
//
//    override fun onResume() {
//        super.onResume()
//        fetchNotes()
//    }
//
//    fun deleteNote(note: NoteEntity) {
//        lifecycleScope.launch {
//            db.noteDao().delete(note)
//            fetchNotes()
//        }
//    }
//}