package com.gc.money.currency.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.gc.money.currency.R;
import com.gc.money.currency.adapter.CoinListAdpter;
import com.gc.money.currency.coin.CoinBean;
import com.tozzais.baselibrary.ui.BaseListFragment;
import com.tozzais.baselibrary.util.log.LogUtil;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CoinListFragment extends BaseListFragment<CoinBean> {


    @BindView(R.id.tv_list_name)
    TextView tv_list_name;

    @Override
    public int setLayout() {
        return R.layout.fragment_coin_list;
    }

    private List<CoinBean> normalList;
    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        normalList = new ArrayList<>();
        tv_list_name.setText("Quotes");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new CoinListAdpter();
        mRecyclerView.setAdapter(mAdapter);
        setEmptyView("No data");
    }

    @Override
    public void loadData() {
        super.loadData();
       getData();

    }

    private Handler mHandler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 1){
                setData(true,mList);
            }
            return false;
        }
    });

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mHandler != null) {
            mHandler.removeMessages(1);
        }
        mHandler = null;

    }

    @Override
    public void initListener() {
        super.initListener();
        mAdapter.setOnItemClickListener(((baseQuickAdapter, view, position) -> {


        }));
    }


    private WebSocketClient mClient;
    /**
     * 所有币的种类
     */
    private ArrayList<CoinBean> mList = new ArrayList<>();
    /**
     * 所有币种是否包含
     */
    private boolean mIsContains;
    /**
     * 热门币的种类
     */
    private ArrayList<CoinBean> mHotList = new ArrayList<>();
    /**
     * 热门币种是否包含
     */
    private boolean mIsHotContains;
    private void getData(){
        LogUtil.e("getData");
        mClient = new WebSocketClient(URI.create("wss://app.goex24pro.com/ws.do")) {

            @Override
            public void onOpen(ServerHandshake handshakedata) {
                LogUtil.e("onOpen");
            }
            @Override
            public void onMessage(String message) {
                LogUtil.e("onMessage"+message);
                try {
                    JSONObject jsonObject = new JSONObject(message);
                    int code = jsonObject.optInt("code");
                    if (code == 201) {
                        //  说明可以发送数据了
                        JSONObject sendJson = new JSONObject();
                        sendJson.put("code", 103);
                        sendJson.put("host", "app.goex24pro.com");
                        sendJson.put("language", "en_US");
                        sendJson.put("payType", "1");
                        sendJson.put("sign", "GOEX");
                        sendJson.put("token1", "");
                        sendJson.put("token2", "");
                        mClient.send(sendJson.toString());
                    } else if (code == 103) {
                        //  说明发送成功，并开始接受数据
                        JSONObject data = jsonObject.optJSONObject("data");
                        if (data != null) {
                            String dataString = data.optString("data");
                            if (dataString.contains("usdt")) {
                                String[] split = dataString.split(",");
                                //  所有币种
                                if (mList.isEmpty()) {
                                    CoinBean coinBean = new CoinBean();
                                    coinBean.setCoinName(split[0]);
                                    coinBean.setOpen(Double.parseDouble(split[7]));
                                    coinBean.setCurrentPrice(Double.parseDouble(split[1]));
                                    coinBean.setTotalNum(Double.parseDouble(split[9]));
                                    mList.add(coinBean);
                                } else {
                                    for (int i = 0; i < mList.size(); i++) {
                                        CoinBean coinBean = mList.get(i);
                                        if (split[0].equalsIgnoreCase(coinBean.getCoinName())) {
                                            mIsContains = true;
                                            //  如果之前集合中已经存在了该币种，就替换
                                            CoinBean bean = new CoinBean();
                                            bean.setCoinName(split[0]);
                                            bean.setOpen(Double.parseDouble(split[7]));
                                            bean.setCurrentPrice(Double.parseDouble(split[1]));
                                            bean.setTotalNum(Double.parseDouble(split[9]));
                                            mList.set(i, bean);
                                            break;
                                        }
                                    }
                                    if (!mIsContains) {
                                        CoinBean bean = new CoinBean();
                                        bean.setCoinName(split[0]);
                                        bean.setOpen(Double.parseDouble(split[7]));
                                        bean.setCurrentPrice(Double.parseDouble(split[1]));
                                        bean.setTotalNum(Double.parseDouble(split[9]));
                                        mList.add(bean);
                                        mIsContains = false;
                                    }
                                }
                                //  热门币种
                                if (dataString.contains("eos") || dataString.contains("xrp") ||
                                        dataString.contains("bch") || dataString.contains("etc") ||
                                        dataString.contains("ltc")) {
                                    if (mHotList.isEmpty()) {
                                        CoinBean coinBean = new CoinBean();
                                        coinBean.setCoinName(split[0]);
                                        coinBean.setOpen(Double.parseDouble(split[7]));
                                        coinBean.setCurrentPrice(Double.parseDouble(split[1]));
                                        coinBean.setTotalNum(Double.parseDouble(split[9]));
                                        mHotList.add(coinBean);
                                    } else {
                                        for (int i = 0; i < mHotList.size(); i++) {
                                            CoinBean coinBean = mHotList.get(i);
                                            if (split[0].equalsIgnoreCase(coinBean.getCoinName())) {
                                                mIsHotContains = true;
                                                //  如果之前集合中已经存在了该币种，就替换
                                                CoinBean bean = new CoinBean();
                                                bean.setCoinName(split[0]);
                                                bean.setOpen(Double.parseDouble(split[7]));
                                                bean.setCurrentPrice(Double.parseDouble(split[1]));
                                                coinBean.setTotalNum(Double.parseDouble(split[9]));
                                                mHotList.set(i, bean);
                                                break;
                                            }
                                        }
                                        if (!mIsHotContains) {
                                            CoinBean bean = new CoinBean();
                                            bean.setCoinName(split[0]);
                                            bean.setOpen(Double.parseDouble(split[7]));
                                            bean.setCurrentPrice(Double.parseDouble(split[1]));
                                            bean.setTotalNum(Double.parseDouble(split[9]));
                                            mHotList.add(bean);
                                            mIsHotContains = false;
                                        }
                                    }
                                }
                                mHandler.sendEmptyMessage(1);
//                                showContent();
//                                LogUtil.e("mList.size = "+mList.size()+",mHotList.size() = "+mHotList.size());
//                                mAdapter.setNewData(mHotList);
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                LogUtil.e("onClose");
            }

            @Override
            public void onError(Exception ex) {
                LogUtil.e("onError"+ex.getMessage());
            }
        };
        mClient.connect();
    }


}
