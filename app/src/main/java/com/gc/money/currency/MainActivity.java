package com.gc.money.currency;

import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.flyco.roundview.RoundTextView;
import com.gc.money.currency.bean.PushMessage;
import com.gc.money.currency.global.CoinApplication;
import com.gc.money.currency.ui.activity.SettingActivity;
import com.gc.money.currency.ui.fragment.CoinListFragment;
import com.gc.money.currency.ui.fragment.InformListFragment;
import com.gc.money.currency.ui.fragment.MineFragment;
import com.tozzais.baselibrary.ui.CheckPermissionActivity;
import com.tozzais.baselibrary.util.StatusBarUtil;
import com.tozzais.baselibrary.util.log.LogUtil;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import io.branch.referral.Branch;
import io.branch.referral.BranchError;

/**
 *  1：修改闪屏
 *  2：小米手机进不去马甲页面
 *  3：还有包有缓存
 *  4：谷歌登录
 */
public class MainActivity extends CheckPermissionActivity {

    @BindView(R.id.iv_shop)
    ImageView ivShop;
    @BindView(R.id.tv_shop)
    TextView tvShop;
    @BindView(R.id.iv_life_service)
    ImageView ivLifeService;
    @BindView(R.id.tv_life_service)
    TextView tvLifeService;
    @BindView(R.id.iv_find)
    ImageView ivFind;
    @BindView(R.id.tv_find)
    TextView tvFind;
    @BindView(R.id.iv_cart)
    ImageView ivCart;
    @BindView(R.id.tv_cart_number)
    RoundTextView tvCartNumber;
    @BindView(R.id.tv_cart)
    TextView tvCart;
    @BindView(R.id.iv_mine)
    ImageView ivMine;
    @BindView(R.id.tv_mine)
    TextView tvMine;
    @BindView(R.id.ll_main)
    LinearLayout llMain;

    public static void launch(Activity context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
        context.finish();
    }

