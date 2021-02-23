package com.gc.money.currency.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.JavascriptInterface;

import androidx.annotation.Nullable;

import com.adjust.sdk.Adjust;
import com.adjust.sdk.AdjustEvent;
import com.blankj.utilcode.util.LogUtils;
import com.facebook.appevents.AppEventsLogger;
import com.gc.money.currency.global.CoinApplication;
import com.gc.money.currency.util.DeviceUtil;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.igexin.sdk.PushManager;
import com.tozzais.baselibrary.util.log.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Set;

import io.branch.referral.util.BranchEvent;

public class AppJs {


    private H5Activity h5Activity;

    public AppJs(H5Activity h5Activity) {
        this.h5Activity = h5Activity;
    }

    public static final String FORBID_BACK_FOR_JS = "forbidBackForJS";
    public static final String GET_DEVICE_ID = "getDeviceId";
    public static final String GET_GA_ID = "getGaId";
    public static final String GET_GOOGLE_ID = "getGoogleId";
    public static final String IS_CONTAINS_NAME = "isContainsName";
    public static final String OPEN_BROWSER = "openBrowser";
    public static final String OPEN_GOOGLE = "openGoogle";
    public static final String OPEN_PAY_TM = "openPayTm";
    public static final String OPEN_PURE_BROWSER = "openPureBrowser";
    public static final String SHOULD_FORBID_SYS_BACK_PRESS = "shouldForbidSysBackPress";
    public static final String SHOW_TITLE_BAR = "showTitleBar";
    public static final String TAKE_CHANNEL = "takeChannel";
    public static final String TAKE_FCM_PUSH_ID = "takeFCMPushId";
    public static final String TAKE_PORTRAIT_PICTURE = "takePortraitPicture";
    public static final String TAKE_PUSH_ID = "takePushId";

    @JavascriptInterface
    @Nullable
    public String callMethod(String data) {
        LogUtils.eTag("callMethod", data);
        try {
            JSONObject dataObj = new JSONObject(data);
            String methodName = dataObj.optString("name");
            JSONObject paraObj = dataObj.optJSONObject("parameter");
            String paraStr = dataObj.optString("parameter");
            switch (methodName) {
                case GET_DEVICE_ID:
                    return getDeviceId();
                case OPEN_PAY_TM:
                    openPayTm(paraObj.toString());
                    break;
                case SHOW_TITLE_BAR:
                    showTitleBar(Boolean.valueOf(paraStr));
                    break;
                case FORBID_BACK_FOR_JS:
                    forbidBackForJS(paraObj.getInt("param2"), paraObj.getString("param1"));
                    break;
                case IS_CONTAINS_NAME:
                    isContainsName(paraObj.getString("param1"), paraObj.getString("param2"));
                    break;
                case GET_GA_ID:
                    return getGaId();
                case GET_GOOGLE_ID:
                    return getGoogleId();
                case OPEN_BROWSER:
                    openBrowser(paraStr);
                    break;
                case OPEN_GOOGLE:
                    openGoogle(paraObj.toString());
                    break;
                case OPEN_PURE_BROWSER:
                    openPureBrowser(paraObj.toString());
                    break;
                case SHOULD_FORBID_SYS_BACK_PRESS:
                    shouldForbidSysBackPress(paraObj.getInt("forbid"));
                    break;
                case TAKE_CHANNEL:
                    return takeChannel();
                case TAKE_FCM_PUSH_ID:
                    return takeFCMPushId();
                case TAKE_PORTRAIT_PICTURE:
                    takePortraitPicture(paraStr);
                    break;
                case TAKE_PUSH_ID:
                    return takePushId();
                default:
                    return null;
            }
        } catch (JSONException e) {
            return null;
        }
        return null;
    }


    @JavascriptInterface
    public String isNewEdition() {
        return "true";
    }


    /**
     * 获取设备id
     * 自己组合获取唯一设备id
     */
    @JavascriptInterface
    public String getDeviceId() {
        return DeviceUtil.getDeviceId();
    }


    /**
     * 就是设备id 同getDeviceId()
     */
    @JavascriptInterface
    public String getGoogleId() {
        return getDeviceId();
    }

    /**
     * 获取pushId
     */
    @android.webkit.JavascriptInterface
    public String takePushId() {
        return PushManager.getInstance().getClientid(h5Activity);
    }

