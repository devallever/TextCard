package com.pic.text.card.ui.dialog

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.pic.text.card.R
import com.pic.text.card.core.DateFormat
import com.pic.text.card.core.DateTimeItem
import com.pic.text.card.core.TextCardCore
import com.pic.text.card.databinding.DialogDateTimeFormatBinding
import com.pic.text.card.helper.DisplayHelper
import com.pic.text.card.ui.adapter.DateTimeFormatAdapter

class DateTimeFormatDialog(val block: (text: String, format: String) -> Unit = { t, f -> }) :
    BottomSheetDialogFragment() {

    protected lateinit var mBinding: DialogDateTimeFormatBinding

    private val mAdapter = DateTimeFormatAdapter(TextCardCore.dateTimeFormatData).apply {


        listener = object : DateTimeFormatAdapter.Listener {
            override fun onItemClick(position: Int, item: DateTimeItem, text: String) {
                val time = System.currentTimeMillis()
                val displayText = DateFormat.format(item.format, time)
                data.map {
                    it.selected = it.format == item.format
                    it.time = time
                }

                notifyDataSetChanged()
                block.invoke(displayText, item.format)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = DialogDateTimeFormatBinding.inflate(layoutInflater)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.ivClose.setOnClickListener {
            dismiss()
        }
        mBinding.apply {
            rvDateFormat.layoutManager = LinearLayoutManager(context)
            rvDateFormat.adapter = mAdapter
        }
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog as BottomSheetDialog?
        val frameLayout =
            dialog?.delegate?.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
        frameLayout?.let {
            val layoutParams = frameLayout.layoutParams
            layoutParams.height = DisplayHelper.dip2px(500)
            val behavior = BottomSheetBehavior.from(frameLayout)
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    open fun show(manager: FragmentManager) {
        if (context is Activity) {
            if ((context as Activity).isFinishing || (context as Activity).isDestroyed) {
                return
            }
        }
        if (isAdded || manager.findFragmentByTag(TAG) != null) {
            return
        }
        super.show(manager, TAG)
    }

    companion object {
        private val TAG = DateTimeFormatDialog::class.java.simpleName
    }
}