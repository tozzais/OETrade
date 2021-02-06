package com.gc.money.currency.coin;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;

public class QuotationDetailSocketService extends Service {
    private WebSocketClient mClient;
    private Gson mGson;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mGson = new Gson();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String data = intent.getStringExtra("extra_key_quotation_data");
        connectSocket(data);
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 链接socket
     */
    private void connectSocket(String data) {
        mClient = new WebSocketClient(URI.create("wss://app.goex24.com/ws.do")) {
            @Override
            public void onOpen(ServerHandshake handshakedata) {

            }

            @Override
            public void onMessage(String message) {
                try {
                    JSONObject jsonObject = new JSONObject(message);
                    int code = jsonObject.optInt("code");
                    if (code == 201) {
                        //  说明可以发送数据了
                        JSONObject sendJson = new JSONObject();
                        sendJson.put("code", 101);
                        sendJson.put("data", data);
                        sendJson.put("host", "app.goex24.com");
                        sendJson.put("kline", "5");
                        sendJson.put("language", "en_US");
                        sendJson.put("payType", "1");
                        sendJson.put("sign", "GOEX");
                        sendJson.put("token1", "");
                        sendJson.put("token2", "");
                        mClient.send(sendJson.toString());
                    } else if (code == 105) {
                        //  当前币种的买卖数据
                        QuotationDetailBean bean = mGson.fromJson(message, QuotationDetailBean.class);
                        EventBus.getDefault().post(new QuotationDetailEvent(bean));
                    } else if (code == 101) {
                        //  当前币种的信息
                        QuotationDetailCoinBean bean = mGson.fromJson(message, QuotationDetailCoinBean.class);
                        EventBus.getDefault().post(new QuotationDetailCoinEvent(bean));
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mClient != null)
            mClient.close();
    }

}



