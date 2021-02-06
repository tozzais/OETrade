package com.gc.money.currency.coin;


import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

public class CoinBean extends LitePalSupport implements Serializable {
    private String uid;
    private long id;

    private double totalNum; //24小时总交易量


    @Override
    public String toString() {
        return "CoinBean{" +
                "uid=" + uid +
                ", id=" + id +
                ", coinName='" + coinName + '\'' +
                ", open=" + open +
                ", currentPrice=" + currentPrice +
                '}';
    }

    /**
     * 币名称
     */
    private String coinName;
    /**
     * 开盘价
     */
    private double open;
    /**
     * 当前价
     */
    private double currentPrice;

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    public double getOpen() {
        return open;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public double getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(double totalNum) {
        this.totalNum = totalNum;
    }
}

