package com.tydk.iblog.activity;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tydk.iblog.R;
import com.tydk.iblog.annotations.ContentView;
import com.tydk.iblog.base.BaseActivity;
import com.tydk.iblog.contract.AboutContract;
import com.tydk.iblog.presenter.AboutPresenter;
import com.tydk.uilibrary.utils.GlideUtils;

import butterknife.BindView;

/**
 * @author: zzs
 * @date: 2019-08-09 上午 9:43
 * @description: 关于我们
*/
@ContentView(R.layout.activity_about)
public class AboutActivity extends BaseActivity<AboutPresenter> implements AboutContract.View {

    @BindView(R.id.iv_about_img)
    ImageView ivAboutImg;
    @BindView(R.id.tool_about_bar)
    Toolbar toolAboutBar;
    @BindView(R.id.ctl_about)
    CollapsingToolbarLayout ctlAbout;
    @BindView(R.id.tv_about_desc)
    TextView tvAboutDesc;

    @Override
    protected AboutPresenter createPresenter() {
        return new AboutPresenter();
    }

    @Override
    public void initView() {
        GlideUtils.getInstance()
                .loadImage(this,
                        "http://ossweb-img.qq.com/images/lol/web201310/skin/big55004.jpg",
                        ivAboutImg);
        onBackListener();
        ctlAbout.setExpandedTitleColor(getResources().getColor(R.color.colorWhite));
        ctlAbout.setCollapsedTitleTextColor(getResources().getColor(R.color.colorWhite));
        tvAboutDesc.setText(Html.fromHtml(getString(R.string.about_desc)));
    }

    @Override
    public void onBackListener() {
        toolAboutBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
