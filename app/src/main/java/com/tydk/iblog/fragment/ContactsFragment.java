package com.tydk.iblog.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tydk.iblog.R;
import com.tydk.iblog.adapter.ContactsAdapter;
import com.tydk.iblog.annotations.ContentView;
import com.tydk.iblog.base.BaseFragment;
import com.tydk.iblog.contract.ContactsContract;
import com.tydk.iblog.presenter.ContactsPresenter;
import com.tydk.uilibrary.view.SideBarView;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * @author: zzs
 * @date: 2019-08-05 上午 9:34
 * @description: 联系人
 */
@ContentView(R.layout.fragment_contacts)
public class ContactsFragment extends BaseFragment<ContactsPresenter> implements ContactsContract.View {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv_contacts)
    RecyclerView rvContacts;
    @BindView(R.id.sb_contacts)
    SideBarView sbContacts;
    Unbinder unbinder;
    private List<Map<String, String>> list;
    private ContactsAdapter adapter;

    @Override
    protected ContactsPresenter createPresenter() {
        return new ContactsPresenter();
    }

    @Override
    public void initView(View view) {
        setTitle("联系人");
        hideBackIcon();
        rvContacts.setLayoutManager(new LinearLayoutManager(getActivity()));
        initData();
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
    public void initData() {
        list = mPresenter.getData(getActivity());
        adapter = new ContactsAdapter(R.layout.item_contacts_list, list);
        rvContacts.setAdapter(adapter);
        onSelectIndexItemListener();
    }

    @Override
    public void onSelectIndexItemListener() {
        sbContacts.setOnSelectIndexItemListener(new SideBarView.OnSelectIndexItemListener() {
            @Override
            public void onSelectIndexItem(String index) {
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).get("index").equals(index)) {
                        ((LinearLayoutManager) rvContacts.getLayoutManager()).scrollToPositionWithOffset(i, 0);
                        return;
                    }
                }
            }
        });
    }
}
