package com.gc.money.currency.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeUtil {
    public static String convert(String time){

        try{
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date  date = df.parse(time);
            SimpleDateFormat df1 = new SimpleDateFormat ("EEE MMM dd HH:mm:ss", Locale.UK);
            Date date1 =  df1.parse(date.toString());
            DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return df2.format(date1);
        }catch (Exception e){

        }
        return "";
    }
}
