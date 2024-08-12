package com.text.card.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.text.card.core.ColorData
import com.text.card.core.TemplateManager
import com.text.card.databinding.FragmentColorBinding
import com.text.card.ui.adapter.ColorAdapter

class ColorFragment: Fragment() {
    private lateinit var mBinding: FragmentColorBinding
    private val colorDataList = mutableListOf<ColorData>()

    private val colorAdapter by lazy {
        ColorAdapter().apply {
            data.clear()
            data.addAll(colorDataList)

            itemClick = object : ColorAdapter.ItemClick {
                override fun onItemClick(position: Int, item: ColorData) {
                    data.map {
                        it.selected = false
                        if (it.name == item.name) {
                            it.selected = true
                        }
                    }
                    notifyDataSetChanged()
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentColorBinding.inflate(layoutInflater, container, false)
        mBinding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 8)
        mBinding.recyclerView.adapter = colorAdapter
        return mBinding.root
    }

    fun updateData(colorData: MutableList<ColorData>) {
        colorAdapter.data.clear()
        colorAdapter.data.addAll(colorData)
        colorAdapter.notifyDataSetChanged()
    }

    private fun updateSelectColor() {

    }
}