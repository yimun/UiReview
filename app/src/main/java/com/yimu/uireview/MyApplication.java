package com.yimu.uireview;

import android.app.Application;

import com.yimu.uireview.library.TextViewScanner;
import com.yimu.uireview.library.UiReview;

/**
 * Created by linwei on 15-8-19.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        UiReview.getInstance().register(
                new TextViewScanner());
    }
}
