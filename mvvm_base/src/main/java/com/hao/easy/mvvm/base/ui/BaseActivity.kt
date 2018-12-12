package com.hao.easy.mvvm.base.ui

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.view.View
import com.hao.easy.mvvm.base.R
import com.hao.easy.mvvm.base.common.AppManager
import com.hao.easy.mvvm.base.di.android.DIActivity
import com.hao.easy.mvvm.view.ToolbarLayout
import kotlinx.android.synthetic.main.activity_base.*

/**
 * @author Yang Shihao
 * @date 2018/11/18
 */
abstract class BaseActivity : DIActivity() {

    private var toolbar: ToolbarLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        //BackgroundLibrary.inject(this)
        super.onCreate(savedInstanceState)
        AppManager.instance().pushActivity(this)
        when {
            getLayoutId() == 0 -> setContentView()

            !showToolbar() -> setContentView(getLayoutId())

            else -> {
                setContentView(R.layout.activity_base)
                View.inflate(this, getLayoutId(), activityRootView)
            }
        }
        toolbar = findViewById(R.id.baseToolbar)
        toolbar?.apply {
            setBackClickListener {
                onBackPressed()
            }
        }
        onInit()
        initInject()
        initView()
        initData()
    }

    override fun onDestroy() {
        super.onDestroy()
        AppManager.instance().popActivity(this)
    }

    open fun onInit() {
    }

    open fun showToolbar() = true

    @LayoutRes
    abstract fun getLayoutId(): Int

    open fun setContentView() {
    }

    open fun initInject() {
    }

    open fun initView() {
    }

    open fun initData() {
    }

    override fun setTitle(title: CharSequence?) {
        toolbar?.title = title
    }

    fun <T : View> f(id: Int): T? {
        return findViewById(id)
    }
}