package com.text.card.ui

import android.content.Context
import android.content.Intent
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.boat.vpn.demo.util.StatusBarUtil
import com.text.card.base.AbsViewModel
import com.text.card.base.AppActivity
import com.text.card.databinding.ActivityWebViewBinding

class BrowserActivity : AppActivity<ActivityWebViewBinding, AbsViewModel>() {
    companion object {
        fun start(context: Context, title: String, url: String) {
            val intent = Intent(context, BrowserActivity::class.java)
            intent.putExtra("title", title)
            intent.putExtra("url", url)
            context.startActivity(intent)
        }
    }

    override fun inflate() = ActivityWebViewBinding.inflate(layoutInflater)

    override fun init() {

        StatusBarUtil.fixStatusBar(mBinding.topBar)
        mBinding.ivClose.setOnClickListener {
            finish()
        }

        val url = intent?.getStringExtra("url") ?: ""
        mBinding.tvTitle.text = intent?.getStringExtra("title") ?: ""
        mBinding.webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                view?.loadUrl(url)
                return true
            }
        }
        mBinding.webView.loadUrl(url)

    }

    override fun viewModelClass() = AbsViewModel::class.java
}