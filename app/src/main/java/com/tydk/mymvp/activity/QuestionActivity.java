package com.tydk.mymvp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.google.gson.Gson;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.tydk.mymvp.R;
import com.tydk.mymvp.adapter.GridImageAdapter;
import com.tydk.mymvp.annotations.ContentView;
import com.tydk.mymvp.base.BaseActivity;
import com.tydk.mymvp.bean.ProvinceInfo;
import com.tydk.mymvp.contract.QuestionContract;
import com.tydk.mymvp.presenter.QuestionPresenter;
import com.tydk.uilibrary.utils.FullyGridLayoutManager;
import com.tydk.uilibrary.utils.JsonUtils;
import com.tydk.uilibrary.utils.KeyboardUtils;
import com.tydk.uilibrary.utils.PickerUtils;
import com.tydk.uilibrary.utils.PopupUtils;
import com.tydk.uilibrary.view.SelectDialog;

import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author: zzs
 * @date: 2019-08-09 上午 9:42
 * @description: 问题反馈
 */
@ContentView(R.layout.activity_question)
public class QuestionActivity extends BaseActivity<QuestionPresenter> implements QuestionContract.View {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_ques_name)
    EditText etQuesName;
    @BindView(R.id.tv_ques_time)
    TextView tvQuesTime;
    @BindView(R.id.ll_ques_time)
    LinearLayout llQuesTime;
    @BindView(R.id.tv_ques_address)
    TextView tvQuesAddress;
    @BindView(R.id.ll_ques_address)
    LinearLayout llQuesAddress;
    @BindView(R.id.tv_ques_type)
    TextView tvQuesType;
    @BindView(R.id.ll_ques_type)
    LinearLayout llQuesType;
    @BindView(R.id.tv_ques_device)
    TextView tvQuesDevice;
    @BindView(R.id.ll_ques_device)
    LinearLayout llQuesDevice;
    @BindView(R.id.et_ques_desc)
    EditText etQuesDesc;
    @BindView(R.id.rv_ques_picture)
    RecyclerView rvQuesPicture;
    @BindView(R.id.btn_ques_submit)
    Button btnQuesSubmit;
    @BindView(R.id.sc_ques_container)
    ScrollView scQuesContainer;
    private List<String> options1Items = new ArrayList<>();
    private List<List<String>> options2Items = new ArrayList<>();
    private List<List<List<String>>> options3Items = new ArrayList<>();
    private GridImageAdapter adapter;
    private int maxSelectNum = 3;
    private List<LocalMedia> selectList = new ArrayList<>();
    private String[] items;
    private boolean[] selected;

    @Override
    protected QuestionPresenter createPresenter() {
        return new QuestionPresenter();
    }

    @Override
    public void initView() {
        mPresenter.attachView(this);
        setTitle("问题反馈");
        initJsonData();
        initRecycler();
        KeyboardUtils.setScrollGlobalListener(this, scQuesContainer, etQuesDesc);
        items = getResources().getStringArray(R.array.ques_type);
        selected = new boolean[items.length];
        for (int i = 0; i < items.length; i++) {
            selected[i] = false;
        }
    }

    private void initRecycler() {
        FullyGridLayoutManager manager = new FullyGridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        rvQuesPicture.setLayoutManager(manager);
        adapter = new GridImageAdapter(this, onAddPicClickListener);
        adapter.setList(selectList);
        adapter.setSelectMax(maxSelectNum);
        rvQuesPicture.setAdapter(adapter);
        adapter.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if (selectList.size() > 0) {
                    LocalMedia media = selectList.get(position);
                    String pictureType = media.getPictureType();
                    int mediaType = PictureMimeType.pictureToVideo(pictureType);
                    switch (mediaType) {
                        case 1:
                            // 预览图片 可自定长按保存路径
                            PictureSelector.create(QuestionActivity.this).externalPicturePreview(position, selectList);
                            break;
                        case 2:
                            // 预览视频
                            PictureSelector.create(QuestionActivity.this).externalPictureVideo(media.getPath());
                            break;
                        case 3:
                            // 预览音频
                            PictureSelector.create(QuestionActivity.this).externalPictureAudio(media.getPath());
                            break;
                    }
                }
            }
        });
        adapter.setOnItemDeleteListener(new GridImageAdapter.OnItemDeleteListener() {
            @Override
            public void onItemDelete(int position, View v) {
                //删除图片监听
//                if (fjxxListBeans.size() > 0) {
//                    fjxxListBeans.remove(position);
//                }
            }
        });
    }

    private GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            List<String> names = new ArrayList();
            names.add("相册");
            names.add("拍照");
            new SelectDialog(QuestionActivity.this, new SelectDialog.SelectDialogListener() {
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    switch (position) {
                        case 0:
                            //相册
                            PictureSelector.create(QuestionActivity.this)
                                    .openGallery(PictureMimeType.ofImage())
                                    .maxSelectNum(maxSelectNum)
                                    .minSelectNum(1)
                                    .imageSpanCount(4)
                                    .selectionMode(PictureConfig.MULTIPLE)
                                    .compress(true)
                                    .sizeMultiplier(0.5f)
                                    .selectionMedia(selectList)
                                    .forResult(PictureConfig.CHOOSE_REQUEST);
                            return;
                        case 1:
                            //拍照
                            PictureSelector.create(QuestionActivity.this)
                                    .openCamera(PictureMimeType.ofImage())
                                    .compress(true)
                                    .sizeMultiplier(0.5f)
                                    .selectionMedia(selectList)
                                    .forResult(PictureConfig.CHOOSE_REQUEST);
                            return;
                        default:
                            return;
                    }
                }
            }, names).show();
        }
    };

    private void initJsonData() {//解析数据
        String JsonData = JsonUtils.getJson(this, "province.json");//获取assets目录下的json文件数据
        List<ProvinceInfo> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        //options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            options1Items.add(jsonBean.get(i).getName());
            List<String> cityList = new ArrayList<>();//该省的城市列表（第二级）
            List<List<String>> province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCity().size(); c++) {//遍历该省份的所有城市
                String cityName = jsonBean.get(i).getCity().get(c).getName();
                cityList.add(cityName);//添加城市
                List<String> city_AreaList = new ArrayList<>();//该城市的所有地区列表

                for (int j = 0; j < jsonBean.get(i).getCity().get(c).getArea().size(); j++) {
                    city_AreaList.add(jsonBean.get(i).getCity().get(c).getArea().get(j));
                    province_AreaList.add(city_AreaList);//添加该省所有地区数据
                }
            }

            /**
             * 添加城市数据
             */
            options2Items.add(cityList);

            /**
             * 添加地区数据
             */
            options3Items.add(province_AreaList);
        }

    }

    public ArrayList<ProvinceInfo> parseData(String result) {//Gson 解析
        ArrayList<ProvinceInfo> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                ProvinceInfo entity = gson.fromJson(data.optJSONObject(i).toString(), ProvinceInfo.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<LocalMedia> images;
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    selectList = new ArrayList<>();
                    images = PictureSelector.obtainMultipleResult(data);
                    selectList.addAll(images);
                    adapter.setList(selectList);
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    }

    @OnClick({R.id.iv_back, R.id.ll_ques_time, R.id.ll_ques_address, R.id.ll_ques_type,
            R.id.ll_ques_device, R.id.btn_ques_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back://返回
                finish();
                break;
            case R.id.ll_ques_time://时间
                PickerUtils.timePicker(this, "时间选择", new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        tvQuesTime.setText(new SimpleDateFormat("yyyy-MM-dd").format(date));
                    }
                });
                break;
            case R.id.ll_ques_address://地区
                PickerUtils.provincePicker(this, "地区选择", options1Items, options2Items,
                        options3Items, new OnOptionsSelectListener() {
                            @Override
                            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                                //返回的分别是三个级别的选中位置
                                String opt1tx = options1Items.size() > 0 ?
                                        options1Items.get(options1) : "";

                                String opt2tx = options2Items.size() > 0
                                        && options2Items.get(options1).size() > 0 ?
                                        options2Items.get(options1).get(options2) : "";

                                String opt3tx = options2Items.size() > 0
                                        && options3Items.get(options1).size() > 0
                                        && options3Items.get(options1).get(options2).size() > 0 ?
                                        options3Items.get(options1).get(options2).get(options3) : "";

                                String address = opt1tx + opt2tx + opt3tx;
                                tvQuesAddress.setText(address);
                            }
                        });
                break;
            case R.id.ll_ques_type://问题类型
