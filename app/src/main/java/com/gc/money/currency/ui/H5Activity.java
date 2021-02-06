package com.gc.money.currency.ui;

import android.annotation.TargetApi;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;

import com.gc.money.currency.R;
import com.gc.money.currency.bean.GoogleLoginSign;
import com.gc.money.currency.bean.GoogleTokenBean;
import com.gc.money.currency.bean.PayBean;
import com.gc.money.currency.global.Constant;
import com.gc.money.currency.http.ApiManager;
import com.gc.money.currency.http.BaseResult;
import com.gc.money.currency.http.Response;
import com.gc.money.currency.util.PhotoUtils;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.CheckPermissionActivity;
import com.tozzais.baselibrary.util.StatusBarUtil;
import com.tozzais.baselibrary.util.log.LogUtil;
import com.tozzais.baselibrary.util.progress.LoadingUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.TreeMap;

import butterknife.BindView;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;


/**
 * Created by Administrator on 2016/9/8.
 */
public class H5Activity extends CheckPermissionActivity {
    @BindView(R.id.web_view)
    WebView web_view;

    public static void launch(Context from, String h5Url) {
        Intent intent = new Intent(from, H5Activity.class);
        intent.putExtra("h5Url", h5Url);
        from.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_h5_detail;
    }

    @Override
    protected int getToolbarLayout() {
        return -1;
    }

    @Override
    public void initView(Bundle savedInstanceState) {


        WebSettings webSettings = web_view.getSettings();
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        String userAgentString = webSettings.getUserAgentString();
        userAgentString = "ANDROID_AGENT_NATIVE/1.0" + " " + userAgentString;
        webSettings.setUserAgentString(userAgentString);
        web_view.addJavascriptInterface(new AppJs(this), "AppJs");


        webSettings.setAllowFileAccess(true);
        webSettings.setRenderPriority(WebSettings.RenderPriority.NORMAL);
        webSettings.setAppCacheEnabled(true);
        webSettings.setAppCachePath(getExternalCacheDir().getPath());
        webSettings.setDatabaseEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setEnableSmoothTransition(true);
        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            web_view.setWebContentsDebuggingEnabled(false);
        }
        web_view.clearHistory();
        web_view.setDrawingCacheEnabled(true);
        web_view.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        web_view.setDownloadListener((url, userAgent, contentDisposition, mimeType, contentLength) -> {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        });
        web_view.loadUrl(getIntent().getStringExtra("h5Url"));
        web_view.setWebChromeClient(new WebChrome());
        web_view.setWebViewClient(new WebViewClient());

