package com.tydk.iblog.fragment;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.tydk.iblog.R;
import com.tydk.iblog.activity.AboutActivity;
import com.tydk.iblog.activity.QuestionActivity;
import com.tydk.iblog.annotations.ContentView;
import com.tydk.iblog.base.BaseFragment;
import com.tydk.iblog.contract.MineContract;
import com.tydk.iblog.presenter.MinePresenter;
import com.tydk.uilibrary.utils.GlideUtils;
import com.tydk.uilibrary.utils.Rotate3dAnimation;
import com.tydk.uilibrary.view.SelectDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author: zzs
 * @date: 2019-08-05 下午 3:14
 * @description: 我的
 */
@ContentView(R.layout.fragment_mine)
public class MineFragment extends BaseFragment<MinePresenter> implements MineContract.View {

    @BindView(R.id.tv_mine_title)
    TextView tvMineTitle;
    @BindView(R.id.iv_mine_head)
    ImageView ivMineHead;
    @BindView(R.id.tv_mine_name)
    TextView tvMineName;
    @BindView(R.id.tv_mine_sign)
    TextView tvMineSign;
    @BindView(R.id.iv_mine_arrow)
    ImageView ivMineArrow;
    @BindView(R.id.cv_mine_user)
    CardView cvMineUser;
    @BindView(R.id.iv_mine_info)
    ImageView ivMineInfo;
    @BindView(R.id.rl_mine_info)
    RelativeLayout rlMineInfo;
    @BindView(R.id.iv_mine_setting)
    ImageView ivMineSetting;
    @BindView(R.id.rl_mine_setting)
    RelativeLayout rlMineSetting;
    @BindView(R.id.iv_mine_question)
    ImageView ivMineQuestion;
    @BindView(R.id.rl_mine_question)
    RelativeLayout rlMineQuestion;
    @BindView(R.id.iv_mine_about)
    ImageView ivMineAbout;
    @BindView(R.id.rl_mine_about)
    RelativeLayout rlMineAbout;
    Unbinder unbinder;
    @BindView(R.id.ll_mine_trends)
    LinearLayout llMineTrends;
    @BindView(R.id.ll_mine_fans)
    LinearLayout llMineFans;
    @BindView(R.id.ll_mine_follow)
    LinearLayout llMineFollow;
    @BindView(R.id.iv_mine_back)
    ImageView ivMineBack;
    @BindView(R.id.ll_mine_front)
    LinearLayout llMineFront;
    @BindView(R.id.rl_mine_back)
    RelativeLayout rlMineBack;
    private int centerX;
    private int centerY;
    private int depthZ = 500;
    private int duration = 600;
    private Rotate3dAnimation openAnimation;
    private boolean isOpen = false;
    private int maxSelectNum = 4;
    private List<LocalMedia> selectList = new ArrayList<>();

    @Override
    protected MinePresenter createPresenter() {
        return new MinePresenter();
    }

    @Override
    public void initView(View view) {
        isOpen = false;
        setTitle("我的");
        GlideUtils.getInstance()
                .loadCircleWithFrameImage(getActivity(),
                        R.drawable.default_head1,
                        R.drawable.default_head,
                        ivMineHead,
                        3,
                        getResources().getColor(R.color.colorBlueDark));
        tvMineName.setText("张三");
        tvMineSign.setText("没个性，不签名！");
        GlideUtils.getInstance()
                .loadImage(getActivity(),
                        "http://ossweb-img.qq.com/images/lol/web201310/skin/big64003.jpg",
                        ivMineBack);
    }

    @Override
    public void initAnimation(View front, View back) {
        centerX = cvMineUser.getWidth() / 2;
        centerY = cvMineUser.getHeight() / 2;
        //从0到90度，顺时针旋转视图，此时reverse参数为true，达到90度时动画结束时视图变得不可见，
        openAnimation = new Rotate3dAnimation(0, 90, centerX, centerY, depthZ, true);
        openAnimation.setDuration(duration);
        openAnimation.setFillAfter(true);
        openAnimation.setInterpolator(new AccelerateInterpolator());
        openAnimation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                front.setVisibility(View.GONE);
                back.setVisibility(View.VISIBLE);
                //从270到360度，顺时针旋转视图，此时reverse参数为false，达到360度动画结束时视图变得可见
                Rotate3dAnimation rotateAnimation = new Rotate3dAnimation(270, 360, centerX, centerY, depthZ, false);
                rotateAnimation.setDuration(duration);
                rotateAnimation.setFillAfter(true);
                rotateAnimation.setInterpolator(new DecelerateInterpolator());
                cvMineUser.startAnimation(rotateAnimation);
            }
        });
    }

    @OnClick({R.id.cv_mine_user, R.id.iv_mine_head, R.id.ll_mine_trends, R.id.ll_mine_fans,
            R.id.ll_mine_follow, R.id.rl_mine_info, R.id.rl_mine_setting, R.id.rl_mine_question,
            R.id.rl_mine_about})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cv_mine_user://切换布局
                if (isOpen) {
                    initAnimation(rlMineBack, llMineFront);
                } else {
                    initAnimation(llMineFront, rlMineBack);
                }
                //用作判断当前点击事件发生时动画是否正在执行
                if (openAnimation.hasStarted() && !openAnimation.hasEnded()) {
                    return;
                }
                cvMineUser.startAnimation(openAnimation);
                isOpen = !isOpen;
                break;
            case R.id.iv_mine_head://上传头像
                List<String> names = new ArrayList();
                names.add("相册");
                names.add("拍照");
                new SelectDialog(getActivity(), new SelectDialog.SelectDialogListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        switch (position) {
                            case 0:
                                PictureSelector.create(MineFragment.this)
                                        .openGallery(PictureMimeType.ofImage())
                                        .maxSelectNum(maxSelectNum)
                                        .minSelectNum(1)
                                        .imageSpanCount(4)
                                        .selectionMode(PictureConfig.SINGLE)
                                        .compress(true)
                                        .sizeMultiplier(0.5f)
                                        .selectionMedia(selectList)
                                        .forResult(PictureConfig.CHOOSE_REQUEST);
                                break;
                            case 1:
                                PictureSelector.create(MineFragment.this)
                                        .openCamera(PictureMimeType.ofImage())
                                        .compress(true)
                                        .sizeMultiplier(0.5f)
                                        .selectionMedia(selectList)
                                        .forResult(PictureConfig.CHOOSE_REQUEST);
                                break;
                        }
                    }
                }, names).show();
                break;
            case R.id.ll_mine_trends://动态
                break;
            case R.id.ll_mine_fans://粉丝
                break;
            case R.id.ll_mine_follow://关注
                break;
            case R.id.rl_mine_info://消息
                break;
            case R.id.rl_mine_setting://设置
                break;
            case R.id.rl_mine_question://问题反馈
                startActivity(new Intent(getActivity(), QuestionActivity.class));
                break;
            case R.id.rl_mine_about://关于
                startActivity(new Intent(getActivity(), AboutActivity.class));
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<LocalMedia> images;
        if (resultCode == getActivity().RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    selectList = new ArrayList<>();
                    images = PictureSelector.obtainMultipleResult(data);
                    selectList.addAll(images);
                    if (selectList.size() > 0) {
                        GlideUtils.getInstance().loadCircleWithFrameImage(getActivity(), selectList.get(0).getPath(),
                                R.drawable.default_head, ivMineHead, 3, R.color.colorBlueDark);
                    }
                    break;
            }
        }
    }

    @Override
    public void setTitle(String title) {
        tvMineTitle.setText(title);
    }

}
