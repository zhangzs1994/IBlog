package com.tydk.uilibrary;

import android.app.Application;

import me.jessyan.autosize.AutoSizeConfig;
import me.jessyan.autosize.unit.Subunits;

/**
 * @author: zzs
 * @date: 2019-07-30 下午 2:35
 * @description: 自定义Application
 */
public class App extends Application {
    public static App myApp;

    public void onCreate() {
        super.onCreate();
        myApp = this;
        initAutoSize();
    }

    public static App getInstance() {
        if (myApp == null) {
            myApp = new App();
        }
        return myApp;
    }

    private void initAutoSize() {
        AutoSizeConfig.getInstance()
                .getUnitsManager()
                .setSupportDP(false)
                .setSupportSP(false)
                .setSupportSubunits(Subunits.MM);
    }
}
