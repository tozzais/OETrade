package com.gc.money.currency.http;



import com.gc.money.currency.bean.GoogleTokenBean;
import com.gc.money.currency.bean.InformDetail;
import com.gc.money.currency.bean.VestResult;
import com.gc.money.currency.bean.net.BitItem;
import com.gc.money.currency.bean.net.InformItem;
import com.gc.money.currency.coin.KLineBean;

import java.util.TreeMap;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;


/**
 * Created by jumpbox on 16/5/2.
 */
public interface ApiService {

    //登录
    @POST(HttpUrl.login)
    Observable<BaseResult>
    getlogin(@QueryMap TreeMap<String, String> map);

    //资讯列表
    @GET("wp-json/v1/articles/format/news/1?mode=list")
    Observable<BaseListResult<InformItem>>
    getInform();
    //资讯详情
    @GET("wp-json/v1/article/{slug}")
    Observable<InformDetail>
    getInformDetail(@Path("slug") String slug);

    //bit列表
    @GET("/order/order/getHallVariety.do?type=3&areaType=0&pCode=7sedj&sign=6f5f5a065a622f81")
    Observable<BaseListData<BitItem>>
    getBitList();


    @GET("/admin/client/vestSign.do")
    Observable<VestResult>
    getVest(@QueryMap TreeMap<String, String> map);

    @GET("/user/google/doLogin2.do")
    Observable<BaseResult<GoogleTokenBean>>
    getGoogleToken(@QueryMap TreeMap<String, String> map);


    @GET("quota/tradingView/history")
    Observable<KLineBean> getCoinLine(
            @Query("symbol") String symbol,
            @Query("resolution") String resolution,
            @Query("from") String from,
            @Query("to") String to);


}
