package com.gc.money.currency.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.gc.money.currency.R;
import com.gc.money.currency.ui.activity.account.FastLoginActivity;
import com.gc.money.currency.util.CacheClear;
import com.tozzais.baselibrary.ui.BaseActivity;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity {


    @BindView(R.id.tv_cache)
    TextView tvCache;
    @BindView(R.id.tv_exit)
    TextView tv_exit;

    public static void launch(Context activity) {
        Intent intent = new Intent(activity, SettingActivity.class);
        activity.startActivity(intent);
    }


    @Override
    public void initView(Bundle savedInstanceState) {
        setBackTitle("Set up");
    }

    @Override
    public void loadData() {
        tvCache.setText(getDataSize());


    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }


    @OnClick({R.id.ll_aboutus, R.id.ll_modify_password
            , R.id.ll_clear, R.id.tv_exit})
    public void onClick(View view) {
        switch (view.getId()) {
           case R.id.ll_aboutus:
               AboutUsActivity.launch(mActivity,"");
                break;
            case R.id.ll_modify_password:
                FastLoginActivity.launch(mActivity,FastLoginActivity.FORGET_PASS);
                break;
            case R.id.ll_clear:
//                tsg("清理完成");
//                tvCache.setText("0M");
                clearAppCache();
                break;
            case R.id.tv_exit:
//                Intent intent = new Intent(mActivity, SelectLoginWayActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
                break;
        }
    }



    private final int CLEAN_SUC = 1001;
    private final int CLEAN_FAIL = 1002;
    public void clearAppCache() {
        new Thread() {
            @Override
            public void run() {
                Message msg = new Message();
                try {
                    msg.what = CLEAN_SUC;
                } catch (Exception e) {
                    e.printStackTrace();
                    msg.what = CLEAN_FAIL;
                }
                handler.sendMessage(msg);
            }
        }.start();
    }

    // 清除缓存Handler
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CLEAN_FAIL:
                    //ToastUtils.showToastNew("清除失败");
                    break;
                case CLEAN_SUC:
                    CacheClear.cleanApplicationDataNoSP(mActivity);
                    String dataSize = getDataSize();//获取缓存大小
                    tvCache.setText(dataSize.equals("0.0Byte")?"0.0Byte":dataSize);
                    //ToastUtils.showToastNew("清除成功");
                    break;
            }
        }
    };

    public void callPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        mContext.startActivity(intent);
    }

    //计算缓存
    private String getDataSize() {
        long fileSize = 0;
        File filesDir = getFilesDir();
        File cacheDir = getCacheDir();
        fileSize += CacheClear.getDirSize(filesDir);
        fileSize += CacheClear.getDirSize(cacheDir);
        String formatSize = CacheClear.getFormatSize(fileSize);
        return formatSize;
    }
}
