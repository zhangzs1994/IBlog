package com.tydk.mymvp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.tydk.mymvp.R;
import com.tydk.mymvp.annotations.ContentView;
import com.tydk.mymvp.base.BaseActivity;
import com.tydk.mymvp.bean.UserInfo;
import com.tydk.mymvp.contract.RegisterContract;
import com.tydk.mymvp.presenter.RegisterPresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author: zzs
 * @date: 2019-07-31 下午 5:13
 * @description: 注册
 */
@ContentView(R.layout.activity_register)
public class RegisterActivity extends BaseActivity<RegisterPresenter> implements RegisterContract.View {

    @BindView(R.id.iv_back)
    ImageView ivRegisterBack;
    @BindView(R.id.iv_register_person)
    ImageView ivRegisterPerson;
    @BindView(R.id.et_register_name)
    EditText etRegisterName;
    @BindView(R.id.tv_line_name)
    TextView tvLineName;
    @BindView(R.id.iv_register_pwd)
    ImageView ivRegisterPwd;
    @BindView(R.id.et_register_pwd)
    EditText etRegisterPwd;
    @BindView(R.id.tv_line_pwd)
    TextView tvLinePwd;
    @BindView(R.id.iv_register_repwd)
    ImageView ivRegisterRepwd;
    @BindView(R.id.et_register_repwd)
    EditText etRegisterRepwd;
    @BindView(R.id.tv_line_repwd)
    TextView tvLineRepwd;
    @BindView(R.id.btn_register)
    Button btnRegister;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected RegisterPresenter createPresenter() {
        return new RegisterPresenter();
    }


    @Override
    public void initView() {
        setTitle("注册");
    }
    
    @Override
    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    @Override
    public String getName() {
        return etRegisterName.getText().toString();
    }

    @Override
    public String getPwd() {
        return etRegisterPwd.getText().toString();
    }

    @Override
    public String getRePwd() {
        return etRegisterRepwd.getText().toString();
    }

    @Override
    public void onSuccess(UserInfo info) {
        toastMsg("注册成功");
    }

    @Override
    public void onError(Throwable throwable, String message) {
        toastMsg(message);
    }

    @OnClick({R.id.iv_back, R.id.btn_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back://返回
                finish();
                break;
            case R.id.btn_register://注册
                mPresenter.register(this, getName(), getPwd(), getRePwd());
                break;
        }
    }

}
