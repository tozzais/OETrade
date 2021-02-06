package com.gc.money.currency.adapter;

import android.content.Intent;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.gc.money.currency.R;
import com.gc.money.currency.coin.CoinBean;
import com.gc.money.currency.ui.activity.DetailActivity;

import java.text.DecimalFormat;


public class CoinListAdpter extends BaseQuickAdapter<CoinBean, BaseViewHolder> implements LoadMoreModule {

    private DecimalFormat decimalFormat = new DecimalFormat("#0.00");
    public CoinListAdpter() {
        super(R.layout.item_coin, null);
    }


    @Override
    protected void convert(final BaseViewHolder holder, final CoinBean bean) {
        int position = holder.getAdapterPosition();
//        helper.setText(R.id.tv_name,item.getMdReqId()+"/"+item.getCoinType())
//                .setText(R.id.tv_id_number,""+item.getMarginMoney());
        holder.setText(R.id.tv_coin, bean.getCoinName().replace("usdt", "").toUpperCase() ); //+ "/USDT"
        holder.setText(R.id.tv_price, decimalFormat.format(bean.getCurrentPrice()) + "");
        holder.setText(R.id.tv_price_total, bean.getTotalNum() + "");
        double gain = (bean.getCurrentPrice() - bean.getOpen()) / bean.getOpen() * 100;
        if (bean.getCurrentPrice() - bean.getOpen() > 0) {
            holder.setTextColor(R.id.tv_gain, getContext().getResources().getColor(R.color.color_3F9842));
            holder.setText(R.id.tv_gain, decimalFormat.format(gain) + "%");
        } else {
            holder.setTextColor(R.id.tv_gain, getContext().getResources().getColor(R.color.color_D1302));
            holder.setText(R.id.tv_gain, decimalFormat.format(gain));
            holder.setText(R.id.tv_gain, decimalFormat.format(gain) + "%");
        }

        holder.getView(R.id.ll_root).setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), DetailActivity.class);
            intent.putExtra("coinBean", bean);
            getContext().startActivity(intent);
        });




    }




}
