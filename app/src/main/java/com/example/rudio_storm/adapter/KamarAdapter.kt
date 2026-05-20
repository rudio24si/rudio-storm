package com.example.rudio_storm.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rudio_storm.databinding.ItemKamarBinding
import com.example.rudio_storm.model.KamarModel

class KamarAdapter(
    private val kamarList: List<KamarModel>,
    private val onItemClick: (KamarModel) -> Unit
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
                .into(imgKamar)

            root.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    override fun getItemCount(): Int = kamarList.size
}