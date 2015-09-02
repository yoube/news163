package com.wg.news.util;

import android.os.AsyncTask;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by EXP on 2015/8/28.
 */
public abstract class HttpAsyncTask  {
    public void executeJsonTask(String url){
        AsyncTask<String,Void,Map<String,String>> task = new AsyncTask<String, Void,Map<String,String>>() {
            @Override
            protected Map<String,String> doInBackground(String... params) {
                Map<String,String> map = new HashMap<String, String>();
                String reslut = null;
                try {
                    reslut = HttpConnectionUtils.getString(params[0]);
                    map.put("status","");
                    map.put("json",reslut);

                } catch (MessageException e) {
                    map.put("status",null);
                    map.put("msg",e.getMessage());
                }
                return map;
            }

            @Override
            protected void onPostExecute(Map<String,String> map) {
                if(map.get("status")==null) {
                    errorExecute(map.get("msg"));
                } else {
                    postExecute(map.get("json"));
                }
            }
        }.execute(url);

    }
    public abstract void postExecute(String josnString);
    public abstract void errorExecute(String msg);

}
