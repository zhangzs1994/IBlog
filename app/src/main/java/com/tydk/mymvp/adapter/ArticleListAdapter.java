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
 * @description: 文章列表适配器
 */
public class ArticleListAdapter extends BaseQuickAdapter<Map<String, String>, BaseViewHolder> {

    public ArticleListAdapter(int layoutResId, @Nullable List<Map<String, String>> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Map<String, String> item) {
        helper.setText(R.id.tv_item_title, item.get("title"));
        helper.setText(R.id.tv_item_desc, "\t\t\t\t" + item.get("desc"));
        helper.setText(R.id.tv_item_chapter, item.get("chapter"));
        helper.setText(R.id.tv_item_date, item.get("date"));
        GlideUtils.getInstance().loadCircleImage(mContext, item.get("image"), helper.getView(R.id.iv_item_img));
    }
}
