package com.gc.money.currency.ui.activity.account;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.gc.money.currency.MainActivity;
import com.gc.money.currency.R;
import com.gc.money.currency.bean.VestBean;
import com.gc.money.currency.bean.VestResult;
import com.gc.money.currency.dialog.OnDialogClickListener;
import com.gc.money.currency.dialog.PrivacyUtil;
import com.gc.money.currency.global.CoinApplication;
import com.gc.money.currency.global.GlobalParam;
import com.gc.money.currency.global.ImageUtil;
import com.gc.money.currency.http.ApiManager;
import com.gc.money.currency.http.HttpUrl;
import com.gc.money.currency.http.Response;
import com.gc.money.currency.ui.H5Activity;
import com.gc.money.currency.ui.WebViewActivity;
import com.gc.money.currency.util.DeviceUtil;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.igexin.sdk.PushManager;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.CheckPermissionActivity;
import com.tozzais.baselibrary.util.StatusBarUtil;
import com.tozzais.baselibrary.util.log.LogUtil;

import java.io.IOException;
import java.util.TreeMap;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.OnClick;


public class SplashActivity extends CheckPermissionActivity {

    public static String[] needPermissions = {
            Manifest.permission.READ_PHONE_STATE
    };

    @BindView(R.id.iv_image)
    ImageView ivImage;
    @BindView(R.id.tv_time)
    TextView tv_time;

    public static void launch(Activity context) {
        Intent intent = new Intent(context, SplashActivity.class);
        context.startActivity(intent);
        context.finish();

    }


    @Override
    public int getLayoutId() {
        return -1;
    }

    @Override
    protected int getBaseLayout() {
        return R.layout.activity_splash;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        int gsmAvaliable = GoogleApiAvailabilityLight.getInstance().isGooglePlayServicesAvailable(mContext);
        if (gsmAvaliable == ConnectionResult.SUCCESS) {
            Executors.newSingleThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        CoinApplication.gaID =  AdvertisingIdClient.getAdvertisingIdInfo(mContext).getId();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (GooglePlayServicesNotAvailableException e) {
                        e.printStackTrace();
                    } catch (GooglePlayServicesRepairableException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    @Override
    public void permissionGranted() {
        getData();
    }


    @Override
    public void loadData() {
        checkPermissions(needPermissions);
    }

    private void getData(){
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("version", "1.5");
        hashMap.put("channelCode", "google");
        hashMap.put("vestCode", HttpUrl.vest_code);
        String deviceId = DeviceUtil.getDeviceId();
        hashMap.put("deviceId", deviceId);
        String time = "" + System.currentTimeMillis() / 1000;
        hashMap.put("timestamp", time);
        new RxHttp<VestResult>().send(ApiManager.getService(HttpUrl.vest_url).getVest(hashMap),
                new Response<VestResult>(mActivity, Response.BOTH) {
                    @Override
                    public void onNext(VestResult result) {
                        data = result.data;
                        LogUtil.e("onNext"+data.toString());
                        shopPrivacy();
                    }
                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("onNext"+e.getMessage());
//                        MainActivity.launch(mActivity);
                    }
                });
    }

    private void shopPrivacy(){
        boolean firstUse = GlobalParam.getFirstUse();
        LogUtil.e("onNext"+firstUse);
        if (!firstUse) {
            PrivacyUtil.showTwo(mActivity, new OnDialogClickListener() {
                @Override
                public void onSure() {
                    PushManager.getInstance().setPrivacyPolicyStrategy(mActivity, true);
                    GlobalParam.setFirstUse(true);
                    agree();
                }
                @Override
                public void onCancel() {
                    finish();
                }
            });
        }else {
            agree();
        }
    }

    private void agree(){
        if (data == null) return;
        if (data.advOn == 1 && !TextUtils.isEmpty(data.advImg)){
            tv_time.setVisibility(View.VISIBLE);
            ImageUtil.loadNet(mContext, ivImage, data.advImg);
            mHandler.sendEmptyMessageDelayed(1, 3);
        }else{
            if ((data.status == 0)){
                H5Activity.launch(mActivity,data.h5Url);
                finish();
            }else {
                MainActivity.launch(mActivity);
            }
        }
    }

    VestBean data;
    private int time = 10;
    private Handler mHandler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            if (time > 1) {
                time--;
                tv_time.setText(time + " 跳过");
                mHandler.sendEmptyMessageDelayed(1, 1000);
            } else {
                if (data != null){
                    if (data.status == 0){
                        H5Activity.launch(mActivity,data.h5Url);
                        finish();
                    }else {
                        MainActivity.launch(mActivity);
                    }
                }

            }
            return false;
        }
    });

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler != null) {
            mHandler.removeMessages(1);
        }
        mHandler = null;

    }


    @Override
    protected int getToolbarLayout() {
        return -1;
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setTransparentForImageViewInFragment(SplashActivity.this, null);
        StatusBarUtil.setDarkMode(this);
    }


    @OnClick({R.id.tv_time,R.id.iv_image})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_image:
                if (data != null &&  !TextUtils.isEmpty(data.advUrl)){
                    WebViewActivity.launch(mActivity,"",data.advUrl);
                }
                break;
            case R.id.tv_time:
                if (data != null){
                    if (data.status == 0){
                        H5Activity.launch(mActivity,data.h5Url);
                        finish();
                    }else {
                        MainActivity.launch(mActivity);
                    }
                }
                break;
        }
    }


}
