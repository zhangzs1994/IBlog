package com.tydk.mymvp.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.scwang.wave.MultiWaveHeader;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tydk.mymvp.R;
import com.tydk.mymvp.annotations.ContentView;
import com.tydk.mymvp.base.BaseActivity;
import com.tydk.mymvp.bean.UserInfo;
import com.tydk.mymvp.contract.LoginContract;
import com.tydk.mymvp.presenter.LoginPresenter;
import com.tydk.uilibrary.utils.KeyboardUtils;
import com.tydk.uilibrary.utils.SPUtils;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

/**
 * @author: zzs
 * @date: 2019-07-31 下午 3:48
 * @description: 登录
 */
@ContentView(R.layout.activity_login)
public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {


    @BindView(R.id.mwh_login_wave)
    MultiWaveHeader mwhLoginWave;
    @BindView(R.id.et_login_name)
    EditText etLoginName;
    @BindView(R.id.et_login_pwd)
    EditText etLoginPwd;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.iv_login_person)
    ImageView ivLoginPerson;
    @BindView(R.id.tv_line_name)
    TextView tvLineName;
    @BindView(R.id.iv_login_lock)
    ImageView ivLoginLock;
    @BindView(R.id.iv_login_visibility)
    ImageView ivLoginVisibility;
    @BindView(R.id.tv_line_pwd)
    TextView tvLinePwd;
    @BindView(R.id.tv_login_forget)
    TextView tvLoginForget;
    @BindView(R.id.btn_register)
    Button btnRegister;
    @BindView(R.id.cl_login_container)
    ConstraintLayout clLoginContainer;
    private boolean isVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter();
    }


    @Override
    public void initView() {

        initPermissions();
        etLoginName.setText("zzs123");
        etLoginName.setSelection(etLoginName.getText().length());
        etLoginPwd.setText("123456");
        etLoginPwd.setSelection(etLoginPwd.getText().length());
        KeyboardUtils.setGlobalListener(this, clLoginContainer);
    }

    @Override
    public String getName() {
        return etLoginName.getText().toString();
    }

    @Override
    public String getPwd() {
        return etLoginPwd.getText().toString();
    }

    @Override
    public void initPermissions() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            // 用户已经同意该权限
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                        } else {
                            // 用户拒绝了该权限，并且选中『不再询问』
                        }
                    }
                });
    }

    @Override
    public void onSuccess(UserInfo info) {
        toastMsg(info.getUsername() + "登录成功！");
        SPUtils.with(this).put("name", info.getUsername());
        SPUtils.with(this).put("password", info.getPassword());
        jumpToMain();
    }

    @Override
    public void onError(Throwable throwable, String message) {
        toastMsg(message);
    }

    @Override
    public void jumpToMain() {
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void jumpToRegister() {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    @OnClick({R.id.iv_login_visibility, R.id.tv_login_forget, R.id.btn_login, R.id.btn_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_login_visibility://显示密码
                if (isVisible) {
                    ivLoginVisibility.setImageResource(R.drawable.ic_visibility_light_blue_500_24dp);
                    etLoginPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    ivLoginVisibility.setImageResource(R.drawable.ic_visibility_off_light_blue_500_24dp);
                    etLoginPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                etLoginPwd.setSelection(etLoginPwd.getText().length());
                isVisible = !isVisible;
                break;
            case R.id.tv_login_forget://忘记密码
                break;
            case R.id.btn_login://登录
                mPresenter.login(this, getName(), getPwd());
                break;
            case R.id.btn_register://注册
                jumpToRegister();
                break;
        }
    }
}
