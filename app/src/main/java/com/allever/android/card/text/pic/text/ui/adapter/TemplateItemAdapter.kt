package com.allever.android.card.text.pic.text.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.allever.android.card.text.pic.text.App
import com.allever.android.card.text.pic.text.R
import com.allever.android.card.text.pic.text.core.TemplateModel
import com.allever.android.card.text.pic.text.databinding.RvTemplateBinding
import com.allever.android.card.text.pic.text.helper.log

class TemplateItemAdapter(val data: MutableList<TemplateModel<*>> = mutableListOf()) :
    RecyclerView.Adapter<TemplateItemAdapter.VH>() {


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
            tvName.setTextColor(App.getColor(if (item.selected) R.color.theme_color else R.color.white))
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