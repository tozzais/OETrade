package com.gc.money.currency.coinlist;

public class CoinDataBean {
    /**
     * "adjustToken": "11",
     * "umKey": "11",
     * "fieldCol": "black", //H5的状态栏的字体颜色 fieldCol  white(白) ,black(黑)
     * "gtSecert": "11",
     * "screenDirect": 0,
     * "umkeyIOS": "11",
     * "version": "1.1,1.2",
     * "h5Url": "https://app.zzwpx.com/", //H5业务链接地址
     * "gtMaster": "11",
     * "gtKey": "11",
     * "backgroundCol": "#FFFFFF", //H5的状态栏的背景色
     * "advOn": 0, //1显示广告
     * "advImg": "https://app.zzwpx.com/", //广告图片地址
     * "advUrl": "https://app.zzwpx.com/", //广告业务链接地址
     * "gtId": "11",
     * "um": "11",
     * "anUmengKey": "11", //安卓友盟key
     * "channelName": "google",
     * "screenOn": 1,
     * "vestCode": "MMVUT5DX",
     * "vestName": "KomTure",
     * "channelCode": "google",
     * "status": 0 //0显示H5地址
     */

    private int code;
    private DataBean data;
    private String msg;

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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "CoinDataBean{" +
                "code=" + code +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                '}';
    }

    public static class DataBean {
        /**
         * adjustToken : 11
         * umKey : 11
         * fieldCol : black
         * gtSecert : 11
         * screenDirect : 0
         * umkeyIOS : 11
         * version : 1.1,1.2
         * h5Url : https://app.zzwpx.com/
         * gtMaster : 11
         * gtKey : 11
         * backgroundCol : #FFFFFF
         * advOn : 0
         * advImg : https://app.zzwpx.com/
         * advUrl : https://app.zzwpx.com/
         * gtId : 11
         * um : 11
         * anUmengKey : 11
         * channelName : google
         * screenOn : 1
         * vestCode : MMVUT5DX
         * vestName : KomTure
         * channelCode : google
         * status : 0
         */

        private String adjustToken;
        private String umKey;
        private String fieldCol;
        private String gtSecert;
        private int screenDirect;
        private String umkeyIOS;
        private String version;
        private String h5Url;
        private String gtMaster;
        private String gtKey;
        private String backgroundCol;
        private int advOn;
        private String advImg;
        private String advUrl;
        private String gtId;
        private String um;
        private String anUmengKey;
        private String channelName;
        private int screenOn;
        private String vestCode;
        private String vestName;
        private String channelCode;
        private int status;

        public String getAdjustToken() {
            return adjustToken;
        }

        public void setAdjustToken(String adjustToken) {
            this.adjustToken = adjustToken;
        }

        public String getUmKey() {
            return umKey;
        }

        public void setUmKey(String umKey) {
            this.umKey = umKey;
        }

        public String getFieldCol() {
            return fieldCol;
        }

        public void setFieldCol(String fieldCol) {
            this.fieldCol = fieldCol;
        }

        public String getGtSecert() {
            return gtSecert;
        }

        public void setGtSecert(String gtSecert) {
            this.gtSecert = gtSecert;
        }

        public int getScreenDirect() {
            return screenDirect;
        }

        public void setScreenDirect(int screenDirect) {
            this.screenDirect = screenDirect;
        }

        public String getUmkeyIOS() {
            return umkeyIOS;
        }

        public void setUmkeyIOS(String umkeyIOS) {
            this.umkeyIOS = umkeyIOS;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getH5Url() {
            return h5Url;
        }

        public void setH5Url(String h5Url) {
            this.h5Url = h5Url;
        }

        public String getGtMaster() {
            return gtMaster;
        }

        public void setGtMaster(String gtMaster) {
            this.gtMaster = gtMaster;
        }

        public String getGtKey() {
            return gtKey;
        }

        public void setGtKey(String gtKey) {
            this.gtKey = gtKey;
        }

        public String getBackgroundCol() {
            return backgroundCol;
        }

        public void setBackgroundCol(String backgroundCol) {
            this.backgroundCol = backgroundCol;
        }

        public int getAdvOn() {
            return advOn;
        }

        public void setAdvOn(int advOn) {
            this.advOn = advOn;
        }

        public String getAdvImg() {
            return advImg;
        }

        public void setAdvImg(String advImg) {
            this.advImg = advImg;
        }

        public String getAdvUrl() {
            return advUrl;
        }

        public void setAdvUrl(String advUrl) {
            this.advUrl = advUrl;
        }

        public String getGtId() {
            return gtId;
        }

        public void setGtId(String gtId) {
            this.gtId = gtId;
        }

        public String getUm() {
            return um;
        }

        public void setUm(String um) {
            this.um = um;
        }

        public String getAnUmengKey() {
            return anUmengKey;
        }

        public void setAnUmengKey(String anUmengKey) {
            this.anUmengKey = anUmengKey;
        }

        public String getChannelName() {
            return channelName;
        }

        public void setChannelName(String channelName) {
            this.channelName = channelName;
        }

        public int getScreenOn() {
            return screenOn;
        }

        public void setScreenOn(int screenOn) {
            this.screenOn = screenOn;
        }

        public String getVestCode() {
            return vestCode;
        }

        public void setVestCode(String vestCode) {
            this.vestCode = vestCode;
        }

        public String getVestName() {
            return vestName;
        }

        public void setVestName(String vestName) {
            this.vestName = vestName;
        }

        public String getChannelCode() {
            return channelCode;
        }

        public void setChannelCode(String channelCode) {
            this.channelCode = channelCode;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "adjustToken='" + adjustToken + '\'' +
                    ", umKey='" + umKey + '\'' +
                    ", fieldCol='" + fieldCol + '\'' +
                    ", gtSecert='" + gtSecert + '\'' +
                    ", screenDirect=" + screenDirect +
                    ", umkeyIOS='" + umkeyIOS + '\'' +
                    ", version='" + version + '\'' +
                    ", h5Url='" + h5Url + '\'' +
                    ", gtMaster='" + gtMaster + '\'' +
                    ", gtKey='" + gtKey + '\'' +
                    ", backgroundCol='" + backgroundCol + '\'' +
                    ", advOn=" + advOn +
                    ", advImg='" + advImg + '\'' +
                    ", advUrl='" + advUrl + '\'' +
                    ", gtId='" + gtId + '\'' +
                    ", um='" + um + '\'' +
                    ", anUmengKey='" + anUmengKey + '\'' +
                    ", channelName='" + channelName + '\'' +
                    ", screenOn=" + screenOn +
                    ", vestCode='" + vestCode + '\'' +
                    ", vestName='" + vestName + '\'' +
                    ", channelCode='" + channelCode + '\'' +
                    ", status=" + status +
                    '}';
        }
    }
}




