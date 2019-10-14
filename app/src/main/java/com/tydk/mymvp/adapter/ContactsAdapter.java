package com.tydk.mymvp.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.github.promeg.pinyinhelper.Pinyin;
import com.tydk.mymvp.R;
import com.tydk.uilibrary.utils.GlideUtils;

import java.util.List;
import java.util.Map;

/**
 * @author: zzs
 * @date: 2019/8/2 0002 上午 9:25
 * @description: 联系人适配器
 */
public class ContactsAdapter extends BaseQuickAdapter<Map<String, String>, BaseViewHolder> {

    public ContactsAdapter(int layoutResId, @Nullable List<Map<String, String>> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Map<String, String> item) {
        GlideUtils.getInstance().loadRoundImage(mContext, item.get("image"), 20, helper.getView(R.id.iv_avatar));
        helper.setText(R.id.tv_name, item.get("name"));
        helper.setText(R.id.tv_index, item.get("index"));
        if (helper.getAdapterPosition() == 0 || !mData.get(helper.getAdapterPosition()-1).get("index").equals(Pinyin.toPinyin(item.get("name"), "").substring(0, 1))) {
            helper.getView(R.id.tv_index).setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_index, item.get("index"));
        } else {
            helper.getView(R.id.tv_index).setVisibility(View.GONE);
        }
    }
}
