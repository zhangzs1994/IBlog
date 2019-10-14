package com.tydk.uilibrary.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * @author: zzs
 * @date: 2019-05-28 下午 3:07
 * @description: ShapePreference工具类
 */
public class SPUtils {
    private static final String SP_NAME = "config";
    private static final int MODE = Context.MODE_PRIVATE;

    private SharedPreferences sharedPreferences;
    private Editor editor;

    public SPUtils(Context context){
        sharedPreferences = context.getSharedPreferences(SP_NAME, MODE);
        editor = sharedPreferences.edit();
    }

    public static SPUtils with(Context context){
        return new SPUtils(context);
    }

    /**
     * 存入数据
     *
     * @param key     键
     * @param value   值
     */
    public void put(String key, Object value) {
        if (value instanceof String) {
            editor.putString(key, String.valueOf(value));
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        }

        editor.apply();
    }

    /**
     * 获取值
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return object
     */
    public Object get(String key, Object defaultValue) {
        if (defaultValue instanceof String) {
            return sharedPreferences.getString(key, String.valueOf(defaultValue));
        } else if (defaultValue instanceof Boolean) {
            return sharedPreferences.getBoolean(key, (Boolean) defaultValue);
        } else if (defaultValue instanceof Integer) {
            return sharedPreferences.getInt(key, (Integer) defaultValue);
        } else if (defaultValue instanceof Float) {
            return sharedPreferences.getFloat(key, (Float) defaultValue);
        } else if (defaultValue instanceof Long) {
            return sharedPreferences.getLong(key, (Long) defaultValue);
        } else {
            return defaultValue;
        }
    }

    /**
     * 清除SharedPreferences
     */
    public void clear() {
        editor.clear();
        editor.apply();
    }

    /**
     * 移除某个键对应的值
     *
     * @param key 键
     */
    public void remove(String key) {
        editor.remove(key);
        editor.apply();
    }
}
