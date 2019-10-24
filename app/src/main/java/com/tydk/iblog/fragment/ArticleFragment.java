package com.tydk.iblog.fragment;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tydk.iblog.R;
import com.tydk.iblog.activity.WebActivity;
import com.tydk.iblog.adapter.ArticleListAdapter;
import com.tydk.iblog.annotations.ContentView;
import com.tydk.iblog.base.BaseFragment;
import com.tydk.iblog.bean.ListDataInfo;
import com.tydk.iblog.contract.ArticleContract;
import com.tydk.iblog.presenter.ArticlePresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * @author: zzs
 * @date: 2019-08-02 下午 2:53
 * @description: 文章列表
 */
@ContentView(R.layout.fragment_article)
public class ArticleFragment extends BaseFragment<ArticlePresenter> implements ArticleContract.View {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv_article)
    RecyclerView rvArticle;
    Unbinder unbinder;
    @BindView(R.id.srl_article)
    SmartRefreshLayout srlArticle;
    private ArticleListAdapter listAdapter;
    private int pageNum = 0;
    private List<Map<String, String>> list;

    @Override
    protected ArticlePresenter createPresenter() {
        return new ArticlePresenter();
    }

    @Override
    public void initView(View view) {
        setTitle("文章列表");
        hideBackIcon();
        initRecycler();
        mPresenter.getListData(getActivity(), pageNum, true);
    }

    @Override
    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    @Override
    public void hideBackIcon() {
        ivBack.setVisibility(View.GONE);
    }

    @Override
    public void initRecycler() {
        list = new ArrayList<>();
        rvArticle.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvArticle.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        srlArticle.setRefreshHeader(new ClassicsHeader(getActivity()));
        srlArticle.setRefreshFooter(new ClassicsFooter(getActivity()));
        srlArticle.setEnableRefresh(true);
        srlArticle.setEnableLoadMore(true);
        onRefresh();
        onLoadMore();
    }

    @Override
    public void onRefresh() {
        srlArticle.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNum = 0;
                list = new ArrayList<>();
                mPresenter.getListData(getActivity(), pageNum, false);
            }
        });
    }

    @Override
    public void onLoadMore() {
        srlArticle.setOnLoadMoreListener(new OnLoadMoreListener() {
            public void onLoadMore(RefreshLayout refreshlayout) {
                pageNum = pageNum + 1;
                mPresenter.getListData(getActivity(), pageNum, false);
            }
        });
    }

    @Override
    public void onItemClick() {
        if (listAdapter == null) return;
        listAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (list == null || list.size() <= 0) return;
                Intent intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra("url", list.get(position).get("link"));
                startActivity(intent);
            }
        });
    }

    @Override
    public void ouSuccess(ListDataInfo info) {
        if (info.getDatas().size() > 0) {
            srlArticle.setVisibility(View.VISIBLE);
            for (int i = 0; i < info.getDatas().size(); i++) {
                Map<String, String> map = new HashMap<>();
                map.put("title", info.getDatas().get(i).getTitle());
                map.put("desc", info.getDatas().get(i).getDesc());
                map.put("chapter", info.getDatas().get(i).getChapterName());
                map.put("date", info.getDatas().get(i).getNiceDate());
                map.put("image", info.getDatas().get(i).getEnvelopePic());
                map.put("link", info.getDatas().get(i).getLink());
                list.add(map);
            }
            if (pageNum == 0) {
                listAdapter = new ArticleListAdapter(R.layout.item_article_list, list);
                rvArticle.setAdapter(listAdapter);
                onItemClick();
            }
            srlArticle.finishRefresh(true);
            srlArticle.finishLoadMore(true);
            srlArticle.setNoMoreData(false);
            listAdapter.notifyDataSetChanged();
        } else {
            if (pageNum == 0) {
                srlArticle.setVisibility(View.GONE);
            } else {
                srlArticle.finishLoadMore(true);
                srlArticle.finishLoadMoreWithNoMoreData();
            }
        }
    }

    @Override
    public void onError(Throwable throwable, String message) {
        toastMsg(message);
    }

}
