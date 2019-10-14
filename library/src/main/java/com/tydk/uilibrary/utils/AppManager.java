package com.tydk.uilibrary.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.util.Iterator;
import java.util.Stack;

/**
 * @author: zzs
 * @date: 2019-05-28 下午 3:09
 * @description: Activity管理类
*/
public class AppManager {
    private static Stack<Activity> activityStack;
    private static AppManager instance;

    private AppManager() {
    }

    public static AppManager getAppManager() {
        if (instance == null) {
            instance = new AppManager();
        }
        return instance;
    }

    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack();
        }
        activityStack.add(activity);
    }

    public Activity currentActivity() {
        return (Activity) activityStack.lastElement();
    }

    public void finishActivity() {
        Activity activity = (Activity) activityStack.lastElement();
        if (activity != null) {
            activity.finish();
        }
    }

    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
        }
    }

    public void finishActivity(Class<?> cls) {
        Iterator it = activityStack.iterator();
        while (it.hasNext()) {
            Activity activity = (Activity) it.next();
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    public void finishAllActivity() {
        int size = activityStack.size();
        for (int i = 0; i < size; i++) {
            if (activityStack.get(i) != null) {
                ((Activity) activityStack.get(i)).finish();
            }
        }
        activityStack.clear();
    }

    public void AppExit(Context context) {
        try {
            finishAllActivity();
            ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).restartPackage(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
