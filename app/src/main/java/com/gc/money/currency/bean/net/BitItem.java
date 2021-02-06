package com.gc.money.currency.bean.net;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class BitItem {


    private int marginMoneySmall;
    private int leverage;
    private String buyNum;
    private String color;
    private int contractType;
    private int marginPoint;
    private String icon;
    private String varietyType;
    private int beatPoorMultiple;
    private String remark;
    private int type;
    private String openMarketTime;
    private int winPriceSubBeat;
    private int lossPriceSubBeat;
    private double beatFewPoints;
    private int varietyId;
    private int exchangeStatus;
    private int maxCoinNum;
    private int areaType;
    private double overNightInterest;
    private int marketPoint;
    private String varietyName;
    private String mdReqId;
    private int invoicePrice;
    private String smallBuyNum;
    private String varietyNameTW;
    private String handsNum;
    private String coinType;
    private int marginMoneyPercent;
    private int isLabel;
    private int marginMoney;
    private int entrustedPriceAddBeat;
    private int marginBase;
    private int feesPoint;
    private int marginMoneyBig;
    private String quotaVarietyCode;
    private int exchangeId;
    private String leveRaged;
    private String unit;
    private String varietyNameCN;
    private String varietyNameUS;
    private int overNightInterestPoint;

    public static BitItem objectFromData(String str) {

        return new Gson().fromJson(str, BitItem.class);
    }

    public static BitItem objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), BitItem.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<BitItem> arrayBitItemFromData(String str) {

        Type listType = new TypeToken<ArrayList<BitItem>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<BitItem> arrayBitItemFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<BitItem>>() {
            }.getType();

            return new Gson().fromJson(jsonObject.getString(str), listType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList();


    }

    public int getMarginMoneySmall() {
        return marginMoneySmall;
    }

    public void setMarginMoneySmall(int marginMoneySmall) {
        this.marginMoneySmall = marginMoneySmall;
    }

    public int getLeverage() {
        return leverage;
    }

    public void setLeverage(int leverage) {
        this.leverage = leverage;
    }

    public String getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(String buyNum) {
        this.buyNum = buyNum;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getContractType() {
        return contractType;
    }

    public void setContractType(int contractType) {
        this.contractType = contractType;
    }

    public int getMarginPoint() {
        return marginPoint;
    }

    public void setMarginPoint(int marginPoint) {
        this.marginPoint = marginPoint;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getVarietyType() {
        return varietyType;
    }

    public void setVarietyType(String varietyType) {
        this.varietyType = varietyType;
    }

    public int getBeatPoorMultiple() {
        return beatPoorMultiple;
    }

    public void setBeatPoorMultiple(int beatPoorMultiple) {
        this.beatPoorMultiple = beatPoorMultiple;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getOpenMarketTime() {
        return openMarketTime;
    }

    public void setOpenMarketTime(String openMarketTime) {
        this.openMarketTime = openMarketTime;
    }

    public int getWinPriceSubBeat() {
        return winPriceSubBeat;
    }

    public void setWinPriceSubBeat(int winPriceSubBeat) {
        this.winPriceSubBeat = winPriceSubBeat;
    }

    public int getLossPriceSubBeat() {
        return lossPriceSubBeat;
    }

    public void setLossPriceSubBeat(int lossPriceSubBeat) {
        this.lossPriceSubBeat = lossPriceSubBeat;
    }

    public double getBeatFewPoints() {
        return beatFewPoints;
    }

    public void setBeatFewPoints(double beatFewPoints) {
        this.beatFewPoints = beatFewPoints;
    }

    public int getVarietyId() {
        return varietyId;
    }

    public void setVarietyId(int varietyId) {
        this.varietyId = varietyId;
    }

    public int getExchangeStatus() {
        return exchangeStatus;
    }

    public void setExchangeStatus(int exchangeStatus) {
        this.exchangeStatus = exchangeStatus;
    }

    public int getMaxCoinNum() {
        return maxCoinNum;
    }

    public void setMaxCoinNum(int maxCoinNum) {
        this.maxCoinNum = maxCoinNum;
    }

    public int getAreaType() {
        return areaType;
    }

    public void setAreaType(int areaType) {
        this.areaType = areaType;
    }

    public double getOverNightInterest() {
        return overNightInterest;
    }

    public void setOverNightInterest(double overNightInterest) {
        this.overNightInterest = overNightInterest;
    }

    public int getMarketPoint() {
        return marketPoint;
    }

    public void setMarketPoint(int marketPoint) {
        this.marketPoint = marketPoint;
    }

    public String getVarietyName() {
        return varietyName;
    }

    public void setVarietyName(String varietyName) {
        this.varietyName = varietyName;
    }

    public String getMdReqId() {
        return mdReqId;
    }

    public void setMdReqId(String mdReqId) {
        this.mdReqId = mdReqId;
    }

    public int getInvoicePrice() {
        return invoicePrice;
    }

    public void setInvoicePrice(int invoicePrice) {
        this.invoicePrice = invoicePrice;
    }

    public String getSmallBuyNum() {
        return smallBuyNum;
    }

    public void setSmallBuyNum(String smallBuyNum) {
        this.smallBuyNum = smallBuyNum;
    }

    public String getVarietyNameTW() {
        return varietyNameTW;
    }

    public void setVarietyNameTW(String varietyNameTW) {
        this.varietyNameTW = varietyNameTW;
    }

    public String getHandsNum() {
        return handsNum;
    }

    public void setHandsNum(String handsNum) {
        this.handsNum = handsNum;
    }

    public String getCoinType() {
        return coinType;
    }

    public void setCoinType(String coinType) {
        this.coinType = coinType;
    }

    public int getMarginMoneyPercent() {
        return marginMoneyPercent;
    }

    public void setMarginMoneyPercent(int marginMoneyPercent) {
        this.marginMoneyPercent = marginMoneyPercent;
    }

    public int getIsLabel() {
        return isLabel;
    }

    public void setIsLabel(int isLabel) {
        this.isLabel = isLabel;
    }

    public int getMarginMoney() {
        return marginMoney;
    }

    public void setMarginMoney(int marginMoney) {
        this.marginMoney = marginMoney;
    }

    public int getEntrustedPriceAddBeat() {
        return entrustedPriceAddBeat;
    }

    public void setEntrustedPriceAddBeat(int entrustedPriceAddBeat) {
        this.entrustedPriceAddBeat = entrustedPriceAddBeat;
    }

    public int getMarginBase() {
        return marginBase;
    }

    public void setMarginBase(int marginBase) {
        this.marginBase = marginBase;
    }

    public int getFeesPoint() {
        return feesPoint;
    }

    public void setFeesPoint(int feesPoint) {
        this.feesPoint = feesPoint;
    }

    public int getMarginMoneyBig() {
        return marginMoneyBig;
    }

    public void setMarginMoneyBig(int marginMoneyBig) {
        this.marginMoneyBig = marginMoneyBig;
    }

    public String getQuotaVarietyCode() {
        return quotaVarietyCode;
    }

    public void setQuotaVarietyCode(String quotaVarietyCode) {
        this.quotaVarietyCode = quotaVarietyCode;
    }

    public int getExchangeId() {
        return exchangeId;
    }

    public void setExchangeId(int exchangeId) {
        this.exchangeId = exchangeId;
    }

    public String getLeveRaged() {
        return leveRaged;
    }

    public void setLeveRaged(String leveRaged) {
        this.leveRaged = leveRaged;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getVarietyNameCN() {
        return varietyNameCN;
    }

    public void setVarietyNameCN(String varietyNameCN) {
        this.varietyNameCN = varietyNameCN;
    }

    public String getVarietyNameUS() {
        return varietyNameUS;
    }

    public void setVarietyNameUS(String varietyNameUS) {
        this.varietyNameUS = varietyNameUS;
    }

    public int getOverNightInterestPoint() {
        return overNightInterestPoint;
    }

    public void setOverNightInterestPoint(int overNightInterestPoint) {
        this.overNightInterestPoint = overNightInterestPoint;
    }
}
