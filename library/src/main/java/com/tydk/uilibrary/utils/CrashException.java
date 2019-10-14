package com.tydk.uilibrary.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.os.Process;
import android.util.Log;
import android.widget.Toast;

import com.tydk.uilibrary.App;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: zzs
 * @date: 2019-05-28 下午 3:08
 * @description: 自定义异常捕获类
*/
public class CrashException implements UncaughtExceptionHandler {
    private static CrashException INSTANCE = new CrashException();
    public static final String TAG = "CrashException";
    private Map<String, String> infos = new HashMap();
    private UncaughtExceptionHandler mDefaultHandler;
    public static final String SD = (Environment.getExternalStorageDirectory() + "/com.tydic.mymvp/");

    private CrashException() {
    }

    public static CrashException getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CrashException();
            INSTANCE.init();
        }
        return INSTANCE;
    }

    public void init() {
        this.mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    public void uncaughtException(Thread thread, Throwable ex) {
        if (handleException(ex) || this.mDefaultHandler == null) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Log.e(TAG, e.getMessage());
            }
            Process.killProcess(Process.myPid());
            System.exit(1);
            return;
        }
        this.mDefaultHandler.uncaughtException(thread, ex);
    }

    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        new Thread() {
            public void run() {
                Looper.prepare();
                Toast.makeText(App.getInstance(), "很抱歉,程序出现异常,即将退出.", Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }.start();
        saveLogFile(ex);
        return true;
    }

    private void saveLogFile(Throwable ex) {
        try {
            File file = new File(SD);
            if (!file.exists()) {
                file.mkdirs();
            }
            FileOutputStream fout = new FileOutputStream(new File(SD + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".txt"), true);
            fout.write((">>>>时间：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "\r\n").getBytes("utf-8"));
            fout.write(("信息：" + ex.getMessage()).getBytes("utf-8"));
            for (int i = 0; i < ex.getStackTrace().length; i++) {
                fout.write(("****StackTrace" + i + "\r\n").getBytes("utf-8"));
                fout.write(("行数：" + ex.getStackTrace()[i].getLineNumber() + "\r\n").getBytes("utf-8"));
                fout.write(("类名：" + ex.getStackTrace()[i].getClassName() + "\r\n").getBytes("utf-8"));
                fout.write(("文件：" + ex.getStackTrace()[i].getFileName() + "\r\n").getBytes("utf-8"));
                fout.write(("方法：" + ex.getStackTrace()[i].getMethodName() + "\r\n\r\n").getBytes("utf-8"));
            }
            fout.write("--------------------------------\r\n--------------------------------\r\n--------------------------------\r\n".getBytes("utf-8"));
            fout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void collectDeviceInfo(Context ctx) {
        try {
            PackageInfo pi = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionCode = pi.versionCode + "";
                this.infos.put("versionName", pi.versionName == null ? "null" : pi.versionName);
                this.infos.put("versionCode", versionCode);
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        for (Field field : Build.class.getDeclaredFields()) {
            try {
                field.setAccessible(true);
                this.infos.put(field.getName(), field.get(null).toString());
            } catch (Exception e2) {
            }
        }
    }
}
