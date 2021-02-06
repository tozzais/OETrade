package com.gc.money.currency.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.gc.money.currency.R;
import com.gc.money.currency.bean.InformDetail;
import com.gc.money.currency.http.ApiManager;
import com.gc.money.currency.http.HttpUrl;
import com.gc.money.currency.http.Response;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseActivity;

import butterknife.BindView;

public class InformDetailActivity extends BaseActivity {


    @BindView(R.id.tv_title1)
    TextView tvTitle1;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_content)
    TextView tvContent;


    private String slug;
    public static void launch(Context activity, String slug) {
        Intent intent = new Intent(activity, InformDetailActivity.class);
        intent.putExtra("slug",slug);
        activity.startActivity(intent);
    }


    @Override
    public void initView(Bundle savedInstanceState) {
        setBackTitle("Information Details");
    }

    @Override
    public void loadData() {
        String slug = getIntent().getStringExtra("slug");
        new RxHttp<InformDetail>().send(ApiManager.getService(HttpUrl.inform_url).getInformDetail(slug),
                new Response<InformDetail>(isLoad,mActivity) {
                    @Override
                    public void onNext(InformDetail result) {
                        tvTitle1.setText(result.excerpt);
                        tvTime.setText(result.updated);
                        tvContent.setText(result.amp);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        showError(e.getMessage());
                    }
                });

    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_informdetail;
    }



}
