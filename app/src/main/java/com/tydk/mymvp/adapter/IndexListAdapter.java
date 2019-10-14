package com.tydk.mymvp.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tydk.mymvp.R;
import com.tydk.uilibrary.utils.GlideUtils;

import java.util.List;
import java.util.Map;

/**
 * @author: zzs
 * @date: 2019/8/2 0002 上午 9:25
 * @description: 首页列表适配器
 */
public class IndexListAdapter extends BaseQuickAdapter<Map<String, String>, BaseViewHolder> {

    public IndexListAdapter(int layoutResId, @Nullable List<Map<String, String>> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Map<String, String> item) {
        helper.setText(R.id.tv_item_name, item.get("name"));
        GlideUtils.getInstance().loadCircleImage(mContext, item.get("image"), helper.getView(R.id.iv_item_img));
    }
}
