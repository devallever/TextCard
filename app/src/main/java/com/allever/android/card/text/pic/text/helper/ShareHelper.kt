package com.allever.android.card.text.pic.text.helper

import android.app.Activity
import android.app.Service
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.allever.android.card.text.pic.text.App
import java.io.File

object ShareHelper {

    fun shareText(obj: Any, msg: String) {
        share(obj, getShareTextIntent(msg))
    }

    fun shareAudio(obj: Any, path: String) {
        share(obj, getShareAudioIntent(path))
    }

    fun shareImage(obj: Any, path: String) {
        share(obj, getShareImageIntent(path))
    }

    private fun share(obj: Any, intent: Intent) {
        try {
            when (obj) {
                is Fragment -> {
                    obj.startActivity(intent)
                }
                is android.app.Fragment -> {
                    obj.startActivity(intent)
                }
                is Activity -> {
                    obj.startActivity(intent)
                }
                is Service -> {
                    obj.startActivity(intent)
                }
                else -> {
                    toast("No app found")
                }
            }
        } catch (e: Exception) {
            toast("No app found")
            e.printStackTrace()
        }
    }

    private fun getShareTextIntent(msg: String): Intent {
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.putExtra(Intent.EXTRA_TEXT, msg)
        shareIntent.type = "text/plain"
        return Intent.createChooser(
            shareIntent,
            "Share to"
        )
    }

    private fun getShareImageIntent(path: String): Intent {
        val file = File(path)
        val fileUri: Uri
        fileUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //解决调用相册不显示图片的问题
            FileProvider.getUriForFile(App.context, "${App.context.packageName}.fileprovider", file)
        } else {
            Uri.fromFile(file)
        }

        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.putExtra(Intent.EXTRA_STREAM, fileUri)
        shareIntent.type = "image/*"
        return shareIntent
    }

    private fun getShareAudioIntent(path: String): Intent {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "audio/*"

        val file = File(path)
        val fileUri: Uri
        fileUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //解决调用相册不显示图片的问题
            FileProvider.getUriForFile(App.context, "${App.context.packageName}.fileprovider", file)
        } else {
            Uri.fromFile(file)
        }

        shareIntent.putExtra(Intent.EXTRA_STREAM, fileUri)
        return Intent.createChooser(
            shareIntent,
            "Share to"
        )
    }

}