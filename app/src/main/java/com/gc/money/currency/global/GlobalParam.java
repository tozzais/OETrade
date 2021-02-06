package com.gc.money.currency.global;


import android.text.TextUtils;

import com.gc.money.currency.bean.net.UserInfo;
import com.google.gson.Gson;
import com.tozzais.baselibrary.util.SharedPreferencesUtil;


/**
 * Created by jumpbox on 16/4/19.
 */
public class GlobalParam {


    //是否使用
    public static void setContainPhone(String phone) {
        String stringData = SharedPreferencesUtil.getStringData(CoinApplication.mContext, Constant.user_login_finish, "");
        SharedPreferencesUtil.saveStringData(CoinApplication.mContext, Constant.user_login_finish, stringData+phone);
    }
    public static boolean getContainPhone(String phone) {
        String stringData = SharedPreferencesUtil.getStringData(CoinApplication.mContext, Constant.user_login_finish, "");
        return stringData.contains(phone);
    }

    //是否使用
    public static void setLogin(boolean firstUse) {
        SharedPreferencesUtil.saveBooleanData(CoinApplication.mContext, Constant.user_first_use, firstUse);
    }
    public static boolean getLogin() {
        return SharedPreferencesUtil.getBooleanData(CoinApplication.mContext, Constant.user_first_use,false);
    }

    public static void setUserBean(UserInfo userInfo) {
        Gson gson = new Gson();
        SharedPreferencesUtil.saveStringData(CoinApplication.mContext, Constant.user_use_info, gson.toJson(userInfo));
    }
    public static UserInfo getUserBean() {
        String data = SharedPreferencesUtil.getStringData(CoinApplication.mContext, Constant.user_use_info, "");
        if (TextUtils.isEmpty(data)){
            return null;
        }
        return new Gson().fromJson(data,UserInfo.class);
    }


    //是否使用
    public static void setFirstUse(boolean firstUse) {
        SharedPreferencesUtil.saveBooleanData(CoinApplication.mContext, Constant.user_agree_privacy, firstUse);
    }
    public static boolean getFirstUse() {
        return SharedPreferencesUtil.getBooleanData(CoinApplication.mContext, Constant.user_agree_privacy,false);
    }


}
