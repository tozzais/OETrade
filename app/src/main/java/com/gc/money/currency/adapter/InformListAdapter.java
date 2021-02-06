package com.gc.money.currency.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.gc.money.currency.R;
import com.gc.money.currency.bean.net.InformItem;
import com.gc.money.currency.global.ImageUtil;
import com.gc.money.currency.ui.activity.InformDetailActivity;


public class InformListAdapter extends BaseQuickAdapter<InformItem, BaseViewHolder> implements LoadMoreModule {

    private int type;

    public InformListAdapter(int type) {
        super(R.layout.item_coin_inform, null);
        this.type = type;
    }


    @Override
    protected void convert(final BaseViewHolder helper, final InformItem item) {
        int position = helper.getAdapterPosition();
        ImageView iv_image = helper.getView(R.id.iv_image);
        helper.setText(R.id.tv_title,item.title)
                .setText(R.id.tv_content,item.text)
                .setText(R.id.tv_time, item.date);
        try {
            ImageUtil.load(getContext(),iv_image,item.images.images.mobile.src);
        }catch (Exception e){

        }
        helper.getView(R.id.ll_root).setOnClickListener(view -> {
            InformDetailActivity.launch(getContext(),item.slug);
        });



    }




}
