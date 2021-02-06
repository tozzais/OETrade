//package com.gaocheng.digital.coin.coinlist;
//
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.LinearLayout;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//
//import com.gaocheng.digital.coin.R;
//import com.gaocheng.digital.coin.coin.CoinBean;
//import com.gaocheng.digital.coin.coin.CoinDetailActivity;
//import com.tozzais.baselibrary.util.CommonUtils;
//
//import org.greenrobot.eventbus.EventBus;
//import org.greenrobot.eventbus.Subscribe;
//import org.greenrobot.eventbus.ThreadMode;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//import butterknife.Unbinder;
//import io.reactivex.Observable;
//import io.reactivex.android.schedulers.AndroidSchedulers;
//import io.reactivex.disposables.Disposable;
//import io.reactivex.functions.Consumer;
//import io.reactivex.schedulers.Schedulers;
//
///**
// * A simple {@link Fragment} subclass.
// */
//public class MarketsFragment extends Fragment {
//
//
//    @BindView(R.id.tv_title)
//    TextView tvTitle;
//    @BindView(R.id.tv_right)
//    TextView tvRight;
//    @BindView(R.id.recycler_coin)
//    RecyclerView recyclerCoin;
//    @BindView(R.id.progressbar)
//    ProgressBar progressbar;
//    @BindView(R.id.ll_pro)
//    LinearLayout llPro;
//
//    private NormalCoinAdapter normalCoinAdapter;
//
//    private Intent intent;
//    private Disposable disposable;
//    private List<CoinBean> normalList;
//    private View contentView;
//    private Unbinder unbinder;
//
//    public MarketsFragment() {
//        // Required empty public constructor
//    }
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        contentView = inflater.inflate(R.layout.fragment_markets, container, false);
//        unbinder = ButterKnife.bind(this, contentView);
//        return contentView;
//    }
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EventBus.getDefault().register(this);
//    }
//
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        initRecycler();
//        initService();
//        initLoading();
//    }
//
//    public void initLoading() {
//        llPro.setVisibility(View.VISIBLE);
//    }
//
//    private void initService() {
//        intent = new Intent(getContext(), CoinSocketService.class);
//        if (getActivity() != null)
//            getActivity().startService(intent);
//        disposable = Observable.interval(1, TimeUnit.SECONDS)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<Long>() {
//                    @Override
//                    public void accept(Long aLong) throws Exception {
//                        if (normalList.size() != 0) {
//                            llPro.setVisibility(View.GONE);
//                        }
//                        Collections.sort(normalList, new Comparator<CoinBean>() {
//                            @Override
//                            public int compare(CoinBean o1, CoinBean o2) {
//                                return Double.compare(o2.getCurrentPrice(), o1.getCurrentPrice());
//                            }
//                        });
//                        normalCoinAdapter.setNewData(normalList);
//                        normalCoinAdapter.notifyDataSetChanged();
//                    }
//                });
//    }
//
//    private void initRecycler() {
//        normalList = new ArrayList<>();
//        recyclerCoin.setLayoutManager(new LinearLayoutManager(getContext()));
//        normalCoinAdapter = new NormalCoinAdapter(normalList);
//        recyclerCoin.setAdapter(normalCoinAdapter);
//        normalCoinAdapter.setOnItemClickListener((adapter, view, postion) -> {
//            Intent intent = new Intent(getContext(), CoinDetailActivity.class);
//            intent.putExtra("coinBean", normalCoinAdapter.getData().get(postion));
//            startActivity(intent);
//        });
//    }
//
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void getCoinData(CoinDataEvent event) {
//        normalList = event.getList();
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        unbinder.unbind();
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        EventBus.getDefault().unregister(this);
//        if (getActivity() != null && intent != null)
//            getActivity().stopService(intent);
////        if (disposable != null && !disposable.isDisposed())
////            disposable.dispose();
//    }
//
//    @OnClick(R.id.tv_right)
//    public void onViewClicked() {
////        if (CommonUtils.isFastClick())
////            return;
////        startActivity(new Intent(getContext(), MarketFavoActivity.class));
//    }
//}
