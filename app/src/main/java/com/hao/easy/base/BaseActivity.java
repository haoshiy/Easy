package com.hao.easy.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.hao.easy.utils.T;
import com.noober.background.BackgroundLibrary;
import com.socks.library.KLog;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Yang Shihao
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected Unbinder unbinder;
    protected String TAG;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        BackgroundLibrary.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        unbinder = ButterKnife.bind(this);
        TAG = getClass().getSimpleName();
        KLog.d(TAG, "onCreate: " + TAG);
        setTitle(TAG);
        initView();
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    protected abstract @LayoutRes
    int getLayoutId();

    protected abstract void initView();

    protected abstract void initData();

    protected void toast(String s) {
        T.showShort(this, s);
    }

    protected void startActivity(Class cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    protected <T extends View> T $(@IdRes int resId) {
        return (T) super.findViewById(resId);
    }

    protected <T extends View> T $(View layoutView, @IdRes int resId) {
        return (T) layoutView.findViewById(resId);
    }
}
