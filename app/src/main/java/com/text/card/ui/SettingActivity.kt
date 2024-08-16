package com.text.card.ui

import com.boat.vpn.demo.util.StatusBarUtil
import com.text.card.BuildConfig
import com.text.card.base.AbsViewModel
import com.text.card.base.AppActivity
import com.text.card.databinding.ActivitySettingBinding
import com.text.card.helper.toast

class SettingActivity: AppActivity<ActivitySettingBinding, AbsViewModel>() {
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
                BrowserActivity.start(this@SettingActivity, "Privacy", "https://www.google.com/")
            }

            tvVersion.text = BuildConfig.VERSION_NAME
        }
    }
}