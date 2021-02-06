package com.gc.money.currency.coin;

import java.io.Serializable;
import java.util.ArrayList;

public class QuotationDetailBean implements Serializable {

    /**
     * code : 105
     * data : {"code":200,"data":{"buyList":[[5429.65,0.077496,0.077496],[5429.86,0.086061,0.163557],[5429.9,0.096718,0.260275],[5430,0.007612,0.267887],[5430.14,0.001,0.268887],[5430.28,0.005,0.273887],[5430.38,0.051131,0.325018],[5430.47,0.996,1.321018],[5430.54,0.001,1.322018],[5430.69,0.2017,1.523718],[5430.88,0.01131,1.535028],[5430.97,1.8,3.335028],[5431.03,0.008818,3.343846],[5431.29,0.015,3.358846],[5431.38,1.8,5.158846],[5431.97,1.8,6.958846]],"marketPoint":2,"sellList":[[5425.85,0.11804,0.11804],[5425.64,0.506364,0.624404],[5425.6,1.8,2.424404],[5425.5,0.099999,2.524403],[5425.36,0.2,2.724403],[5425.22,2.280602,5.005005],[5425.12,0.02,5.025005],[5425.03,8.391196,13.416201],[5424.96,0.001,13.417201],[5424.81,0.2661,13.683301],[5424.62,0.013733,13.697034],[5424.53,1.8,15.497034],[5424.47,0.096718,15.593752],[5424.21,1.8,17.393752],[5424.12,0.4607,17.854452],[5423.53,0.2,18.054452]],"varietyType":"btcusdt"}}
     */

    private int code;
    private DataBeanX data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public static class DataBeanX implements Serializable {
        /**
         * code : 200
         * data : {"buyList":[[5429.65,0.077496,0.077496],[5429.86,0.086061,0.163557],[5429.9,0.096718,0.260275],[5430,0.007612,0.267887],[5430.14,0.001,0.268887],[5430.28,0.005,0.273887],[5430.38,0.051131,0.325018],[5430.47,0.996,1.321018],[5430.54,0.001,1.322018],[5430.69,0.2017,1.523718],[5430.88,0.01131,1.535028],[5430.97,1.8,3.335028],[5431.03,0.008818,3.343846],[5431.29,0.015,3.358846],[5431.38,1.8,5.158846],[5431.97,1.8,6.958846]],"marketPoint":2,"sellList":[[5425.85,0.11804,0.11804],[5425.64,0.506364,0.624404],[5425.6,1.8,2.424404],[5425.5,0.099999,2.524403],[5425.36,0.2,2.724403],[5425.22,2.280602,5.005005],[5425.12,0.02,5.025005],[5425.03,8.391196,13.416201],[5424.96,0.001,13.417201],[5424.81,0.2661,13.683301],[5424.62,0.013733,13.697034],[5424.53,1.8,15.497034],[5424.47,0.096718,15.593752],[5424.21,1.8,17.393752],[5424.12,0.4607,17.854452],[5423.53,0.2,18.054452]],"varietyType":"btcusdt"}
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

        public static class DataBean implements Serializable{
            /**
             * buyList : [[5429.65,0.077496,0.077496],[5429.86,0.086061,0.163557],[5429.9,0.096718,0.260275],[5430,0.007612,0.267887],[5430.14,0.001,0.268887],[5430.28,0.005,0.273887],[5430.38,0.051131,0.325018],[5430.47,0.996,1.321018],[5430.54,0.001,1.322018],[5430.69,0.2017,1.523718],[5430.88,0.01131,1.535028],[5430.97,1.8,3.335028],[5431.03,0.008818,3.343846],[5431.29,0.015,3.358846],[5431.38,1.8,5.158846],[5431.97,1.8,6.958846]]
             * marketPoint : 2
             * sellList : [[5425.85,0.11804,0.11804],[5425.64,0.506364,0.624404],[5425.6,1.8,2.424404],[5425.5,0.099999,2.524403],[5425.36,0.2,2.724403],[5425.22,2.280602,5.005005],[5425.12,0.02,5.025005],[5425.03,8.391196,13.416201],[5424.96,0.001,13.417201],[5424.81,0.2661,13.683301],[5424.62,0.013733,13.697034],[5424.53,1.8,15.497034],[5424.47,0.096718,15.593752],[5424.21,1.8,17.393752],[5424.12,0.4607,17.854452],[5423.53,0.2,18.054452]]
             * varietyType : btcusdt
             */

            private int marketPoint;
            private String varietyType;
            private ArrayList<ArrayList<Double>> buyList;
            private ArrayList<ArrayList<Double>> sellList;

            public int getMarketPoint() {
                return marketPoint;
            }

            public void setMarketPoint(int marketPoint) {
                this.marketPoint = marketPoint;
            }

            public String getVarietyType() {
                return varietyType;
            }

            public void setVarietyType(String varietyType) {
                this.varietyType = varietyType;
            }

            public ArrayList<ArrayList<Double>> getBuyList() {
                return buyList;
            }

            public void setBuyList(ArrayList<ArrayList<Double>> buyList) {
                this.buyList = buyList;
            }

            public ArrayList<ArrayList<Double>> getSellList() {
                return sellList;
            }

            public void setSellList(ArrayList<ArrayList<Double>> sellList) {
                this.sellList = sellList;
            }
        }
    }
}


