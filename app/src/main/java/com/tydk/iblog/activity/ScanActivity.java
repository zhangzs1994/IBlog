package com.tydk.iblog.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tydk.iblog.R;
import com.tydk.iblog.annotations.ContentView;
import com.tydk.iblog.base.BaseActivity;
import com.tydk.iblog.common.Constant;
import com.tydk.iblog.contract.ScanContract;
import com.tydk.iblog.presenter.ScanPresenter;
import com.tydk.uilibrary.utils.ImageUtils;
import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author: zzs
 * @date: 2019-08-06 下午 7:29
 * @description: 扫一扫
*/
@ContentView(R.layout.activity_scan)
public class ScanActivity extends BaseActivity<ScanPresenter> implements ScanContract.View {

    @BindView(R.id.fl_scan_container)
    FrameLayout flScanContainer;
    @BindView(R.id.iv_scan_back)
    ImageView ivScanBack;
    @BindView(R.id.tv_scan_photo)
    TextView tvScanPhoto;
    @BindView(R.id.rl_scan_title)
    RelativeLayout rlScanTitle;
    @BindView(R.id.rl_scan)
    RelativeLayout rlScan;
    private CaptureFragment captureFragment;

    @Override
    public void initView() {
        initCaptureFragment();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initCaptureFragment();
    }

    @Override
    protected ScanPresenter createPresenter() {
        return new ScanPresenter();
    }

    @OnClick({R.id.iv_scan_back, R.id.tv_scan_photo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_scan_back://返回
                finish();
                break;
            case R.id.tv_scan_photo://相册
                openAlbum();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.REQUEST_IMAGE && data != null) {
            try {
                CodeUtils.analyzeBitmap(ImageUtils.getImageAbsolutePath(this, data.getData()), analyzeCallback());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initCaptureFragment() {
        captureFragment = new CaptureFragment();
        CodeUtils.setFragmentArgs(captureFragment, R.layout.layout_scan_view);
        captureFragment.setAnalyzeCallback(analyzeCallback());
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_scan_container, captureFragment).commit();
    }

    @Override
    public void openAlbum() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, Constant.REQUEST_IMAGE);
    }

    @Override
    public CodeUtils.AnalyzeCallback analyzeCallback() {
        return new CodeUtils.AnalyzeCallback() {
            public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
                toastMsg("识别结果：" + result);
            }

            public void onAnalyzeFailed() {
                toastMsg("识别失败！");
            }
        };
    }
}
