package com.gc.money.currency.coin;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface   Api {
    /**
     * 获取K线图的数据 60
     */
    @GET("https://app.goex24.com/quota/tradingView/history")
    Call<KLineBean> getCoinLine(
            @Query("symbol") String symbol,
            @Query("resolution") String resolution,
            @Query("from") String from,
            @Query("to") String to);


}
