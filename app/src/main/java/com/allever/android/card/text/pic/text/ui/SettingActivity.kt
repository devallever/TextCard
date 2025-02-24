package com.allever.android.card.text.pic.text.ui

import com.allever.android.card.text.pic.text.helper.StatusBarUtil
import com.allever.android.card.text.pic.text.BuildConfig
import com.allever.android.card.text.pic.text.base.AbsViewModel
import com.allever.android.card.text.pic.text.base.AppActivity
import com.allever.android.card.text.pic.text.databinding.ActivitySettingBinding
import com.allever.android.card.text.pic.text.helper.toast

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
                com.allever.android.card.text.pic.text.ui.BrowserActivity.start(this@SettingActivity, "Privacy", "https://text-card.inbushtech.uk/privacy.html")
            }

            tvVersion.text = BuildConfig.VERSION_NAME
        }
    }
}