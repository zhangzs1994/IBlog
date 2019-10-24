package com.tydk.iblog.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.widget.LinearLayout;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.tydk.iblog.R;
import com.tydk.iblog.annotations.ContentView;
import com.tydk.iblog.base.BaseActivity;
import com.tydk.iblog.bean.TabEntity;
import com.tydk.iblog.fragment.ContactsFragment;
import com.tydk.iblog.fragment.IndexFragment;
import com.tydk.iblog.contract.MainContract;
import com.tydk.iblog.fragment.ArticleFragment;
import com.tydk.iblog.fragment.MineFragment;
import com.tydk.iblog.presenter.MainPresenter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author: zzs
 * @date: 2019-08-01 下午 2:27
 * @description: 首页
 */
@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {

    @BindView(R.id.ctl_main)
    CommonTabLayout ctlMain;
    @BindView(R.id.ll_main_zxing)
    LinearLayout llMainZxing;
    private String[] mTitles = {"首页", "文章", "", "联系人", "我的"};
    private int[] mIconUnSelectIds = {R.drawable.nav_index, R.drawable.nav_ctrol, 0,
            R.drawable.nav_xinxi, R.drawable.nav_user};
    private int[] mIconSelectIds = {R.drawable.nav_active_index, R.drawable.nav_active_ctrol, 0,
            R.drawable.nav_active_xinxi, R.drawable.nav_active_user};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private SparseArray<Fragment> mFragmentSparseArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter();
    }

    @Override
    public void initView() {
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnSelectIds[i]));
        }
        mFragmentSparseArray = new SparseArray<>();
        mFragmentSparseArray.append(0, new IndexFragment());
        mFragmentSparseArray.append(1, new ArticleFragment());
        mFragmentSparseArray.append(3, new ContactsFragment());
        mFragmentSparseArray.append(4, new MineFragment());
        ctlMain.setTabData(mTabEntities);
        ctlMain.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                if (position == 2) {
                    return;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_main,
                        mFragmentSparseArray.get(position)).commit();
            }

            @Override
            public void onTabReselect(int position) {
            }
        });
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_main,
                mFragmentSparseArray.get(0)).commit();
    }


    @OnClick(R.id.ll_main_zxing)
    public void onViewClicked() {
        jumpToScan();
    }

    @Override
    public void jumpToScan() {
        startActivity(new Intent(this, ScanActivity.class));
    }
}
