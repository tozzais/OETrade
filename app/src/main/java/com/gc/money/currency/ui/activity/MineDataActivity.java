package com.gc.money.currency.ui.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.gc.money.currency.R;
import com.gc.money.currency.bean.eventbus.UpdateMineInfo;
import com.gc.money.currency.bean.net.UserInfo;
import com.gc.money.currency.global.GlobalParam;
import com.gc.money.currency.global.ImageUtil;
import com.gc.money.currency.util.PhotoUtils;
import com.gc.money.currency.weight.CircleImageView;
import com.tozzais.baselibrary.ui.CheckPermissionActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

public class MineDataActivity extends CheckPermissionActivity {

    @BindView(R.id.iv_avater)
    CircleImageView ivAvater;
    @BindView(R.id.tv_nickname)
    TextView tv_nickname;
    @BindView(R.id.tv_phone)
    TextView tv_phone;

    public static void launch(Context activity) {
        Intent intent = new Intent(activity, MineDataActivity.class);
        activity.startActivity(intent);
    }
    @Override
    public void initView(Bundle savedInstanceState) {
        setBackTitle("personal information");
    }
    @Override
    public void loadData() {
        UserInfo userBean = GlobalParam.getUserBean();
        if (!TextUtils.isEmpty(userBean.logo))
            ImageUtil.load(mActivity,ivAvater,userBean.logo);

        tv_phone.setText(userBean.phone);


    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_minedata;
    }

    Dialog dialog;
    @OnClick({R.id.ll_avatar, R.id.ll_nickname})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_avatar:
                checkPermissions(PhotoUtils.needPermissions);
                break;

            case R.id.ll_nickname:
                ModifyNickNameActivity.launch(mActivity, tv_nickname.getText().toString().trim());
                break;
        }
    }

    @Override
    public void permissionGranted() {
        PhotoUtils.getInstance().selectPic(mActivity);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String path = PhotoUtils.getInstance().getPath(mActivity, requestCode, data);
            UserInfo userInfo = new UserInfo(path, tv_phone.getText().toString());
            GlobalParam.setUserBean(userInfo);

            EventBus.getDefault().post(new UpdateMineInfo());
            ImageUtil.loadLocal(mContext, ivAvater, path);
        }
        if (requestCode == 100 && resultCode == 101) {
            tv_nickname.setText(data.getStringExtra("name"));
        }
    }

}
