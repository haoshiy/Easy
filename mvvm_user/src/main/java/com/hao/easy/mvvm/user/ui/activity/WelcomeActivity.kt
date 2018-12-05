package com.hao.easy.mvvm.user.ui.activity

import android.os.Build
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowManager
import com.hao.easy.mvvm.base.Config
import com.hao.easy.mvvm.base.extensions.gone
import com.hao.easy.mvvm.base.ui.BaseActivity
import com.hao.easy.mvvm.user.R
import com.hao.easy.mvvm.user.Router
import kotlin.concurrent.thread

class WelcomeActivity : BaseActivity() {

    companion object {
        private const val DURATION = 1500L
    }

    override fun showToolbar() = false

    override fun getLayoutId(): Int {
        return R.layout.user_activity_welcome
    }

    override fun initView() {
        super.initView()
        hideSystemNavigationBar()
    }

    override fun initData() {
        thread {
            var l = System.currentTimeMillis()
            Config.instance().init()
            var delayTime = DURATION + l - System.currentTimeMillis()
            if (delayTime <= 0) {
                start()
            } else {
                Handler(Looper.getMainLooper()).postDelayed({
                    start()
                }, delayTime)
            }
        }
    }

    private fun hideSystemNavigationBar() {
        if (Build.VERSION.SDK_INT in 12..18) {
            window.decorView.gone()
        } else if (Build.VERSION.SDK_INT >= 19) {
            var uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                    View.SYSTEM_UI_FLAG_FULLSCREEN
            window.decorView.systemUiVisibility = uiOptions
        }
    }

    private fun start() {
        Router.startMainActivity()
        finish()
    }

    override fun finish() {
        window.addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN)
        super.finish()
    }
}
