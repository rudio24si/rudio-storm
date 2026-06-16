package com.example.rudio_storm.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rudio_storm.KamarActivity
import com.example.rudio_storm.databinding.ItemKamarBinding
import com.example.rudio_storm.data.model.KamarEntity
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class KamarAdapter(
    private val kamarList: List<KamarEntity>,
    private val kamarActivity: KamarActivity
) : RecyclerView.Adapter<KamarAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(val binding: ItemKamarBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemKamarBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val item = kamarList[position]
        with(holder.binding) {
            namaKamar.text = item.name
            hargaKamar.text = item.price

            Glide.with(holder.itemView.context)
                .load(item.imageUrl)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .into(imgKamar)

            root.setOnClickListener {
                MaterialAlertDialogBuilder(holder.itemView.context)
                    .setTitle("Hapus Kamar")
                    .setMessage("Hapus kamar \"${item.name}\"?")
                    .setPositiveButton("Hapus") { dialog, _ ->
                        kamarActivity.deleteKamar(item)
                        dialog.dismiss()
                    }
                    .setNegativeButton("Batal") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            }
        }
    }

    override fun getItemCount(): Int = kamarList.size
}