package com.gc.money.currency.ui.activity.account;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.gc.money.currency.MainActivity;
import com.gc.money.currency.R;
import com.gc.money.currency.bean.eventbus.LoginFinishSuccess;
import com.tozzais.baselibrary.ui.BaseActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *
 */
public class FastLoginActivity extends BaseActivity {


    /**
     * 快捷登录
     */
    public static final int FAST_LOGIN = 0;
    /**
     * 绑定手机号
     */
    public static final int BIND_PHONE = 1;
    /**
     * 忘记密码
     */
    public static final int FORGET_PASS = 2;


    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.et_pass)
    EditText etPass;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.tv_switch)
    TextView tvSwitch;

    private int type;

    public static void launch(Activity activity, int type) {
        Intent intent = new Intent(activity, FastLoginActivity.class);
        intent.putExtra("type", type);
        activity.startActivityForResult(intent, 100);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_fast_login;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        type = getIntent().getIntExtra("type", FAST_LOGIN);
        if (type == FAST_LOGIN) {
            setBackTitle("quick login");
            tvRegister.setText("log in");
            etPass.setVisibility(View.GONE);
        } else if (type == BIND_PHONE) {
            setBackTitle("Bind phone number");
            tvRegister.setText("determine");
            tvSwitch.setVisibility(View.GONE);
        } else if (type == FORGET_PASS) {
            setBackTitle("forget password");
            tvRegister.setText("determine");
            etPass.setHint("Enter new login password");
            tvSwitch.setVisibility(View.GONE);
        }

    }

    @Override
    public void loadData() {

    }

    @Override
    public void initListener() {

    }



    private int time = 60;
    private Handler mHandler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            if (time > 0) {
                time--;
                tvCode.setText(time + "second");
                tvCode.setTextColor(getResources().getColor(R.color.grayText));
                mHandler.sendEmptyMessageDelayed(1, 1000);
                tvCode.setEnabled(false);
            } else {
                time = 60;
                tvCode.setTextColor(getResources().getColor(R.color.red));
                tvCode.setText("get verification code");
                tvCode.setEnabled(true);
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

    private void getCode() {
        String phone = etPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            tsg("Please enter phone number");
            return;
        }
        tsg("Verification code sent successfully");
        mHandler.sendEmptyMessage(1);


    }


    @OnClick({R.id.tv_code, R.id.tv_register, R.id.tv_switch})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_code:
                getCode();
                break;
            case R.id.tv_register:
                sure();
                break;
            case R.id.tv_switch:
                finish();
                break;
        }
    }

    private void sure(){
        String phone = etPhone.getText().toString().trim();
        String code = etCode.getText().toString().trim();
        String pass = etPass.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            tsg("Please enter phone number");
            return;
        }
        if (TextUtils.isEmpty(code)) {
            tsg("Please enter SMS verification code");
            return;
        }
        if (type == BIND_PHONE && TextUtils.isEmpty(pass)) {
            tsg("Please enter your password");
            return;

        }if (type == FORGET_PASS && TextUtils.isEmpty(pass)) {
            tsg("Please enter a new login password");
            return;

        }
        if (type == FAST_LOGIN) {
            EventBus.getDefault().post(new LoginFinishSuccess());
            tsg("login successful");
            MainActivity.launch(mActivity);

        } else if (type == BIND_PHONE) {
            EventBus.getDefault().post(new LoginFinishSuccess());
            tsg("Bind successfully");
            MainActivity.launch(mActivity);
        } else if (type == FORGET_PASS) {
            tsg("Successfully modified");
            setResult();
        }

    }

    private void setResult() {
        String phone = etPhone.getText().toString().trim();
        String pass = etPass.getText().toString().trim();
        Intent intent = new Intent();
        intent.putExtra("phone", phone);
        intent.putExtra("pass", pass);
        setResult(RESULT_OK, intent);
        finish();
    }
}
