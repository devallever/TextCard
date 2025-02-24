package com.text.card.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.text.card.App
import com.text.card.R
import com.text.card.core.TemplateModel
import com.text.card.databinding.RvTemplateBinding
import com.text.card.helper.log

class TemplateItemAdapter(val data: MutableList<TemplateModel<*>> = mutableListOf()): RecyclerView.Adapter<TemplateItemAdapter.VH>(){


    var itemClick: ItemClick? = null

    class VH(val binding: RvTemplateBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = RvTemplateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.binding.apply {
            val item = data[position]
            ivIcon.setImageResource(item.cover)
            tvName.text = item.getTemplateName()
            log("${item.getTemplateName()} is selected = ${item.selected}")
            bgFrame.isVisible = item.selected

            root.setOnClickListener {
                itemClick?.onItemClick(position, item)
            }
        }
    }

    interface ItemClick {
        fun onItemClick(position: Int, item: TemplateModel<*>)
    }
}