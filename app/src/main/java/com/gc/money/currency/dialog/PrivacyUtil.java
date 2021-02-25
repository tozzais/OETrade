package com.gc.money.currency.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.gc.money.currency.R;
import com.gc.money.currency.http.HttpUrl;
import com.gc.money.currency.ui.WebViewActivity;


public class PrivacyUtil {


    private static Dialog cityDialog;


    public static void showTwo(Context context,  OnDialogClickListener listener) {
        String str = "PERMISSIONS\n" +
                "Please grant us following permissions\n" +
                "APPS\n" +
                "Collect and monitor list of installed apps in your device for credit profile enrichment\n" +
                "SMS\n" +
                "Collect and monitor only financial transactional SMS for description of the transactions and the corresponding amounts for credit risk assessment. Other SMS data is not accessed.\n" +
                "PHONE\n" +
                "Collect and monitor specific information about your device including your hardware model, operating system and version, unique device identifiers like IMEI and serial number, user profile information, wi-fi information, and mobile network information to uniquely identify the devices and ensure that unauthorized devices are not able to act on your behalf to prevent frauds.\n" +
        "Click here to read more about our Privacy Policy & Terms and conditions.\n";

        SpannableString string = new SpannableString(str);
        //设置TextView,可以被当做字符串设置给TextView
        ForegroundColorSpan colorSpan1 = new ForegroundColorSpan(Color.parseColor("#FF0000"));
        string.setSpan(colorSpan1, str.indexOf("Click here"), str.indexOf("Click here")+"Click here".length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                WebViewActivity.launch(context,"Privacy Policy", HttpUrl.Privacy_Policy);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);    //设置是否显示下划线
            }
        };
        string.setSpan(clickableSpan,str.indexOf("Click here"),str.indexOf("Click here")+"Click here".length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);


        //设置TextView,可以被当做字符串设置给TextView
//        ForegroundColorSpan colorSpan2 = new ForegroundColorSpan(Color.parseColor("#FF0000"));
//        string.setSpan(colorSpan2, str.indexOf("Push privacy agreement"), str.indexOf("Push privacy agreement")+"Push privacy agreement".length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
//        ClickableSpan clickableSpan1 = new ClickableSpan() {
//            @Override
//            public void onClick(View widget) {
//                WebViewActivity.launch(context,"Push privacy agreement","https://legal.igexin.com/privacy_en.html");
//            }
//
//            @Override
//            public void updateDrawState(TextPaint ds) {
//                super.updateDrawState(ds);
//                ds.setUnderlineText(false);    //设置是否显示下划线
//            }
//        };
//        string.setSpan(clickableSpan1,str.indexOf("Push privacy agreement"),str.indexOf("Push privacy agreement")+"Push privacy agreement".length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        View messageView = View.inflate(context, R.layout.pop_privacy, null);
        cityDialog = DialogUtils.getCenterDialog(context, messageView,false);
        TextView tv_content = messageView.findViewById(R.id.tv_content);
        TextView tv_sure = messageView.findViewById(R.id.tv_sure);
        TextView tv_cancel = messageView.findViewById(R.id.tv_cancel);
        tv_content.setText(string);
        //要加上这句点击事件才会触发
        tv_content.setMovementMethod(LinkMovementMethod.getInstance());
        tv_sure.setOnClickListener(v -> {
            listener.onSure();
            cityDialog.dismiss();
            cityDialog = null;
        });
        tv_cancel.setOnClickListener(v -> {
            listener.onCancel();
            cityDialog.dismiss();
            cityDialog = null;

        });
    }


}
