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
 * @description: 首页横向列表适配器
 */
public class IndexAbeamAdapter extends BaseQuickAdapter<Map<String, String>, BaseViewHolder> {

    public IndexAbeamAdapter(int layoutResId, @Nullable List<Map<String, String>> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Map<String, String> item) {
        GlideUtils.getInstance().loadRoundImage(mContext, item.get("image"),20, helper.getView(R.id.iv_item_img));
    }
}
