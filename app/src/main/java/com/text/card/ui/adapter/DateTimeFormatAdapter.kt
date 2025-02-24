package com.text.card.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.text.card.core.DateFormat
import com.text.card.core.DateTimeItem
import com.text.card.databinding.RvDateTimeFormatBinding

class DateTimeFormatAdapter(val data: MutableList<DateTimeItem>): RecyclerView.Adapter<DateTimeFormatAdapter.VH>(){


    var listener: Listener? = null

    class VH(val binding: RvDateTimeFormatBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val bindng = RvDateTimeFormatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(bindng)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.binding.apply {
            val item = data[position]
            val text = DateFormat.format(item.format, item.time)
            tvTime.text = text
            ivSelect.isVisible = item.selected
            root.setOnClickListener {
                listener?.onItemClick(position, item, text)
            }
        }
    }

    interface Listener {
        fun onItemClick(position: Int, item: DateTimeItem, text: String)
    }
}