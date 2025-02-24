package com.allever.android.card.text.pic.text.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.allever.android.card.text.pic.text.R
import com.allever.android.card.text.pic.text.core.SwitchItem
import com.allever.android.card.text.pic.text.databinding.RvSwitchBinding

class SwitchItemAdapter(val data: MutableList<SwitchItem> = mutableListOf()) :
    RecyclerView.Adapter<SwitchItemAdapter.VH>() {

    var itemClick: ItemClick? = null

    class VH(val binding: RvSwitchBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = RvSwitchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val binding = holder.binding
        binding.apply {
            val item = data[position]
            tvName.text = item.name
            ivIcon.setImageResource(item.icon)

            if (item.show) {
                ivIcon.setColorFilter(ContextCompat.getColor(root.context, R.color.theme_color))
            } else {
                ivIcon.setColorFilter(ContextCompat.getColor(root.context, R.color.color_999999))
            }

            root.setOnClickListener {
                itemClick?.onItemClick(position, item)
            }
        }
    }

    interface ItemClick {
        fun onItemClick(position: Int, item: SwitchItem)
    }
}