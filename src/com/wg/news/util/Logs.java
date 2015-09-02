package com.wg.news.util;

import android.util.Log;

/**
 * Created by EXP on 2015/8/26.
 */
public class Logs {
    public static String tag = "news";
    public static boolean flag=true;
    public static void i(Object str){
        if(flag)
        Log.i(tag,str+"");
    }
    public static void w(Object str){
        if(flag)
        Log.w(tag,str+"");
    }
    public static void d(Object str){
        if(flag)
        Log.d(tag, str+"");
    }
    public static void v(Object str){
        if(flag)
        Log.v(tag,str+"");
    }
    public static void e(Object str){
        if(flag)
        Log.e(tag,str+"");
    }
}
