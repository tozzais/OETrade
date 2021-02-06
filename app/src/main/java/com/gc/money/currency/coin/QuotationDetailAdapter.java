package com.gc.money.currency.coin;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.gc.money.currency.R;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class QuotationDetailAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private ArrayList<ArrayList<Double>> mSellList;
    private ArrayList<ArrayList<Double>> mBuyList;

    public ArrayList<ArrayList<Double>> getmSellList() {
        return mSellList;
    }

    public void setmSellList(ArrayList<ArrayList<Double>> mSellList) {
        this.mSellList = mSellList;
    }

    public ArrayList<ArrayList<Double>> getmBuyList() {
        return mBuyList;
    }

    public void setmBuyList(ArrayList<ArrayList<Double>> mBuyList) {
        this.mBuyList = mBuyList;
    }

    public QuotationDetailAdapter(@Nullable List<String> data) {
        super(R.layout.item_coin_detail, data);
    }

    /**
     * 购买指数格式化
     */
    private DecimalFormat mDecimalFormatIndex = new DecimalFormat("#0.000000");
    private DecimalFormat mDecimalFormatPrice = new DecimalFormat("#0.00");

    @Override
    protected void convert(@NotNull BaseViewHolder holder, String bean) {
        holder.setText(R.id.tv_buy_no, 6 - holder.getAdapterPosition() + "")
                .setText(R.id.tv_buy_index, mDecimalFormatIndex.format(mBuyList.get(holder.getAdapterPosition()).get(1)))
                .setText(R.id.tv_buy_price, mDecimalFormatPrice.format(mBuyList.get(holder.getAdapterPosition()).get(0)))
                .setText(R.id.tv_sell_no, 6 - holder.getAdapterPosition() + "")
                .setText(R.id.tv_sell_index, mDecimalFormatIndex.format(mSellList.get(holder.getAdapterPosition()).get(1)))
                .setText(R.id.tv_sell_price, mDecimalFormatPrice.format(mSellList.get(holder.getAdapterPosition()).get(0)));

    }


}

