package com.tydk.iblog.presenter;

import android.content.Context;

import com.tydk.iblog.base.BasePresenter;
import com.tydk.iblog.contract.ContactsContract;
import com.tydk.iblog.model.ContactsModel;

import java.util.List;
import java.util.Map;

/**
 * @author: zzs
 * @date: 2019/8/5 0005 上午 9:35
 * @description:
 */
public class ContactsPresenter extends BasePresenter implements ContactsContract.Presenter {
    private ContactsContract.Model model;

    public ContactsPresenter() {
        model = new ContactsModel();
    }

    @Override
    public List<Map<String,String>> getData(Context context) {
        return model.getData(context);
    }
}
