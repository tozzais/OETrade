package com.gc.money.currency.ui.activity.account;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.gc.money.currency.MainActivity;
import com.gc.money.currency.R;
import com.gc.money.currency.bean.eventbus.LoginFinishSuccess;
import com.gc.money.currency.bean.net.UserInfo;
import com.gc.money.currency.global.GlobalParam;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.tozzais.baselibrary.ui.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_pass)
    EditText etPass;
    @BindView(R.id.tv_selete_phone)
    TextView tvSeletePhone;

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setBackTitle("log in");

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

    }
    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
//        updateUI(account);
    }

    @Override
    public void loadData() {

    }

    @Override
    public void initListener() {

    }


    private void login() {
        String phone = etPhone.getText().toString().trim();
        String pass = etPass.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            tsg("Please enter phone number");
            return;
        }
        if (TextUtils.isEmpty(pass)) {
            tsg("Please enter your password");
            return;
        }
        if (!GlobalParam.getContainPhone(phone)){
            tsg("The account was not found");
            return;
        }else {
            GlobalParam.setLogin(true);
            UserInfo userInfo = new UserInfo("", phone);
            GlobalParam.setUserBean(userInfo);
            tsg("login successful");
            MainActivity.launch(mActivity);
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            etPhone.setText(data.getStringExtra("phone"));
            etPass.setText(data.getStringExtra("pass"));
            etPhone.setSelection(etPhone.getText().toString().trim().length());
            etPass.setSelection(etPass.getText().toString().trim().length());

        }if (requestCode == 1009) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            UserInfo userInfo = new UserInfo("",  account.getAccount().name);
            GlobalParam.setUserBean(userInfo);
            tsg("login successful");
            MainActivity.launch(mActivity);
            // Signed in successfully, show authenticated UI.
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
        }
    }




    @OnClick({R.id.tv_forget, R.id.tv_register, R.id.tv_login, R.id.tv_switch, R.id.tv_weChat_login
            , R.id.tv_aLiPay_login, R.id.sign_in_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_forget:
                FastLoginActivity.launch(mActivity,FastLoginActivity.FORGET_PASS);
                break;
            case R.id.tv_register:
                RegisterActivity.launch(mActivity);
                break;
            case R.id.tv_login:
                login();
                break;
            case R.id.tv_switch:
                FastLoginActivity.launch(mActivity,FastLoginActivity.FAST_LOGIN);
                break;
            case R.id.tv_weChat_login:
                break;
            case R.id.tv_aLiPay_login:
                break;
            case R.id.sign_in_button:
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, 1009);
                break;
        }
    }



    @Override
    public void onEvent(Object o) {
        super.onEvent(o);
        if (o instanceof LoginFinishSuccess){
            finish();
        }
    }



}
