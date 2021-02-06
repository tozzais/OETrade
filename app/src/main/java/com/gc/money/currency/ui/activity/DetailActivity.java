package com.gc.money.currency.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gc.money.currency.R;
import com.gc.money.currency.adapter.SellAdpter;
import com.gc.money.currency.coin.CoinBean;
import com.gc.money.currency.coin.DataHelper;
import com.gc.money.currency.coin.KChartAdapter;
import com.gc.money.currency.coin.KLineBean;
import com.gc.money.currency.coin.KLineEntity;
import com.gc.money.currency.coin.QuotationDetailBean;
import com.gc.money.currency.coin.QuotationDetailCoinBean;
import com.gc.money.currency.http.ApiManager;
import com.gc.money.currency.http.Response;
import com.github.tifezh.kchartlib.chart.KChartView;
import com.github.tifezh.kchartlib.chart.formatter.DateFormatter;
import com.google.gson.Gson;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.util.log.LogUtil;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class DetailActivity extends BaseActivity {

    @BindView(R.id.chart_view)
    KChartView chartView;
    private KChartAdapter mChartAdapter;

    @BindView(R.id.rv_sell_and_buy)
    RecyclerView rvSellAndBuy;
    private SellAdpter quotationDetailAdapter;
    private List<String> mList;
    private Gson mGson;


    private CoinBean coinBean;

    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_gain)
    TextView tvGain;
    @BindView(R.id.tv_price_high)
    TextView tvPriceHigh;
    @BindView(R.id.tv_price_low)
    TextView tvPriceLow;

    public static void launch(Context activity) {
        Intent intent = new Intent(activity, DetailActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_detail;
    }


    @Override
    public void initView(Bundle savedInstanceState) {
        mGson = new Gson();
        coinBean = (CoinBean) getIntent().getSerializableExtra("coinBean");
        setBackTitle(coinBean.getCoinName().replace("usdt", "").toUpperCase());
        tvName.setText(coinBean.getCoinName().replace("usdt", "").toUpperCase());
        //初始化k线
        mChartAdapter = new KChartAdapter();
        chartView.setAdapter(mChartAdapter);
        chartView.setDateTimeFormatter(new DateFormatter());
        chartView.setGridRows(4);
        chartView.setGridColumns(4);
        //初始化买卖
        mList = new ArrayList<>();
        quotationDetailAdapter = new SellAdpter();
        rvSellAndBuy.setNestedScrollingEnabled(false);
        rvSellAndBuy.setLayoutManager(new LinearLayoutManager(this));
        rvSellAndBuy.setAdapter(quotationDetailAdapter);
    }
    @Override
    public void loadData() {
        getChartData();
        connectSocket(coinBean.getCoinName());
    }

    /**
     * 链接socket
     */
    private WebSocketClient mClient;
    private void connectSocket(String data) {
        mClient = new WebSocketClient(URI.create("wss://app.goex24pro.com/ws.do")) {
            @Override
            public void onOpen(ServerHandshake handshakedata) {
                LogUtil.e("onOpen");
            }
            @Override
            public void onMessage(String message) {
                LogUtil.e("onMessage");
                try {
                    JSONObject jsonObject = new JSONObject(message);
                    int code = jsonObject.optInt("code");
                    if (code == 201) {
                        //  说明可以发送数据了
                        JSONObject sendJson = new JSONObject();
                        sendJson.put("code", 101);
                        sendJson.put("data", data);
                        sendJson.put("host", "app.goex24pro.com");
                        sendJson.put("kline", "5");
                        sendJson.put("language", "en_US");
                        sendJson.put("payType", "1");
                        sendJson.put("sign", "GOEX");
                        sendJson.put("token1", "");
                        sendJson.put("token2", "");
                        mClient.send(sendJson.toString());
                    } else if (code == 105) {
                        //  当前币种的买卖数据
                        LogUtil.e("onMessage"+message);
                         bean = mGson.fromJson(message, QuotationDetailBean.class);
                        mHandler.sendEmptyMessage(2);
//                        EventBus.getDefault().post(new QuotationDetailEvent(bean));
                    } else if (code == 101) {
                        //  当前币种的信息
                        detailCoinBean = mGson.fromJson(message, QuotationDetailCoinBean.class);
                        mHandler.sendEmptyMessage(3);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {

            }

            @Override
            public void onError(Exception ex) {
                if (mClient != null)
                    mClient.connect();
            }
        };
        mClient.connect();
    }


    QuotationDetailBean bean;
    QuotationDetailCoinBean detailCoinBean;
    private DecimalFormat mDecimalFormat = new DecimalFormat("#0.00");
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 2 && bean != null){
                if (mList.size() == 0) {
                    for (int i = 0; i < 6; i++) {
                        mList.add("");
                    }
                }
                quotationDetailAdapter.setmBuyList(bean.getData().getData().getBuyList());
                quotationDetailAdapter.setmSellList(bean.getData().getData().getSellList());
                quotationDetailAdapter.setNewData(mList);
            }else if (msg.what == 3 && detailCoinBean != null){
                String[] dataString = detailCoinBean.getData().getData().split(",");
                tvPriceHigh.setText("24H high " + dataString[11]);
                tvPriceLow.setText("24H low" + dataString[12]);
                //  计算涨幅
                double gain = (Double.valueOf(dataString[1]) - Double.valueOf(dataString[7]) / Double.valueOf(dataString[7]) * 100);
                if (gain > 0) {
                    tvPrice.setTextColor(getResources().getColor(R.color.color_3F9842));
                    tvGain.setTextColor(getResources().getColor(R.color.color_3F9842));
                    tvGain.setText("+" + mDecimalFormat.format(gain) + "%");
                } else {
                    tvPrice.setTextColor(getResources().getColor(R.color.color_FF0000));
                    tvGain.setTextColor(getResources().getColor(R.color.color_FF0000));
                    tvGain.setText(mDecimalFormat.format(gain) + "%");
                }
                tvPrice.setText(dataString[1]);
            }
            return false;
        }
    });

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mHandler != null) {
            mHandler.removeMessages(2);
        }
        if (mClient != null)
            mClient.close();
        mHandler = null;

    }

    /**
     * 设置表格
     */

    public void getChartData() {
        long to = System.currentTimeMillis();
        long from = to - 365 * 24 * 60 * 60 * 1000;
        new RxHttp<KLineBean>().send(ApiManager.getService("https://app.goex24pro.com/")
                        .getCoinLine(coinBean.getCoinName(), "60", from + "", to + ""),
                new Response<KLineBean>(mActivity, Response.BOTH) {
                    @Override
                    public void onNext(KLineBean result) {
                        if ("ok".equals(result.getS())) {
                            processingData(result);
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e(e.getMessage());
                    }
                });


    }
    private void processingData(KLineBean bean) {
        ArrayList<KLineEntity> data = new ArrayList<>();
        //  Close
        for (int i = 0; i < bean.getC().size(); i++) {
            KLineEntity k = new KLineEntity();
            k.Close = bean.getC().get(i);
            data.add(k);
        }
        //  High
        for (int i = 0; i < bean.getH().size(); i++) {
            KLineEntity k = data.get(i);
            k.High = bean.getH().get(i);
        }

        //  Low
        for (int i = 0; i < bean.getL().size(); i++) {
            KLineEntity k = data.get(i);
            k.Low = bean.getL().get(i);
        }

        //  Time
        for (int i = 0; i < bean.getT().size(); i++) {
            KLineEntity k = data.get(i);
            k.Date = bean.getT().get(i).toString();
        }

        //  Volume
        for (int i = 0; i < bean.getV().size(); i++) {
            KLineEntity k = data.get(i);
            k.Volume = bean.getV().get(i);
        }

        //  Open
        for (int i = 0; i < bean.getO().size(); i++) {
            KLineEntity k = data.get(i);
            k.Open = bean.getO().get(i);
        }
        DataHelper.calculate(data);
        getDataSuccess(data);

    }
    private void getDataSuccess(ArrayList<KLineEntity> data) {
        chartView.showLoading();
        mChartAdapter.addFooterData(data);
        chartView.startAnimation();
        chartView.refreshEnd();
    }


    @Override
    public void back() {
        Configuration cf= this.getResources().getConfiguration(); //获取设置的配置信息

        int ori = cf.orientation ; //获取屏幕方向

        if(ori == cf.ORIENTATION_LANDSCAPE){
            //横屏
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        }else if(ori == cf.ORIENTATION_PORTRAIT){

            //竖屏
            super.back();

        }

    }
}
