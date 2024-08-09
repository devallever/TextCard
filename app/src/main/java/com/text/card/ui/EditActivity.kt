package com.text.card.ui

import android.view.View
import androidx.core.view.isVisible
import com.boat.vpn.demo.util.StatusBarUtil
import com.text.card.base.AppActivity
import com.text.card.databinding.ActivityEditBinding
import com.text.card.databinding.TemplateMediaBinding
import com.text.card.helper.DisplayHelper
import com.text.card.helper.KeyboardUtils
import com.text.card.helper.KeyboardUtils.SoftKeyboardListener.OnSoftKeyboardChangeListener
import com.text.card.helper.KeyboardVisibilityListener
import com.text.card.helper.ViewHelper
import com.text.card.viewmodel.EditViewMode

class EditActivity : AppActivity<ActivityEditBinding, EditViewMode>() {

    private lateinit var keyboardListener: KeyboardVisibilityListener

    private lateinit var rootView: View


    override fun viewModelClass() = EditViewMode::class.java

    override fun inflate() = ActivityEditBinding.inflate(layoutInflater)

    override fun init() {
        rootView = this.findViewById(android.R.id.content)

        mBinding.apply {
            StatusBarUtil.fixStatusBar(toolBar)
            val tempMediaBinding = TemplateMediaBinding.inflate(layoutInflater)
            contentContainer.removeAllViews()
            contentContainer.addView(tempMediaBinding.root)

            scrollView.post {
                scrollView.post {
                    ViewHelper.setMarginTop(
                        contentContainer,
                        StatusBarUtil.getStatusBarHeight(this@EditActivity) + DisplayHelper.dip2px(
                            54 + 10
                        )
                    )
                }
            }

            KeyboardUtils.SoftKeyboardListener.setListener(
                this@EditActivity,
                object : OnSoftKeyboardChangeListener {
                    override fun hide(height: Int) {
                        btnDone.isVisible = false
                        btnExport.isVisible = true
                        ivClearText.isVisible = true
                    }

                    override fun show(height: Int) {
                        btnDone.isVisible = true
                        btnExport.isVisible = false
                        ivClearText.isVisible = false
                    }
                })
        }

        initClickListener()

    }

    private fun initClickListener() {
        mBinding.apply {
            btnDone.setOnClickListener {
                KeyboardUtils.hideInput(this@EditActivity)
            }
        }
    }
}