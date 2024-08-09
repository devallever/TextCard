package com.text.card.ui

import android.content.Context
import android.graphics.Color
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.boat.vpn.demo.util.StatusBarUtil
import com.text.card.R
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

            KeyboardUtils.SoftKeyboardListener.setListener(this@EditActivity, object : OnSoftKeyboardChangeListener {
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

            btnEditStyle.setOnClickListener {
                menuContainer.isVisible = !menuContainer.isVisible
                if (menuContainer.isVisible) {
                    btnEditStyle.setCardBackgroundColor(ContextCompat.getColor(this@EditActivity, R.color.page_bg))
                    tvEditStyle.visibility = View.INVISIBLE
                    ivBtnEditStyleArrow.animate().rotation(-180f).start()
                } else {
                    btnEditStyle.setCardBackgroundColor(ContextCompat.getColor(this@EditActivity, R.color.color_252525))
                    tvEditStyle.visibility = View.VISIBLE
                    ivBtnEditStyleArrow.animate().rotation(0f).start()
                }

            }

            val selectTextColor = ContextCompat.getColor(this@EditActivity, R.color.white)
            val unSelectColor = ContextCompat.getColor(this@EditActivity, R.color.color_999999)
            btnTemplate.setOnClickListener {
                ivTemplate.isVisible = true
                tvTemplate.setTextColor(selectTextColor)
                btnTemplate.background = ContextCompat.getDrawable(this@EditActivity, R.drawable.shape_edit_page_btn_bg)
                templateContent.isVisible = true

                ivBgColor.isVisible = false
                tvBgColor.setTextColor(unSelectColor)
                btnBgColor.background = null
                bgColorContent.isVisible = false

                ivSwitch.isVisible = false
                tvSwitch.setTextColor(unSelectColor)
                btnSwitch.background = null
                switchContent.isVisible = false
            }

            btnBgColor.setOnClickListener {
                ivTemplate.isVisible = false
                tvTemplate.setTextColor(unSelectColor)
                btnTemplate.background = null
                templateContent.isVisible = false

                ivBgColor.isVisible = true
                tvBgColor.setTextColor(selectTextColor)
                btnBgColor.background = ContextCompat.getDrawable(this@EditActivity, R.drawable.shape_edit_page_btn_bg)
                bgColorContent.isVisible = true

                ivSwitch.isVisible = false
                tvSwitch.setTextColor(unSelectColor)
                btnSwitch.background = null
                switchContent.isVisible = false
            }

            btnSwitch.setOnClickListener {
                ivTemplate.isVisible = false
                tvTemplate.setTextColor(unSelectColor)
                btnTemplate.background = null
                templateContent.isVisible = false

                ivBgColor.isVisible = false
                tvBgColor.setTextColor(unSelectColor)
                btnBgColor.background = null
                bgColorContent.isVisible = false

                ivSwitch.isVisible = true
                tvSwitch.setTextColor(selectTextColor)
                btnSwitch.background = ContextCompat.getDrawable(this@EditActivity, R.drawable.shape_edit_page_btn_bg)
                switchContent.isVisible = true
            }
        }
    }
}