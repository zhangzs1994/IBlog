package com.tydk.mymvp.bean;

import java.util.List;

/**
 * @author: zzs
 * @date: 2019/8/5 0005 下午 12:25
 * @description:
 */
public class ContactsInfo {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * name : 德玛西亚之翼
         * url : http://ossweb-img.qq.com/images/lol/web201310/skin/big133000.jpg
         */

        private String name;
        private String url;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