//                PickerUtils.showMultiAlertDialog(this, "类型选择", items, selected,
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                String name = "";
//                                for (int j = 0; j < selected.length; j++) {
//                                    if (selected[j]) {
//                                        name = name + items[j] + "\n";
//                                    }
//                                }
//                                tvQuesType.setText(name.substring(0, name.length() - 1));
//                            }
//                        });
                List<String> list = new ArrayList<>();
                for (int i = 0; i < 20; i++) {
                    list.add("条目" + i);
                }
                new PopupUtils.Builder(this)
                        .setList(list)
                        .setConfirmListener(new PopupUtils.OnConfirmClickListener() {
                            @Override
                            public void onClick(ArrayList<Integer> indexList) {
                                Toast.makeText(QuestionActivity.this, new Gson().toJson(indexList), Toast.LENGTH_SHORT).show();
                            }
                        })
                        .build()
                        .show();
                break;
            case R.id.ll_ques_device://设备信息
                List<String> data = Arrays.asList(getResources().getStringArray(R.array.ques_device));
                PickerUtils.dataPicker(this, "设备选择", data, new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        tvQuesDevice.setText(data.get(options1));
                    }
                });
                break;
            case R.id.btn_ques_submit://提交
                break;
        }
    }

    @Override
    public void setTitle(String title) {
        tvTitle.setText(title);
    }

}
