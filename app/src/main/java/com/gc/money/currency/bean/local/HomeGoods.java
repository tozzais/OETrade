package com.gc.money.currency.bean.local;

public class HomeGoods {

    //精选
    public static final int FEATURED = 0;
    //拼团
    public static final int FIGHT = 1;
    //进口
    public static final int INLET = 2;
    //实惠
    public static final int AFFORDABLE = 3;

    public int type;

    public HomeGoods(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
