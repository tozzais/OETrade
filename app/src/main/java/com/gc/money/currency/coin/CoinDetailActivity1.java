//package com.gaocheng.digital.coin.coin;
//
//import android.content.Intent;
//import android.graphics.drawable.AnimationDrawable;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.gaocheng.digital.coin.R;
//import com.github.tifezh.kchartlib.chart.KChartView;
//import com.github.tifezh.kchartlib.chart.formatter.DateFormatter;
//
//import org.greenrobot.eventbus.EventBus;
//import org.greenrobot.eventbus.Subscribe;
//import org.greenrobot.eventbus.ThreadMode;
//import org.litepal.LitePal;
//
//import java.text.DecimalFormat;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//import retrofit2.Retrofit;
//import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
//import retrofit2.converter.gson.GsonConverterFactory;
//import rx.Observable;
//import rx.android.schedulers.AndroidSchedulers;
//import rx.schedulers.Schedulers;
//
//public class CoinDetailActivity1 extends AppCompatActivity {
//
//    @BindView(R.id.tv_name)
//    TextView tvName;
//    @BindView(R.id.tv_price)
//    TextView tvPrice;
//    @BindView(R.id.tv_gain)
//    TextView tvGain;
//    @BindView(R.id.tv_price_high)
//    TextView tvPriceHigh;
//    @BindView(R.id.tv_price_low)
//    TextView tvPriceLow;
//    @BindView(R.id.re_top)
//    RelativeLayout reTop;
//    @BindView(R.id.re_bom)
//    RelativeLayout reBom;
//    @BindView(R.id.rv_sell_and_buy)
//    RecyclerView rvSellAndBuy;
//    @BindView(R.id.ll_loading)
//    LinearLayout llLoading;
//
//    private QuotationDetailAdapter quotationDetailAdapter;
//    private CoinBean coinBean;
//    private KChartAdapter mChartAdapter;
//    private Intent intent;
//    private Disposable disposable;
//    private List<String> mList;
//    private AnimationDrawable annotation;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_coin_detail);
//        ButterKnife.bind(this);
//        EventBus.getDefault().register(this);
//        initView();
//        checkIsAdd();
//        getData();
//    }
//
//    private void initView() {
////        tvRight.setVisibility(View.VISIBLE);
////        tvRight.setText("+ Add to Fav");
//        mList = new ArrayList<>();
//        coinBean = (CoinBean) getIntent().getSerializableExtra("coinBean");
////        tvTitle.setText(coinBean.getCoinName().replace("usdt", "").toUpperCase());
//        tvName.setText(coinBean.getCoinName().replace("usdt", "").toUpperCase());
//        quotationDetailAdapter = new QuotationDetailAdapter(mList);
//        rvSellAndBuy.setNestedScrollingEnabled(false);
//        rvSellAndBuy.setLayoutManager(new LinearLayoutManager(this));
//        rvSellAndBuy.setAdapter(quotationDetailAdapter);
//        refreshData();
////        annotation = (AnimationDrawable) ivLoading.getBackground();
////        annotation.start();
//    }
//
//    private void refreshData() {
//        disposable = Observable.interval(1, TimeUnit.SECONDS)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<Long>() {
//                    @Override
//                    public void accept(Long aLong) throws Exception {
//                        if (quotationDetailAdapter.getmBuyList() != null) {
//                            if (mList.size() == 0) {
//                                for (int i = 0; i < 6; i++) {
//                                    mList.add("");
//                                }
//                            }
//                            if (annotation != null && annotation.isRunning()) {
//                                annotation.stop();
//                                llLoading.setVisibility(View.GONE);
//                            }
//                            reBom.setVisibility(View.VISIBLE);
//                            quotationDetailAdapter.notifyDataSetChanged();
//                        }
//                    }
//                });
//    }
//
//
//    private long id;
//
//    private void checkIsAdd() {
//        LitePal.where("coinName =?", coinBean.getCoinName()).findAsync(CoinBean.class)
//                .listen(coinList -> {
//                    if (coinList.size() > 0) {
//                        id = coinList.get(0).getId();
////                        tvRight.setText("Delete from Fav");
//                    } else {
////                        tvRight.setText("+ Add to Fav");
//                    }
//                });
//    }
//
//    public void getData() {
//        intent = new Intent(CoinDetailActivity1.this, QuotationDetailSocketService.class);
//        intent.putExtra("extra_key_quotation_data", coinBean.getCoinName());
//        startService(intent);
//
//    }
//
//
//    /**
//     * 获取到详情买卖的数据
//     */
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onQuotationDetailEvent(QuotationDetailEvent event) {
//        quotationDetailAdapter.setmBuyList(event.bean.getData().getData().getBuyList());
//        quotationDetailAdapter.setmSellList(event.bean.getData().getData().getSellList());
//    }
//
//    private DecimalFormat mDecimalFormat = new DecimalFormat("#0.00");
//
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onQuotationDetailCoinEvent(QuotationDetailCoinEvent event) {
//        String[] dataString = event.bean.getData().getData().split(",");
//        tvPriceHigh.setText("24H high " + dataString[11]);
//        tvPriceLow.setText("24H low" + dataString[12]);
//        //  计算涨幅
//        double gain = (Double.valueOf(dataString[1]) - Double.valueOf(dataString[7]) / Double.valueOf(dataString[7]) * 100);
//        if (gain > 0) {
//            tvPrice.setTextColor(getResources().getColor(R.color.color_3F9842));
//            tvGain.setTextColor(getResources().getColor(R.color.color_3F9842));
//            tvGain.setText("+" + mDecimalFormat.format(gain) + "%");
//        } else {
//            tvPrice.setTextColor(getResources().getColor(R.color.color_FF0000));
//            tvGain.setTextColor(getResources().getColor(R.color.color_FF0000));
//            tvGain.setText(mDecimalFormat.format(gain) + "%");
//        }
//        tvPrice.setText(dataString[1]);
//        reTop.setVisibility(View.VISIBLE);
//        if (annotation != null && annotation.isRunning()) {
//            annotation.stop();
//            llLoading.setVisibility(View.GONE);
//        }
//    }
//
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        EventBus.getDefault().unregister(this);
//        stopService(intent);
//        if (disposable != null && !disposable.isDisposed()) {
//            disposable.dispose();
//        }
//        if (annotation != null && annotation.isRunning()) {
//            annotation.stop();
//            llLoading.setVisibility(View.GONE);
//        }
//    }
//
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void loginSuccess(String msg) {
//        if (msg.equals("login_success")) {
//            checkIsAdd();
//        }
//    }
//
//}
