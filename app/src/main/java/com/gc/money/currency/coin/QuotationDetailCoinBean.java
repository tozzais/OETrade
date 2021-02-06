package com.gc.money.currency.coin;

import java.io.Serializable;

public class QuotationDetailCoinBean implements Serializable {

    /**
     * code : 101
     * data : {"code":200,"data":"btcusdt,6144.2,6140.77,1.0,6140.77,1.0,6144.2,5412.57,6142.52,143668.59047595,7.1058,6399.08,5252.55,178.00648905771973,1584667783946,null,null,0.0"}
     */

    private int code;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * code : 200
         * data : btcusdt,6144.2,6140.77,1.0,6140.77,1.0,6144.2,5412.57,6142.52,143668.59047595,7.1058,6399.08,5252.55,178.00648905771973,1584667783946,null,null,0.0
         */

        private int code;
        private String data;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }
    }
}




