package com.tydk.mymvp.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tydk.mymvp.R;
import com.tydk.mymvp.activity.MainActivity;
import com.tydk.mymvp.adapter.IndexAbeamAdapter;
import com.tydk.mymvp.adapter.IndexListAdapter;
import com.tydk.mymvp.annotations.ContentView;
import com.tydk.mymvp.base.BaseFragment;
import com.tydk.mymvp.bean.BannerInfo;
import com.tydk.mymvp.bean.ListDataInfo;
import com.tydk.mymvp.contract.IndexContract;
import com.tydk.mymvp.presenter.IndexPresenter;
import com.tydk.uilibrary.utils.GlideUtils;
import com.tydk.uilibrary.utils.NotificationUtil;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author: zzs
 * @date: 2019-08-01 下午 3:21
 * @description: 首页fragment
 */
@ContentView(R.layout.fragment_index)
public class IndexFragment extends BaseFragment<IndexPresenter> implements IndexContract.View {

    @BindView(R.id.iv_index_list)
    ImageView ivIndexList;
    @BindView(R.id.et_index_search)
    EditText etIndexSearch;
    @BindView(R.id.iv_index_info)
    ImageView ivIndexInfo;
    @BindView(R.id.banner_index)
    Banner bannerIndex;
    Unbinder unbinder;
    @BindView(R.id.rv_index_list)
    RecyclerView rvIndexList;
    @BindView(R.id.rv_index_abeam)
    RecyclerView rvIndexAbeam;
    @BindView(R.id.dl_index)
    DrawerLayout dlIndex;
    @BindView(R.id.ll_index_menu)
    LinearLayout llIndexMenu;
    @BindView(R.id.ll_index_container)
    LinearLayout llIndexContainer;
    @BindView(R.id.iv_index_head)
    ImageView ivIndexHead;
    private IndexListAdapter listAdapter;
    private IndexAbeamAdapter abeamAdapter;
    private Handler handler = new Handler();

    @Override
    protected IndexPresenter createPresenter() {
        return new IndexPresenter();
    }

    @Override
    public void initView(View view) {
        onDrawerListener();
        initRecycler();
        mPresenter.getBanner(getActivity());
        mPresenter.getListData(getActivity());
        GlideUtils.getInstance()
                .loadCircleWithFrameImage(getActivity(),
                        R.drawable.default_head1,
                        R.drawable.default_head,
                        ivIndexHead,
                        3,
                        getResources().getColor(R.color.colorBlueDark));
    }

    @Override
    public void onDrawerListener() {
        //侧滑监听
        dlIndex.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                //设置拉出布局的宽度
                llIndexContainer.setX(slideOffset * drawerView.getWidth());
                //Log.e(TAG, "onDrawerSlide: " + "滑动时执行");
                //Log.e(TAG, "onDrawerSlide偏移量: " + slideOffset);
                //Log.e(TAG, "onDrawerSlide偏移的宽度: " + drawerView.getWidth());
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                //Log.e(TAG, "onDrawerSlide: " + "完全展开时执行");
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                //Log.e(TAG, "onDrawerSlide: " + "完全关闭时执行");
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                //Log.e(TAG, "onDrawerSlide: " + "改变状态时时执行");
            }
        });
    }

    @Override
    public void initBanner(List<String> images, List<String> titles) {
        //设置banner样式
        bannerIndex.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE);
        //设置图片加载器
        bannerIndex.setImageLoader(new GlideUtils());
        //设置图片集合
        bannerIndex.setImages(images);
        //设置banner动画效果
        bannerIndex.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
        bannerIndex.setBannerTitles(titles);
        //设置自动轮播，默认为true
        bannerIndex.isAutoPlay(true);
        //设置轮播时间
        bannerIndex.setDelayTime(3000);
        //设置指示器位置（当banner模式中有指示器时）
        bannerIndex.setIndicatorGravity(BannerConfig.RIGHT);
        //banner设置方法全部调用完毕时最后调用
        bannerIndex.start();
    }

    @Override
    public void initRecycler() {
        rvIndexList.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        rvIndexAbeam.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
    }

    @Override
    public String getSearchContext() {
        return etIndexSearch.getText().toString();
    }

    @Override
    public void onBannerSuccess(List<BannerInfo> info) {
        List<String> images = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        for (int i = 0; i < info.size(); i++) {
            images.add(info.get(i).getImagePath());
            titles.add(info.get(i).getTitle());
        }
        initBanner(images, titles);
    }

    @Override
    public void onBannerError(Throwable throwable, String message) {
        toastMsg(message);
    }

    @Override
    public void onListSuccess(ListDataInfo info) {
        List<Map<String, String>> list = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            Map<String, String> map = new HashMap<>();
            map.put("name", info.getDatas().get(i).getChapterName());
            map.put("image", info.getDatas().get(i).getEnvelopePic());
            list.add(map);
        }
        listAdapter = new IndexListAdapter(R.layout.item_index_list, list);
        rvIndexList.setAdapter(listAdapter);
        abeamAdapter = new IndexAbeamAdapter(R.layout.item_index_abeam, list);
        rvIndexAbeam.setAdapter(abeamAdapter);
        onListItemClickListener();
        onAbeamItemClickListener();
    }

    @Override
    public void onListError(Throwable throwable, String message) {
        toastMsg(message);
    }

    @Override
    public void onListItemClickListener() {
        listAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //toastMsg(position + "");
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        NotificationUtil.show(getActivity(), R.drawable.default_head1, "标题", "通知内容" + position, MainActivity.class);
                    }
                }, position * 1000);
            }
        });
    }

    @Override
    public void onAbeamItemClickListener() {
        abeamAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                toastMsg(position + "");
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        bannerIndex.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        bannerIndex.stopAutoPlay();
    }

    @OnClick({R.id.iv_index_list, R.id.iv_index_info})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_index_list:
                if (!dlIndex.isDrawerOpen(Gravity.LEFT)) {
                    dlIndex.openDrawer(llIndexMenu);
                }
                break;
            case R.id.iv_index_info:
                break;
        }
    }
}
