package com.tydk.uilibrary.utils;

import android.support.annotation.Nullable;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tydk.uilibrary.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author: zzs
 * @date: 2019/8/2 0002 上午 9:25
 * @description: 联系人适配器
 */
public class MultiPopupAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private Boolean[] selectStates;

    public MultiPopupAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
        if (data != null && data.size() > 0) {
            selectStates = new Boolean[data.size()];
            Arrays.fill(selectStates, false);
        }
    }

    @Override
    protected void convert(final BaseViewHolder helper, String item) {
        CheckBox checkBox = helper.getView(R.id.checkbox);
        checkBox.setText(item);
        checkBox.setChecked(selectStates[helper.getAdapterPosition()]);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selectStates[helper.getAdapterPosition()] = true;
                } else {
                    selectStates[helper.getAdapterPosition()] = false;
                }
            }
        });
    }

    public ArrayList<Integer> getSelectedPosition() {
        ArrayList<Integer> result = new ArrayList<>();
        if (selectStates == null) return result;
        for (int i = 0; i < selectStates.length; i++) {
            if (selectStates[i]) {
                result.add(i);
            }
        }
        return result;
    }

    public void selectAll() {
        if (selectStates != null) {
            Arrays.fill(selectStates, true);
            notifyDataSetChanged();
        }
    }

    public void selectNotAll() {
        if (selectStates != null) {
            for (int i = 0; i < getData().size(); i++) {
                selectStates[i] = !selectStates[i];
            }
            notifyDataSetChanged();
        }
    }
}