        //谷歌登录
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    public class WebChrome extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView webView, int i) {
            super.onProgressChanged(webView, i);
            web_view.loadUrl("javascript:" + "AppJs");
        }

        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> valueCallback1, FileChooserParams fileChooserParams) {
            valueCallback = valueCallback1;
            selectImage();
            return true;

        }
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.white));
    }

    @Override
    public void loadData() {
        checkPermissions(needPermissions);
    }


    private GoogleLoginSign googleLoginSign;
    GoogleSignInClient mGoogleSignInClient;

    protected void googleLogin(String data) {
        //{"sign":"2eca8ed9-0427-4176-98ac-ac80273a502c","host":"https://fire.citicoption.com"}
        Gson gson = new Gson();
        googleLoginSign = gson.fromJson(data, GoogleLoginSign.class);
        LogUtil.e("请求参数" + googleLoginSign.sign);
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 1009);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtil.e("requestCode=" + requestCode + "，resultCode=" + resultCode);
        if (requestCode == 1009) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            GoogleSignInAccount account;
            try {
                account = task.getResult(ApiException.class);
                String id = account.getId();
                String name = account.getDisplayName();
                String email = account.getEmail();
                getToken(id, name, email);
                //TODO接口请求获取服务器返回的token保存到cookie中然后刷新页面
//                "${host}/user/google/doLogin2.do?id=${id}&name=${name}&sign=${sign}"
            } catch (ApiException e) {
            }
        } else if (requestCode == REQUEST_CODE_PAYTM && data != null) {
            //支付回调
//            tsg("PayTmCallback:" + data.getStringExtra("response"));
        } else if ((requestCode == 0 || requestCode == 1) && resultCode == RESULT_OK) {

            Luban.with(this)
                    .load(PhotoUtils.getInstance().getPath(mActivity, requestCode, data))
                    .ignoreBy(100)
                    .setTargetDir(Constant.ROOT_PATH)
                    .filter(path -> !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif")))
                    .setCompressListener(new OnCompressListener() {
                        @Override
                        public void onStart() {
                            LoadingUtils.show(mContext, "Compressing pictures ...");
                        }

                        @Override
                        public void onSuccess(File file) {
                            LoadingUtils.dismiss();
                            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                            ByteArrayOutputStream b = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);
                            byte[] bytes = b.toByteArray();
                            String bit = Base64.encodeToString(bytes, Base64.NO_WRAP);
                            callback2Web(bit);
//                            if (valueCallback != null ){
//                                valueCallback.onReceiveValue(new Uri[]{Uri.fromFile(file)});
//                            }
                        }
                        @Override
                        public void onError(Throwable e) {
                            LoadingUtils.dismiss();
                        }
                    }).launch();
        }else if (requestCode == 15) {
            if (null == uploadMessage && null == valueCallback) return;
            Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
            if (valueCallback != null) {
                onActivityResultAboveL(data);
            } else if (uploadMessage != null) {
                uploadMessage.onReceiveValue(result);
                uploadMessage = null;

            }
        }
    }

    //{"code":200,"data":{"token1":"cGt4d3ZlanpianJpa2JkbG1uaGRweGNvY2JlNQ\u003D\u003D","token2":"NjAwY2QyZGNjMDhjZDJjYTE4MDJjY2E2ZTUxYjkwZjA\u003D","url":"https\u003A\u002F\u002Ffire.citicoption.com\u002Fusers"},"dts":1611821298895,"msg":"Success."}
    private void getToken(String id, String name, String email) {
        LogUtil.e("参数" + id + "=" + name + "=" + email);
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("id", id);
        hashMap.put("name", name);
        hashMap.put("type", "1");
        hashMap.put("email", email);
        hashMap.put("sign", googleLoginSign.sign);
        new RxHttp<BaseResult<GoogleTokenBean>>().send(ApiManager.getService(googleLoginSign.host).getGoogleToken(hashMap),
                new Response<BaseResult<GoogleTokenBean>>(mActivity, Response.BOTH) {
                    @Override
                    public void onNext(BaseResult<GoogleTokenBean> result) {

                        GoogleTokenBean tokenBean = result.data;
                        if (!TextUtils.isEmpty(tokenBean.token1)) {
                            String value1 = "token1=" + tokenBean.token1 + "==;expires=1; path=/";
                            CookieManager.getInstance().setCookie(googleLoginSign.host, value1);// 设置 Cookie
                        }
                        if (!TextUtils.isEmpty(tokenBean.token2)) {
                            String value2 = "token2=" + tokenBean.token2 + "==;expires=1; path=/";
                            CookieManager.getInstance().setCookie(googleLoginSign.host, value2);// 设置 Cookie
                        }
                        web_view.loadUrl(tokenBean.url);
                        LogUtil.e("参数结果" + tokenBean.token1 + "\n" + tokenBean.token2 + "\n" + tokenBean.url);

                    }
                });
    }



    //全局声明，用于记录选择图片返回的地址值
    private ValueCallback<Uri> uploadMessage;
    private ValueCallback<Uri[]> valueCallback;
    private void selectImage() {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("image/*");
        startActivityForResult(Intent.createChooser(i, "Image Chooser"), 15);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void onActivityResultAboveL( Intent intent) {
        if (valueCallback == null)
            return;
//        content://com.android.providers.media.documents/document/image%3A93667
        Uri[] results = null;
        if (intent != null) {
            String dataString = intent.getDataString();
            ClipData clipData = intent.getClipData();
            if (clipData != null) {
                results = new Uri[clipData.getItemCount()];
                for (int i = 0; i < clipData.getItemCount(); i++) {
                    ClipData.Item item = clipData.getItemAt(i);
                    results[i] = item.getUri();
                }
            }
            if (dataString != null)
                results = new Uri[]{Uri.parse(dataString)};
        }
        valueCallback.onReceiveValue(results);
        valueCallback = null;
    }

    //1 是 js 调用 2 是onShowFileChooser调用
    private int isImage = 0;
    private String callbackMethod;

    protected void takePortraitPicture(String callbackMethod) {
        takePortraitPicture(callbackMethod, 1);
    }

    protected void takePortraitPicture(String callbackMethod, int type) {
        LogUtil.e("参数 图片" + callbackMethod);
        this.callbackMethod = callbackMethod;
        this.isImage = type;
        checkPermissions(PhotoUtils.needPermissions);
    }

    @Override
    public void permissionGranted() {
        if (isImage == 1 || isImage == 2) {
            PhotoUtils.getInstance().selectPic(mActivity);
        }
    }

    /**
     * WebActivity中的方法
     * 需要将图片转码成base64字符串 压缩
     *
     * @param str
     */
    private void callback2Web(String str) {
        LogUtil.e("参数 上传图片结果" + str);
        if (!TextUtils.isEmpty(callbackMethod)) {
            StringBuilder builder = new StringBuilder(callbackMethod).append("(");
            builder.append("'").append("data:image/png;base64,").append(str).append("'");
            builder.append(")");
            String methodName = builder.toString();
            String javaScript = "javascript:" + methodName;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                web_view.evaluateJavascript(javaScript, null);
            } else {
                web_view.loadUrl(javaScript);
            }
        }
    }

    private static final String PACKAGE_NAME_PAYTM = "net.one97.paytm";
    private final int REQUEST_CODE_PAYTM = 111;

    protected void pay(String data) {
        if (!TextUtils.isEmpty(data)) {
            Gson gson = new Gson();
            PayBean payBean = gson.fromJson(data, PayBean.class);
            //init intent
            Intent paytmIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putDouble("nativeSdkForMerchantAmount", Double.parseDouble(payBean.amount));
            bundle.putString("orderid", payBean.orderId);
            bundle.putString("txnToken", payBean.textToken);
            bundle.putString("mid", payBean.mid);
            //the "mode" parameter is not from transaction api,and this parameter is not mandatory
            bundle.putInt("mode", 2);
            paytmIntent.putExtra("bill", bundle);
            try {
                paytmIntent.setComponent(new ComponentName(PACKAGE_NAME_PAYTM,
                        "net.one97.paytm.AJRJarvisSplash"));
                paytmIntent.putExtra("paymentmode", 2);
                startActivityForResult(paytmIntent, REQUEST_CODE_PAYTM);
            } catch (ActivityNotFoundException e) {
                paytmIntent.setComponent(new ComponentName(this, WebViewPayActivity.class));
                startActivity(paytmIntent);
            }
        }
    }


    protected void setBackPressJSMethod(String callbackMethod) {
        this.callbackMethod = callbackMethod;

    }


    int forbid;

    protected void setShouldForbidBackPress(int forbid) {
        this.forbid = forbid;
    }

    @Override
    public void onBackPressed() {
        if (forbid == 1) {
            return;
        }
        super.onBackPressed();
    }


    @Override
    public void onResume() {
        super.onResume();
        if (web_view != null) {
            web_view.getSettings().setJavaScriptEnabled(true);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (web_view != null) {
            web_view.getSettings().setJavaScriptEnabled(false);
        }
    }

    @Override
    protected void onDestroy() {
        try {
            if (web_view != null) {
                web_view.stopLoading();
                web_view.destroy();
                web_view = null;
            }
        } catch (Exception e) {
        }
        super.onDestroy();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && web_view.canGoBack()) {
            web_view.goBack();// 返回前一个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

