package com.tydk.uilibrary.utils;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ScrollView;

/**
 * @author: zzs
 * @date: 2019/7/31 0031 下午 2:52
 * @description: 软键盘工具类
 */
public class KeyboardUtils {
    private static Handler mHandler = new Handler();

    /**
     * @method: setGlobalListener
     * @date: 2019-07-31 下午 2:56
     * @param: @Activity(当前activity)  @View(需要移动的布局，一般为根布局)
     * @return:
     * @description: 解决软键盘遮挡输入框问题，动态监控软键盘，控制布局移动
     */
    public static void setGlobalListener(final Activity activity, final View view) {
        final View rootView = activity.getWindow().getDecorView().findViewById(android.R.id.content);
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                final int softKeyboardHeight = 140;
                Rect r = new Rect();
                rootView.getWindowVisibleDisplayFrame(r);
                DisplayMetrics dm = rootView.getResources().getDisplayMetrics();
                int heightDiff = rootView.getBottom() - r.bottom;
                FrameLayout frameLayout = activity.getWindow().getDecorView().findViewById(android.R.id.content);
                View mChildView = frameLayout.getChildAt(0);
                if (heightDiff > softKeyboardHeight * dm.density) {
                    //键盘弹出
                    mChildView.setPadding(0, 0, 0, heightDiff);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            view.scrollTo(0, softKeyboardHeight);
                        }
                    });
                } else {
                    //键盘收起
                    mChildView.setPadding(0, 0, 0, 0);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            view.scrollTo(0, 0);
                        }
                    });
                }
                //如果只想检测一次，需要注销
                //rootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    public static void setScrollGlobalListener(final Activity activity, final ScrollView scrollView, final View view) {
        final View rootView = activity.getWindow().getDecorView().findViewById(android.R.id.content);
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                final int softKeyboardHeight = 140;
                Rect r = new Rect();
                rootView.getWindowVisibleDisplayFrame(r);
                DisplayMetrics dm = rootView.getResources().getDisplayMetrics();
                int heightDiff = rootView.getBottom() - r.bottom;
                FrameLayout frameLayout = activity.getWindow().getDecorView().findViewById(android.R.id.content);
                View mChildView = frameLayout.getChildAt(0);
                if (heightDiff > softKeyboardHeight * dm.density) {
                    //键盘弹出
                    mChildView.setPadding(0, 0, 0, heightDiff);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (rootView.getBottom() / 2 < view.getBottom()) {
                                scrollView.scrollTo(0, view.getBottom());
                            }
                        }
                    });
                } else {
                    //键盘收起
                    mChildView.setPadding(0, 0, 0, 0);
                }
                //如果只想检测一次，需要注销
                //rootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }
}
