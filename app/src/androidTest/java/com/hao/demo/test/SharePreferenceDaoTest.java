package com.hao.demo.test;

import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.hao.demo.App;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * @author Yang Shihao
 */
@RunWith(AndroidJUnit4.class)
public class SharePreferenceDaoTest {

    private static final String TAG = "SharePreferenceDaoTest";

    private static final String KEY = "PRE_KEY";

    SharePreferenceDao mDao;

    @Before
    public void setUp() {
        mDao = new SharePreferenceDao(App.getInstance());
    }

    @Test
    public void sharePreferenceDaoWriteRead() {
        mDao.put(KEY, "123");
        String s = mDao.get(KEY);
        System.out.println(s);
    }
}