    /**
     * 集成branch包的时候已经带有Google Play Service核心jar包
     * 获取gpsadid
     */
    @android.webkit.JavascriptInterface
    public String getGaId() {
        return CoinApplication.gaID;
    }

    /**
     * 获取应用渠道
     */
    @android.webkit.JavascriptInterface
    public String takeChannel() {
        return "google";
    }

    /**
     * adjust事件统计
     */
    @android.webkit.JavascriptInterface
    public void adjustTrackEvent(String eventToken) {
        AdjustEvent adjustEvent = new AdjustEvent(eventToken);
        Adjust.trackEvent(adjustEvent);
    }

    /**
     * branch事件统计
     *
     * @param eventName 统计事件名称
     */
    @JavascriptInterface
    public void branchEvent(String eventName) {
        new BranchEvent(eventName)
                .logEvent(h5Activity);
    }

    /**
     * branch事件统计
     *
     * @param eventName  统计时间名称
     * @param parameters 自定义统计参数
     */
    @JavascriptInterface
    public void branchEvent(String eventName, String parameters) {
        BranchEvent branchEvent = new BranchEvent(eventName);
        JsonObject obj = new JsonObject().getAsJsonObject(parameters);
        Bundle bundle = new Bundle();
        Set<String> strings = obj.keySet();
        for (String entry : strings) {
            JsonElement value = obj.get(entry);
            bundle.putString(entry, value.getAsString());
            branchEvent.addCustomDataProperty(
                    entry,
                    value.getAsString()
            );
        }
        branchEvent
                .logEvent(h5Activity);
    }

    /**
     * branch事件统计
     *
     * @param eventName  统计事件名称
     * @param parameters 自定义统计参数
     * @param alias      事件别名
     */
    @JavascriptInterface
    public void branchEvent(String eventName, String parameters, String alias) {
        BranchEvent branchEvent = new BranchEvent(eventName);
        JsonObject obj = new JsonObject().getAsJsonObject(parameters);
        Bundle bundle = new Bundle();
        for (String entry : obj.keySet()) {
            JsonElement value = obj.get(entry);
            bundle.putString(entry, value.getAsString());
            branchEvent.addCustomDataProperty(
                    entry,
                    value.getAsString()
            );
        }
        branchEvent
                .setCustomerEventAlias(alias)
                .logEvent(h5Activity);
    }

    /**
     * facebook事件统计
     *
     * @param eventName  事件名称
     * @param valueToSum 计数数值
     * @param parameters 自定义统计参数json{}需要全是String类型
     */
    @JavascriptInterface
    public void facebookEvent(String eventName, Double valueToSum, String parameters) {
        AppEventsLogger logger = AppEventsLogger.newLogger(h5Activity);
        JsonObject obj = new JsonObject().getAsJsonObject(parameters);
        Bundle bundle = new Bundle();
        for (String entry : obj.keySet()) {
            JsonElement value = obj.get(entry);
            bundle.putString(entry, value.getAsString());

        }
        logger.logEvent(eventName, valueToSum, bundle);
    }

    /**
     * facebook事件统计
     *
     * @param eventName  事件名称
     * @param parameters 自定义统计参数json{}需要全是String类型
     */
    @JavascriptInterface
    public void facebookEvent(String eventName, String parameters) {

        AppEventsLogger logger = AppEventsLogger.newLogger(h5Activity);
        JsonObject obj = new JsonObject().getAsJsonObject(parameters);
        Bundle bundle = new Bundle();
        for (String entry : obj.keySet()) {
            JsonElement value = obj.get(entry);
            bundle.putString(entry, value.getAsString());
        }
        logger.logEvent(eventName, bundle);
    }

    /**
     * facebook计数统计
     *
     * @param eventName  事件名称
     * @param valueToSum 计数数值
     */
    @JavascriptInterface
    public void facebookEvent(String eventName, Double valueToSum) {
        AppEventsLogger logger = AppEventsLogger.newLogger(h5Activity);
        logger.logEvent(eventName, valueToSum);
    }

    /**
     * facebook 计数事件统计
     *
     * @param eventName 事件名称
     */
    @JavascriptInterface
    public void facebookEvent(String eventName) {
        AppEventsLogger logger = AppEventsLogger.newLogger(h5Activity);
        logger.logEvent(eventName);
    }

