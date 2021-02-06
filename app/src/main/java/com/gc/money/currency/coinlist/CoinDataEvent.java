package com.gc.money.currency.coinlist;

import com.gc.money.currency.coin.CoinBean;

import java.util.ArrayList;

public class CoinDataEvent {
    private ArrayList<CoinBean> list;
    private ArrayList<CoinBean> hotList;

    public CoinDataEvent(ArrayList<CoinBean> list, ArrayList<CoinBean> hotList) {
        this.list = list;
        this.hotList = hotList;
    }

    public ArrayList<CoinBean> getList() {
        return list;
    }

    public void setList(ArrayList<CoinBean> list) {
        this.list = list;
    }

    public ArrayList<CoinBean> getHotList() {
        return hotList;
    }

    public void setHotList(ArrayList<CoinBean> hotList) {
        this.hotList = hotList;
    }
}
