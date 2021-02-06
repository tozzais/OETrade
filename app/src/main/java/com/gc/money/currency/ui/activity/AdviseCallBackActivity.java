package com.gc.money.currency.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.EditText;

import com.gc.money.currency.R;
import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.util.progress.LoadingUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class AdviseCallBackActivity extends BaseActivity {


    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.et_contact_information)
    EditText etContactInformation;

    public static void launch(Context activity) {
        Intent intent = new Intent(activity, AdviseCallBackActivity.class);
        activity.startActivity(intent);
    }


    @Override
    public void initView(Bundle savedInstanceState) {
        setBackTitle("Feedback");
    }

    @Override
    public void loadData() {

    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_callback;
    }


    @OnClick(R.id.tv_commit)
    public void onClick() {
        commit();
    }

    private void commit(){
        String content = etContent.getText().toString().trim();
        String contact = etContactInformation.getText().toString().trim();
        if (TextUtils.isEmpty(content)){
            tsg("Please enter feedback");
            return;
        }
        LoadingUtils.show(mContext);
        new Handler().postDelayed(()->{
            tsg("Submitted successfully");
            finish();

        },1000);
    }

    private void success(){
        tsg("Submitted successfully");
        finish();
    }
}
