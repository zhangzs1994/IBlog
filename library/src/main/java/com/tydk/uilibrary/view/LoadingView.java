package com.tydk.uilibrary.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.WindowManager.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tydk.uilibrary.R;

/**
 * @author: zzs
 * @date: 2019-05-28 下午 3:10
 * @description: 自定义加载工具类
*/
public class LoadingView extends ProgressDialog {
    private String message;
    private TextView tvLoadDialog;

    public LoadingView(Context context) {
        super(context, R.style.CustomDialog);
    }

    public LoadingView(Context context, String message) {
        super(context, R.style.CustomDialog);
        this.message = message;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        setCancelable(true);
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.dialog_loading);
        LayoutParams params = getWindow().getAttributes();
        params.width = LinearLayout.LayoutParams.WRAP_CONTENT;
        params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(params);
        tvLoadDialog = findViewById(R.id.tv_load_dialog);
        if (!TextUtils.isEmpty(message)) {
            tvLoadDialog.setText(this.message);
        }
    }

    public void show() {
        super.show();
    }

    public void dismiss() {
        super.dismiss();
    }
}
