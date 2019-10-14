package com.tydk.uilibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.tydk.uilibrary.R;

import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author: zzs
 * @date: 2019/8/7 0007 下午 3:40
 * @description: 条件选择器
 */
public class PickerUtils {
    private static TimePickerView pvTime;
    private static OptionsPickerView pvOption;
    private static AlertDialog alertDialog;
    private static AlertDialog.Builder alertBuilder;


    //条件选择
    public static void dataPicker(Activity activity, String title, final List<String> data, OnOptionsSelectListener listener) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
        }
        pvOption = new OptionsPickerBuilder(activity, listener)
                .setSubmitText("确定")//确定按钮文字
                .setCancelText("取消")//取消按钮文字
                .setTitleText(title)//标题
                .setSubCalSize(18)//确定和取消文字大小
                .setTitleSize(20)//标题文字大小
                .setTitleColor(activity.getResources().getColor(R.color.colorFont))
                .setSubmitColor(activity.getResources().getColor(R.color.colorBlue))
                .setCancelColor(activity.getResources().getColor(R.color.colorBlue))
                .setTitleBgColor(activity.getResources().getColor(R.color.colorBg))
                .setBgColor(activity.getResources().getColor(R.color.colorWhite))
                .setContentTextSize(18)//滚轮文字大小
                .setSelectOptions(0)  //设置默认选中项
                .setOutSideCancelable(true)//点击外部dismiss default true
                .isDialog(false)//是否显示为对话框样式
                .build();
        pvOption.setPicker(data);//添加数据源
        pvOption.show();
    }

    //地区三级联动
    public static void provincePicker(Activity activity, String title, final List data1,
                                      final List<List<String>> data2, final List<List<List<String>>> data3,
                                      OnOptionsSelectListener listener) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
        }
        pvOption = new OptionsPickerBuilder(activity, listener)
                .setSubmitText("确定")//确定按钮文字
                .setCancelText("取消")//取消按钮文字
                .setTitleText(title)//标题
                .setSubCalSize(18)//确定和取消文字大小
                .setTitleSize(20)//标题文字大小
                .setTitleColor(activity.getResources().getColor(R.color.colorFont))
                .setSubmitColor(activity.getResources().getColor(R.color.colorBlue))
                .setCancelColor(activity.getResources().getColor(R.color.colorBlue))
                .setTitleBgColor(activity.getResources().getColor(R.color.colorBg))
                .setBgColor(activity.getResources().getColor(R.color.colorWhite))
                .setContentTextSize(18)//滚轮文字大小
                .setSelectOptions(0, 0, 0)  //设置默认选中项
                .setOutSideCancelable(true)//点击外部dismiss default true
                .isDialog(false)//是否显示为对话框样式
                .build();
        pvOption.setPicker(data1, data2, data3);//添加数据源
        pvOption.show();
    }


    //时间选择
    public static void timePicker(Activity activity, String title, OnTimeSelectListener listener) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
        }
        Calendar selectedDate = Calendar.getInstance();
        int year = selectedDate.get(Calendar.YEAR);
        Calendar startDate = Calendar.getInstance();
        startDate.set(2000, 0, 1);
        Calendar endDate = Calendar.getInstance();
        endDate.set(year, 11, 31);
        pvTime = new TimePickerBuilder(activity, listener)
                .setType(new boolean[]{true, true, true, false, false, false})
                .setCancelText("取消")
                .setSubmitText("确定")
                .setContentTextSize(18)
                .setTitleSize(20)
                .setTitleText(title)
                .setOutSideCancelable(true)
                .isCyclic(false)
                .setTitleColor(activity.getResources().getColor(R.color.colorFont))
                .setSubmitColor(activity.getResources().getColor(R.color.colorBlue))
                .setCancelColor(activity.getResources().getColor(R.color.colorBlue))
                .setTitleBgColor(activity.getResources().getColor(R.color.colorBg))
                .setBgColor(activity.getResources().getColor(R.color.colorWhite))
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setLabel("年", "月", "日", "时", "分", "秒")
                .isCenterLabel(false)
                .isDialog(false)
                .build();
        pvTime.show();
    }

    //多条件选择dialog
    public static void showMultiAlertDialog(Context context, String title, final String[] items,
                                            final boolean[] selected, DialogInterface.OnClickListener listener) {
        alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setTitle(title);
        /**
         *第一个参数:弹出框的消息集合，一般为字符串集合
         * 第二个参数：默认被选中的，布尔类数组
         * 第三个参数：勾选事件监听
         */
        alertBuilder.setMultiChoiceItems(items, selected, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i, boolean isChecked) {
            }
        });
        alertBuilder.setPositiveButton("确定", listener);
        alertBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog.dismiss();
            }
        });
        alertDialog = alertBuilder.create();
        alertDialog.show();
        alertDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, 1200);
    }

}
