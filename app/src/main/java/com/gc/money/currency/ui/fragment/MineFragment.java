package com.gc.money.currency.ui.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.gc.money.currency.R;
import com.gc.money.currency.bean.eventbus.UpdateMineInfo;
import com.gc.money.currency.bean.net.UserInfo;
import com.gc.money.currency.global.GlobalParam;
import com.gc.money.currency.global.ImageUtil;
import com.gc.money.currency.ui.activity.AboutUsActivity;
import com.gc.money.currency.ui.activity.AdviseCallBackActivity;
import com.gc.money.currency.ui.activity.MineDataActivity;
import com.gc.money.currency.ui.activity.SettingActivity;
import com.gc.money.currency.ui.activity.account.LoginActivity;
import com.gc.money.currency.weight.CircleImageView;
import com.tozzais.baselibrary.ui.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;


public class MineFragment extends BaseFragment {


    @BindView(R.id.vi_image)
    CircleImageView viImage;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.tv_exit)
    TextView tv_exit;

    @Override
    public int setLayout() {
        return R.layout.fragment_mine;
    }


    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);


    }


    @Override
    public void loadData() {
        if (GlobalParam.getLogin()){
            tvLogin.setVisibility(View.GONE);
            tv_exit.setVisibility(View.VISIBLE);
            UserInfo userBean = GlobalParam.getUserBean();
            if (!TextUtils.isEmpty(userBean.logo))
                ImageUtil.load(mActivity,viImage,userBean.logo);

        }else {
            tvLogin.setVisibility(View.VISIBLE);
            tv_exit.setVisibility(View.GONE);
            viImage.setImageResource(R.mipmap.avatar_default);
        }



    }


    @OnClick({R.id.vi_image, R.id.tv_login, R.id.ll_collect
            , R.id.ll_callback, R.id.ll_aboutus
            , R.id.ll_setting, R.id.iv_setting, R.id.tv_exit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.vi_image:
                if (GlobalParam.getLogin()){
                    MineDataActivity.launch(mActivity);
                }
                break;
            case R.id.tv_login:
                LoginActivity.launch(mActivity);
                break;
            case R.id.ll_collect:
                break;
            case R.id.ll_callback:
                AdviseCallBackActivity.launch(mActivity);
                break;
            case R.id.ll_aboutus:
                AboutUsActivity.launch(mActivity,"");
                break;
            case R.id.ll_setting:
            case R.id.iv_setting:
                SettingActivity.launch(mActivity);
                break;
            case R.id.tv_exit:
                GlobalParam.setLogin(false);
                tsg("退出成功");
                loadData();
                break;
        }
    }

    @Override
    public void onEvent(Object o) {
        super.onEvent(o);
        if (o instanceof UpdateMineInfo){
            loadData();
        }
    }
}
