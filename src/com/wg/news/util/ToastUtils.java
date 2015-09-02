package com.wg.news.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by EXP on 2015/8/30.
 */
public class ToastUtils {
    public static void show(Context context,String msg){
        if(context==null)return;
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }
}
