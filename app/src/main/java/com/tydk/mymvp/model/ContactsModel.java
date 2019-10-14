package com.tydk.mymvp.model;

import android.content.Context;
import android.content.res.AssetManager;

import com.github.promeg.pinyinhelper.Pinyin;
import com.google.gson.Gson;
import com.tydk.mymvp.bean.ContactsInfo;
import com.tydk.mymvp.contract.ContactsContract;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: zzs
 * @date: 2019/8/5 0005 上午 9:35
 * @description:
 */
public class ContactsModel implements ContactsContract.Model {
    private List<Map<String, String>> list;

    @Override
    public List<Map<String, String>> getData(Context context) {
        list = new ArrayList<>();
        ContactsInfo contacts = new Gson().fromJson(getJson(context,"lol.json"), ContactsInfo.class);
        for (int i = 0; i < contacts.getData().size(); i++) {
            Map<String, String> map = new HashMap<>();
            map.put("image", contacts.getData().get(i).getUrl());
            map.put("name", contacts.getData().get(i).getName());
            map.put("index", Pinyin.toPinyin(contacts.getData().get(i).getName(), "").substring(0, 1));
            list.add(map);
        }
        //集合数据根据字母进行排序
        Collections.sort(list, new Comparator<Map>() {
            @Override
            public int compare(Map o1, Map o2) {
                Comparator<Object> comparator = Collator.getInstance(java.util.Locale.CHINA);
                return comparator.compare(o1.get("name"), o2.get("name"));
            }
        });
        return list;
    }

    /**
     * 获取资源文件assets下的json文件
     */
    private String getJson(Context context,String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assets = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assets.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
