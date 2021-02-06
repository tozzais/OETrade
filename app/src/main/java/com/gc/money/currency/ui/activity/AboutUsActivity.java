package com.gc.money.currency.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.gc.money.currency.R;
import com.tozzais.baselibrary.ui.BaseActivity;

import butterknife.BindView;

public class AboutUsActivity extends BaseActivity {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_content)
    TextView tvContent;


    private String slug;
    public static void launch(Context activity, String slug) {
        Intent intent = new Intent(activity, AboutUsActivity.class);
        intent.putExtra("slug",slug);
        activity.startActivity(intent);
    }


    @Override
    public void initView(Bundle savedInstanceState) {
        setBackTitle("about us");
    }

    @Override
    public void loadData() {

    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_about;
    }



}