    public static void launch(Activity context,int mPosition) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("position",mPosition);
        context.startActivity(intent);
        context.finish();

    }

    @Override
    public int getLayoutId() {
        return -1;
    }

    @Override
    protected int getBaseLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        fragmentManager = getSupportFragmentManager();
        selectFragment(SHOP);
    }

    public static String[] needPermissions = {
            Manifest.permission.READ_PHONE_STATE
    };
    @Override
    public void loadData() {
        checkPermissions(needPermissions);

    }

    @Override
    public void initListener() {
        LogUtil.e(CoinApplication.gaID);
    }


    public static final int SHOP = 0;
    public static final int FIND = 2;
    public static final int MINE = 4;
    private static final String TAG_SHOP = "tag_shop";
    private static final String TAG_FIND = "tag_find";
    private static final String TAG_MINE = "tag_mine";

    private int mPosition;//当前选中的底部菜单
    private FragmentManager fragmentManager;
    private CoinListFragment shopFragment;
    private InformListFragment findFragment;
    private MineFragment mineFragment;


    @OnClick({R.id.ll_shop, R.id.ll_life_service, R.id.ll_find, R.id.ll_cart, R.id.ll_mine})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_shop:
                selectFragment(SHOP);
                break;
            case R.id.ll_find:
                selectFragment(FIND);
                break;

            case R.id.ll_mine:
                selectFragment(MINE);
                break;
        }
    }

    public void selectFragment(int position) {
        switch (position) {
            case SHOP:
                mPosition = SHOP;
                StatusBarUtil.setDarkMode(this);
                setTabChecked(mPosition);
                setTabSelection(mPosition);
                break;

            case FIND:
                StatusBarUtil.setDarkMode(this);
                mPosition = FIND;
                setTabChecked(mPosition);
                setTabSelection(mPosition);
                break;

            case MINE:
                StatusBarUtil.setLightMode(this);
                mPosition = MINE;
                setTabChecked(mPosition);
                setTabSelection(mPosition);
                break;
        }


    }

    /**
     * 设置底部菜单被选中后字体、图片的颜色
     *
     * @param pos
     */
    private void setTabChecked(int pos) {
        ivShop.setImageResource(pos == SHOP ? R.mipmap.shop_select : R.mipmap.shop);
        tvShop.setTextColor(pos == SHOP ? getResources().getColor(R.color.baseColor) : getResources().getColor(R.color.main_tab_gray_text_color));
        ivFind.setImageResource(pos == FIND ? R.mipmap.find_select : R.mipmap.find);
        tvFind.setTextColor(pos == FIND ? getResources().getColor(R.color.baseColor) : getResources().getColor(R.color.main_tab_gray_text_color));
        ivMine.setImageResource(pos == MINE ? R.mipmap.mine_select : R.mipmap.mine);
        tvMine.setTextColor(pos == MINE ? getResources().getColor(R.color.baseColor) : getResources().getColor(R.color.main_tab_gray_text_color));

    }

    private void setTabSelection(int position) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();//开启Fragment事务
        hideFragments(transaction);//隐藏所有页面（）
        switch (position) {
            case SHOP:
                if (shopFragment == null) {
                    shopFragment = new CoinListFragment();
                    transaction.add(R.id.fl_container, shopFragment, TAG_SHOP);
                } else {
                    transaction.show(shopFragment);
                }
                break;


            case FIND:
                if (findFragment == null) {
                    findFragment = new InformListFragment();
                    transaction.add(R.id.fl_container, findFragment, TAG_FIND);
                } else {
                    transaction.show(findFragment);
                }
                break;

            case MINE:
//                create();
                if (mineFragment == null) {
                    mineFragment = new MineFragment();
                    transaction.add(R.id.fl_container, mineFragment, TAG_MINE);
                } else {
                    transaction.show(mineFragment);
                }

                break;
        }
        // 提交
        transaction.commit();
    }

    /**
     * 隐藏所有的页面
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (shopFragment != null) {
            transaction.hide(shopFragment);
        }

        if (findFragment != null) {
            transaction.hide(findFragment);
        }

        if (mineFragment != null) {
            transaction.hide(mineFragment);
        }
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            fragmentManager = getSupportFragmentManager();
            shopFragment = (CoinListFragment) fragmentManager.findFragmentByTag(TAG_SHOP);
            findFragment = (InformListFragment) fragmentManager.findFragmentByTag(TAG_FIND);
            mineFragment = (MineFragment) fragmentManager.findFragmentByTag(TAG_MINE);
        }
        mPosition = savedInstanceState.getInt("mPosition");
        selectFragment(mPosition);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("mPosition", mPosition);
    }

    @Override
    protected int getToolbarLayout() {
        return -1;
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setTransparentForImageViewInFragment(MainActivity.this, null);
        StatusBarUtil.setLightMode(this);
    }





    private long mExitTime;

    @Override
    public void permissionGranted() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                tsg("Press again to exit");
                mExitTime = System.currentTimeMillis();
            } else {
                //小于2000ms则认为是用户确实希望退出程序-调用System.exit()方法进行退出
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override public void onStart() {
        super.onStart();
        Branch.sessionBuilder(this).withCallback(branchReferralInitListener).withData(getIntent() != null ? getIntent().getData() : null).init();
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        // if activity is in foreground (or in backstack but partially visible) launching the same
        // activity will skip onStart, handle this case with reInitSession
        Branch.sessionBuilder(this).withCallback(branchReferralInitListener).reInit();
    }
    private Branch.BranchReferralInitListener branchReferralInitListener = new Branch.BranchReferralInitListener() {
        @Override
        public void onInitFinished(JSONObject linkProperties, BranchError error) {
            // do stuff with deep link data (nav to page, display content, etc)
        }
    };


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void create(){
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //创建通知渠道实例
        //并为它设置属性
        //通知渠道的ID,随便写
        String id = "channel_01";
        //用户可以看到的通知渠道的名字，R.string.app_name就是strings.xml文件的参数，自定义一个就好了
        CharSequence name = getString(R.string.app_name);
        //用户可看到的通知描述
        String description = getString(R.string.app_name);
        //构建NotificationChannel实例
        NotificationChannel notificationChannel =
                new NotificationChannel(id, name, NotificationManager.IMPORTANCE_HIGH);
        //配置通知渠道的属性
        notificationChannel.setDescription(description);
        //设置通知出现时的闪光灯
        notificationChannel.enableLights(true);
        notificationChannel.setLightColor(Color.RED);
        //设置通知出现时的震动
        notificationChannel.enableVibration(true);
        notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 100});
        //在notificationManager中创建通知渠道
        manager.createNotificationChannel(notificationChannel);
        PushMessage data = new PushMessage();
        data.setUrl("https://www.baidu.com");
        Notification notification = new NotificationCompat.Builder(MainActivity.this, id)
                .setContentTitle("标题")
                .setContentText("内容666666666666666666666666666")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_foreground))
                .setContentIntent(setPendingIntent(mActivity,data))
                .build();
        manager.notify(1, notification);
    }

    @NonNull
    private PendingIntent setPendingIntent(Context context, PushMessage data) {
        Intent intent = new Intent();
        String url = data.getUrl();
        if (TextUtils.isEmpty(url)) {//url为空时启动app
            intent = new Intent(context, SettingActivity.class);
            startActivity(intent);
        } else {//不为空打开web页面
            Uri uri = Uri.parse("https://www.baidu.com");
            intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
        if (intent != null) {
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }
        return PendingIntent.getActivity(context, (int) System.currentTimeMillis(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
