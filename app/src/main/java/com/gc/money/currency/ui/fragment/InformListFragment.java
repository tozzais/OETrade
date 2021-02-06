package com.gc.money.currency.ui.fragment;

import android.os.Bundle;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.gc.money.currency.R;
import com.gc.money.currency.adapter.InformListAdapter;
import com.gc.money.currency.bean.net.InformItem;
import com.gc.money.currency.http.ApiManager;
import com.gc.money.currency.http.BaseListResult;
import com.gc.money.currency.http.HttpUrl;
import com.gc.money.currency.http.Response;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseListFragment;

import butterknife.BindView;

public class InformListFragment extends BaseListFragment<InformItem> {


    @BindView(R.id.tv_list_name)
    TextView tv_list_name;

    @Override
    public int setLayout() {
        return R.layout.fragment_coin_list;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        tv_list_name.setText("Information");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new InformListAdapter(1);
        mRecyclerView.setAdapter(mAdapter);

        setEmptyView("No data");

    }

    @Override
    public void loadData() {
        super.loadData();
        new RxHttp<BaseListResult<InformItem>>().send(ApiManager.getService(HttpUrl.inform_url).getInform(),
                new Response<BaseListResult<InformItem>>(isLoad,mActivity) {
                    @Override
                    public void onNext(BaseListResult<InformItem> result) {
                        showContent();
                        setData(result.posts);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        showError(e.getMessage());
                    }
                });
    }



    @Override
    public void initListener() {
        super.initListener();
        mAdapter.getLoadMoreModule().setEnableLoadMore(false);
        mAdapter.setOnItemClickListener(((baseQuickAdapter, view, position) -> {


        }));
    }




}
