package com.example.rudio_storm.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rudio_storm.data.model.NoteEntity
import com.example.rudio_storm.databinding.ItemNoteBinding
import com.example.rudio_storm.pertemuan_7.HomeFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class NoteAdapter(
    private val notes: List<NoteEntity>,
    private val homeFragment: HomeFragment
) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    inner class NoteViewHolder(val binding: ItemNoteBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.binding.tvTitle.text = note.title
        holder.binding.tvContent.text = note.content

        holder.binding.btnDelete.setOnClickListener {
            MaterialAlertDialogBuilder(holder.itemView.context)
                .setTitle("Hapus Catatan")
                .setMessage("Apakah kamu yakin ingin menghapus catatan ini?")
                .setPositiveButton("Ya") { dialog, _ ->
                    homeFragment.deleteNote(note)
                    dialog.dismiss()
                }
                .setNegativeButton("Batal") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }

    override fun getItemCount(): Int = notes.size
}