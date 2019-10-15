package com.tydk.mymvp.activity;

import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tydk.mymvp.R;
import com.tydk.mymvp.annotations.ContentView;
import com.tydk.mymvp.base.BaseActivity;
import com.tydk.mymvp.contract.WebContract;
import com.tydk.mymvp.presenter.WebPresenter;
import com.tydk.uilibrary.view.CustomWebView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author: zzs
 * @date: 2019-08-12 下午 4:35
 * @description: 网页界面
 */
@ContentView(R.layout.activity_web)
public class WebActivity extends BaseActivity<WebPresenter> implements WebContract.View {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.pb_web)
    ProgressBar pbWeb;
    @BindView(R.id.wv_web)
    WebView wvWeb;
    private String url;

    @Override
    protected WebPresenter createPresenter() {
        return new WebPresenter();
    }

    @Override
    public void initView() {
        if (getIntent() != null) {
            url = getIntent().getStringExtra("url");
        }
        initWebView();
    }

    private void initWebView() {
        CustomWebView customWebView = new CustomWebView(this);
        customWebView.initWebView(wvWeb, url, true);
        wvWeb.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                tvTitle.setText(title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (pbWeb == null) return;
                if (newProgress == 100) {
                    pbWeb.setVisibility(View.GONE);
                }
                pbWeb.setProgress(newProgress);
                super.onProgressChanged(view, newProgress);
            }

        });
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        if (wvWeb.canGoBack()) {
            wvWeb.goBack();
        } else {
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        if (wvWeb.canGoBack()) {
            wvWeb.goBack();
        } else {
            finish();
        }
    }
}
