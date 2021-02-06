package com.gc.money.currency.coinlist;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.gc.money.currency.R;
import com.gc.money.currency.coin.CoinBean;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.DecimalFormat;
import java.util.List;

public class NormalCoinAdapter extends BaseQuickAdapter<CoinBean, BaseViewHolder> {
    public NormalCoinAdapter(@Nullable List<CoinBean> data) {
        super(R.layout.item_normal_coin, data);
    }

    private DecimalFormat decimalFormat = new DecimalFormat("#0.00");

    private static final String TAG = "NormalCoinAdapter";

    @Override
    protected void convert(@NotNull BaseViewHolder holder, CoinBean bean) {
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
    }
}


