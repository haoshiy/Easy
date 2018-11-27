package com.hao.easy.mvvm.inject.module

import android.app.Activity
import android.content.Context
import android.support.v4.app.Fragment
import com.hao.easy.mvvm.inject.ActivityContext
import dagger.Module
import dagger.Provides

/**
 * @author Yang Shihao
 * @date 2018/10/23
 */
@Module
class FragmentCommonModule(private val fragment: Fragment) {

    @Provides
    internal fun provideFragment(): Fragment {
        return fragment
    }

    @Provides
    internal fun provideActivity(): Activity? {
        return fragment.activity
    }

    @Provides
    @ActivityContext
    internal fun provideContext(): Context? {
        return fragment.context
    }
}