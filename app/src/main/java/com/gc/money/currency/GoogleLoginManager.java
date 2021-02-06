package com.gc.money.currency;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class GoogleLoginManager {
    private static final String TAG = GoogleLoginManager.class.getSimpleName();

    /**
     * 初始化登录
     *
     * @params contenxt 上下文
     * @params clientID Google配置分配的客户端ID
     * @params selfRequestCode 自定义请求码
     */
    public static void initGoogle(Activity context, String clientID, int selfRequestCode) {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(clientID)
                .requestEmail()
                .build();
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(context, gso);
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        context.startActivityForResult(signInIntent, selfRequestCode);
    }

    /**
     * 登录回调
     *
     * @params requestCode 对应onActivityResult的 requestCode
     * @params data 对应onActivityResult的 data
     * @params selfRequestCode 对应上面初始化请求的自定义请求码
     * @params mListener 登录结果回调
     */
    public static void onActivityResult(int requestCode, Intent data, int selfRequestCode,
                                        OnLoginSuccessListener mListener) {
        if (requestCode == selfRequestCode) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task, mListener);
        }
    }


    private static void handleSignInResult(@NonNull Task<GoogleSignInAccount> completedTask,
                                           OnLoginSuccessListener mListener) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            String idToken = account.getIdToken();
            Log.e(TAG, idToken);
            mListener.onSuccessResult(idToken);
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * 退出登录
     */
    public static void GoogleSingOut(Context context, String clientID, final OnGoogleSignOutListener mListener) {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(clientID)
                .requestEmail()
                .build();
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(context, gso);
        mGoogleSignInClient.signOut().addOnCompleteListener((Activity) context, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                mListener.onSignOutSuccess();
            }
        });
    }

    /**
     * 退出登录接口
     */
    public interface OnGoogleSignOutListener {

        void onSignOutSuccess();

    }

    /**
     * 登录回调接口
     */
    public interface OnLoginSuccessListener {

        void onSuccessResult(String result);
    }





}
