package com.pic.text.card.ui

import com.pic.text.card.helper.StatusBarUtil
import com.pic.text.card.BuildConfig
import com.pic.text.card.base.AbsViewModel
import com.pic.text.card.base.AppActivity
import com.pic.text.card.databinding.ActivitySettingBinding
import com.pic.text.card.helper.toast

class SettingActivity : AppActivity<ActivitySettingBinding, AbsViewModel>() {
    override fun viewModelClass() = AbsViewModel::class.java

    override fun inflate() = ActivitySettingBinding.inflate(layoutInflater)

    override fun init() {
        mBinding.apply {
            StatusBarUtil.fixStatusBar(ivClose)
            ivClose.setOnClickListener {
                finish()
            }

            tvRateUs.setOnClickListener {
                toast("rate us")
            }

            tvShare.setOnClickListener {
                toast("Share")
            }

            tvPrivacy.setOnClickListener {
                com.pic.text.card.ui.BrowserActivity.start(this@SettingActivity, "Privacy", "https://text-card.inbushtech.uk/privacy.html")
            }

            tvVersion.text = BuildConfig.VERSION_NAME
        }
    }
}