package com.gc.money.currency.bean.local;


public class CartItem {








    public boolean isCheck;

    public CartItem(boolean isCheck) {
        this.isCheck = isCheck;
    }

    public CartItem(boolean isCheck, double price, int num) {
        this.isCheck = isCheck;
        this.price = price;
        this.num = num;
    }


    public int cart_id;
    public int product_id;
    public int sku_id;
    public int other_id;
    public int area_id;
    public String logo;
    public String product_name;
    public String norm;
    public String other_name;
    public double price;
    public int num;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }


}
