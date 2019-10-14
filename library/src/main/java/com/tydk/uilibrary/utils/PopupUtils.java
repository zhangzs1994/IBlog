package com.tydk.uilibrary.utils;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.tydk.uilibrary.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: zzs
 * @date: 2019/8/30 0030 上午 10:04
 * @description:
 */
public class PopupUtils {
    private PopupWindow popupWindow;
    private MultiPopupAdapter adapter;
    private View view;
    private TextView tvCancel;
    private TextView tvSure;
    private TextView tvAll;
    private TextView tvNotAll;
    private RecyclerView rvList;
    private OnConfirmClickListener mOnConfirmListener;
    private Builder mBuilder;

    static public class Builder{
        private Activity mActivity;
        private List<String> mList = new ArrayList<>();
        private OnConfirmClickListener mOnConfirmListener;

        public Builder(Activity mActivity){
            this.mActivity = mActivity;
        }
        public Builder setList(List<String> list){
            this.mList = list;
            return this;
        }

        public Builder setConfirmListener(OnConfirmClickListener listener){
            this.mOnConfirmListener = listener;
            return this;
        }

        public PopupUtils build(){
            return new PopupUtils(this);
        }
    }

    public PopupUtils(Builder builder) {
        mBuilder = builder;
        initView();
        initListener();
    }

    public interface OnConfirmClickListener{
        void onClick(ArrayList<Integer> indexList);
    }

    private void initListener() {
        this.mOnConfirmListener = mBuilder.mOnConfirmListener;
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                mOnConfirmListener.onClick(adapter.getSelectedPosition());
            }
        });
        tvAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.selectAll();
            }
        });
        tvNotAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.selectNotAll();
            }
        });
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == MotionEvent.ACTION_DOWN && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    return true;
                }
                return false;
            }
        });
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });
    }

    private void initView() {
        view = LayoutInflater.from(mBuilder.mActivity).inflate(R.layout.popup_multi_view, null);
        tvCancel = view.findViewById(R.id.tv_cancel);
        tvSure = view.findViewById(R.id.tv_sure);
        tvAll = view.findViewById(R.id.tv_all);
        tvNotAll = view.findViewById(R.id.tv_not_all);
        rvList = view.findViewById(R.id.rv_list);
        rvList.setLayoutManager(new LinearLayoutManager(mBuilder.mActivity));
        adapter = new MultiPopupAdapter(R.layout.item_rv_list, mBuilder.mList);
        rvList.setAdapter(adapter);
        popupWindow = new PopupWindow(mBuilder.mActivity);
        popupWindow.setAnimationStyle(R.style.pop_animation);
        popupWindow.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(800);
        popupWindow.setContentView(view);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);

    }

    public void show() {
        backgroundAlpha(0.8f);
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }

    public void backgroundAlpha(float alpha) {
        WindowManager.LayoutParams lp = mBuilder.mActivity.getWindow().getAttributes();
        lp.alpha = alpha; //0.0-1.0
        mBuilder.mActivity.getWindow().setAttributes(lp);
    }
}
