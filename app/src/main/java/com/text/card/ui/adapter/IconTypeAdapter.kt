package com.text.card.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.text.card.App
import com.text.card.core.IconData
import com.text.card.core.IconTypeData
import com.text.card.databinding.RvIconTeypeBinding

class IconTypeAdapter(val data: MutableList<IconTypeData>) :
    RecyclerView.Adapter<IconTypeAdapter.VH>() {

    var iconTypeListener: Listener? = null

    class VH(val binding: RvIconTeypeBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = RvIconTeypeBinding.inflate(LayoutInflater.from(App.context), parent, false)
        return VH(binding)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: VH, @SuppressLint("RecyclerView") position: Int) {
        val binding = holder.binding
        binding.apply {
            val typeItem = data[position]
            rvIcon.layoutManager = GridLayoutManager(root.context, 7)
            rvIcon.adapter = IconAdapter(typeItem.iconList).apply {
                listener = object : IconAdapter.Listener {
                    override fun onItemClick(iconPosition: Int, item: IconData) {
                        iconTypeListener?.onItemSelect(position, iconPosition, typeItem)
                    }
                }
            }
        }
    }

    interface Listener {
        fun onItemSelect(typePosition: Int, iconPosition: Int, item: IconTypeData)
    }
}