    /**
     * firebase事件统计
     */
    @JavascriptInterface
    public void firebaseEvent(String category, String parameters) {
        JsonObject obj = new JsonObject().getAsJsonObject(parameters);
        Bundle bundle = new Bundle();
        for (String entry : obj.keySet()) {
            JsonElement value = obj.get(entry);
            bundle.putString(entry, value.getAsString());
        }
        FirebaseAnalytics.getInstance(h5Activity).logEvent(category, bundle);
    }

    /**
     * adjust事件统计
     *
     * @param eventToken 统计时间名称
     * @param valueToSum 收入
     * @param currency   货币名
     */
    @JavascriptInterface
    public void adjustTrackEvent(String eventToken, Double valueToSum, String currency) {

        AdjustEvent adjustEvent = new AdjustEvent(eventToken);
        adjustEvent.setRevenue(valueToSum, currency);
        Adjust.trackEvent(adjustEvent);
    }

    /**
     * 调取谷歌
     *
     * @param data {"sign":"","host":"https://bb.skr.today"}
     */
    @JavascriptInterface
    public void openGoogle(String data) {
        if (h5Activity instanceof H5Activity) {
            h5Activity.googleLogin(data);
        }
    }

    /**
     * 打开paytm
     * 本地有paytm打开应用/没有打开web版 paytm支付(web版)需要新开一个页面
     */
    @JavascriptInterface
    public void openPayTm(String data) {
        if (h5Activity instanceof H5Activity) {
            h5Activity.pay(data);
        }

    }

    /**
     * 头像获取
     *
     * @param callbackMethod 回传图片时调用H5的方法名
     */
    @JavascriptInterface
    public void takePortraitPicture(String callbackMethod) {
        if (h5Activity instanceof H5Activity) {
            // WebActivity成员变量记录下js方法名
            h5Activity.takePortraitPicture(callbackMethod);
        }
    }


    /**
     * 是否禁用系统返回键
     * 1 禁止
     */
    @JavascriptInterface
    public void shouldForbidSysBackPress(int forbid) {
        if (h5Activity instanceof H5Activity) {
            //WebActivity成员变量记录下是否禁止
            h5Activity.setShouldForbidBackPress(forbid);
            //WebActivity 重写onBackPressed方法 变量为1时禁止返回操作
        }
    }

    /**
     * 返回键调用h5控制
     *
     * @param forbid         是否禁止返回键 1 禁止
     * @param callbackMethod 反回时调用的h5方法 例如:detailBack() 不需要时传空串只禁止返回
     */
    @JavascriptInterface
    public void forbidBackForJS(int forbid, String callbackMethod) {
        if (h5Activity instanceof H5Activity) {
            h5Activity.setShouldForbidBackPress(forbid);
            //同上
            h5Activity.setBackPressJSMethod(callbackMethod);
            //WebActivity成员变量记录下js方法名 在禁止返回时调用js方法
        }
    }

    /**
     * 使用手机里面的浏览器打开 url
     *
     * @param url 打开 url
     */
    @JavascriptInterface
    public void openBrowser(String url) {
        if (h5Activity instanceof H5Activity) {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(uri);
            if (intent.resolveActivity(h5Activity.getPackageManager()) != null) {
                h5Activity.startActivity(intent);
            }
        }
    }

    @JavascriptInterface
    public void isContainsName(String callbackMethod, String name) {
        boolean has = false;
        switch (name) {
            case FORBID_BACK_FOR_JS:
            case GET_DEVICE_ID:
            case GET_GA_ID:
            case GET_GOOGLE_ID:
            case IS_CONTAINS_NAME:
            case OPEN_BROWSER:
            case OPEN_GOOGLE:
            case OPEN_PAY_TM:
            case OPEN_PURE_BROWSER:
            case SHOULD_FORBID_SYS_BACK_PRESS:
            case SHOW_TITLE_BAR:
            case TAKE_CHANNEL:
            case TAKE_FCM_PUSH_ID:
            case TAKE_PORTRAIT_PICTURE:
            case TAKE_PUSH_ID:
                has = true;
                break;
        }
        h5Activity.isContainName(callbackMethod,has);

    }

    @JavascriptInterface
    public void openPureBrowser(String json) {
        Gson gson = new Gson();
        WebBean webBean = gson.fromJson(json, WebBean.class);
        WebViewActivity.launch(h5Activity, webBean);

    }

    @JavascriptInterface
    public void showTitleBar(boolean visible) {
        h5Activity.showTitleBar(visible);

    }

    @JavascriptInterface
    public String takeFCMPushId() {
        return FirebaseInstanceId.getInstance().getToken();

    }
}
