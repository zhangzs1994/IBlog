package com.tydk.uilibrary.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;

/**
 * @author: zzs
 * @date: 2019-07-30 下午 4:45
 * @description: 自动生产dimens文件
*/
public class DimensUtils {

    public static void genDimen() {

        StringBuilder swdef = new StringBuilder();
        PrintWriter out;

        try {

            swdef.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                    "<resources>\n");

            int value;
            for (int i = 0; i < 1920; i++) {
                //这里控制对应转换的值，如果是标准尺寸就一对一转换
                value = i + 1;
                swdef.append("<dimen name=\"mm_" + (i + 1) + "\">" + value + "mm</dimen>\r\n");
            }
            swdef.append("</resources>");

            //这里是文件名，1 注意修改 sw 后面的值，和转换值一一对应  2 文件夹和文件要先创建好否则要代码创建
            String filedef = "./app/src/main/res/values/dimens.xml";


            out = new PrintWriter(new BufferedWriter(new FileWriter(filedef)));

            out.println(swdef.toString());


            out.close();

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

        }

    }


    public static void main(String[] args) {

        //genDimen();

    }
}