package com.yimu.uireview.library;


import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;


/**
 * Created by linwei on 15-8-19.
 */
public class UiReview {

    private Handler mHandler;
    private TraverseTask mTask;
    private long mInterval = 1000L;
    private ArrayList<Scanner> mScanners = new ArrayList<>();
    private ViewGroup mRootView;
    private static final String TAG = UiReview.class.getSimpleName();

    private UiReview() {
        mScanners.clear();
        mTask = new TraverseTask();
        mHandler = new Handler(Looper.getMainLooper());
        //or register your scanners
        //register(new TextViewScanner());
    }

    private volatile static UiReview instance;

    public static UiReview getInstance() {
        if (instance == null) {
            synchronized (UiReview.class) {
                if (instance == null) {
                    instance = new UiReview();
                }
            }
        }
        return instance;
    }

    public synchronized void resume(Activity activity) {
        View view = activity.findViewById(android.R.id.content);
        if (null == view || !ViewGroup.class.isInstance(view)) {
            Log.d(TAG, "Error:Can not find root view!");
            return;
        }
        mRootView = (ViewGroup) view;
        for (Scanner scanner : mScanners) {
            scanner.init(activity);
        }
        mHandler.postDelayed(mTask, mInterval);
    }

    public synchronized void pause() {
        mHandler.removeCallbacks(mTask);
    }

    public synchronized UiReview register(Scanner scanner) {
        if (scanner != null) {
            mScanners.add(scanner);
        }
        return this;
    }

    public synchronized UiReview unregister(Class<?> type) {
        for (Scanner scanner : mScanners) {
            if (type.isInstance(scanner)) {
                mScanners.remove(scanner);
            }
        }
        return this;
    }

    public UiReview setInterval(long interval) {
        this.mInterval = interval;
        return this;
    }

    class TraverseTask implements Runnable {
        @Override
        public void run() {
            // traverse
            long start = android.os.SystemClock.uptimeMillis();
            traverse(mRootView);
            Log.d(TAG, "Traverse execution time: " +
                    (android.os.SystemClock.uptimeMillis() - start) + "ms");
            mHandler.postDelayed(mTask, mInterval);
        }

        private void traverse(ViewGroup root) {
            if(root == null){
                return;
            }
            final int childCount = root.getChildCount();
            for (int i = 0; i < childCount; ++i) {
                final View child = root.getChildAt(i);
                for (Scanner scanner : mScanners) {
                    if (scanner.filter(child)) {
                        scanner.handle(child);
                    }
                }
                if (child instanceof ViewGroup) {
                    traverse((ViewGroup) child);
                }
            }
        }
    }

    public interface Scanner {
        void init(Context context);

        boolean filter(View view);

        void handle(View view);
    }

}
