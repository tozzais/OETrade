package com.gc.money.currency.global;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.adjust.sdk.Adjust;
import com.adjust.sdk.AdjustConfig;
import com.blankj.utilcode.util.LogUtils;
import com.chad.library.adapter.base.module.LoadMoreModuleConfig;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.gc.money.currency.BuildConfig;
import com.gc.money.currency.DemoIntentService;
import com.gc.money.currency.DemoPushService;
import com.google.firebase.messaging.FirebaseMessaging;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.tozzais.baselibrary.weight.loadmore.CustomLoadMoreView;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Locale;

import io.branch.referral.Branch;


public class CoinApplication extends Application {
    public static Context mContext;

    public static String gaID = "";
    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseMessaging.getInstance().setAutoInitEnabled(true);

        LogUtils.getConfig().setLogSwitch(BuildConfig.DEBUG);

        // 在 Application 中配置全局自定义的 LoadMoreView
        LoadMoreModuleConfig.setDefLoadMoreView(new CustomLoadMoreView());

        mContext = this;

        Configuration config = getResources().getConfiguration();
        Resources resources = getResources();
        config.locale = Locale.CHINA;
        DisplayMetrics dm = resources.getDisplayMetrics();
        resources.updateConfiguration(config, dm);

        closeAndroidPDialog();


        // Branch logging for debugging
        Branch.enableLogging();
        Branch.getAutoInstance(this);


        //adjust
        String appToken = "7tmrg3qbrclc";
        String environment = AdjustConfig.ENVIRONMENT_PRODUCTION;
        AdjustConfig config1 = new AdjustConfig(this, appToken, environment);
        Adjust.onCreate(config1);
        registerActivityLifecycleCallbacks(new AdjustLifecycleCallbacks());

        //个推
        com.igexin.sdk.PushManager.getInstance().initialize(this, DemoPushService.class);
        com.igexin.sdk.PushManager.getInstance().registerPushIntentService(getApplicationContext(), DemoIntentService.class);
//        com.igexin.sdk.PushManager.getInstance().setDebugLogger(this, new IUserLoggerInterface() {
//            @Override
//            public void log(String s) {
//                Log.i("PUSH_LOG",s);
//            }
//        });

        // Facebook统计开启
        FacebookSdk.setApplicationId("1129629230835543");
        FacebookSdk.sdkInitialize(this);
        AppEventsLogger.initializeLib(this,"1129629230835543");
        AppEventsLogger.activateApp(this);
    }
    private static final class AdjustLifecycleCallbacks implements ActivityLifecycleCallbacks {
        @Override
        public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle bundle) {

        }

        @Override
        public void onActivityStarted(@NonNull Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {
            Adjust.onResume();
        }

        @Override
        public void onActivityPaused(Activity activity) {
            Adjust.onPause();
        }

        @Override
        public void onActivityStopped(@NonNull Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {

        }

        @Override
        public void onActivityDestroyed(@NonNull Activity activity) {

        }

    }

    //static 代码段可以防止内存泄露
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(android.R.color.transparent, android.R.color.darker_gray);//全局设置主题颜色
                return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });

    }



    private void closeAndroidPDialog(){
        try {
            Class aClass = Class.forName("android.content.pm.PackageParser$Package");
            Constructor declaredConstructor = aClass.getDeclaredConstructor(String.class);
            declaredConstructor.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Class cls = Class.forName("android.app.ActivityThread");
            Method declaredMethod = cls.getDeclaredMethod("currentActivityThread");
            declaredMethod.setAccessible(true);
            Object activityThread = declaredMethod.invoke(null);
            Field mHiddenApiWarningShown = cls.getDeclaredField("mHiddenApiWarningShown");
            mHiddenApiWarningShown.setAccessible(true);
            mHiddenApiWarningShown.setBoolean(activityThread, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
