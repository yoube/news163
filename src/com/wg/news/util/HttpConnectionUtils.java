package com.wg.news.util;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by EXP on 2015/8/26.
 */
public class HttpConnectionUtils {

    public static String  getString(String url) throws MessageException {
        HttpClient httpClient = new DefaultHttpClient();
        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,10000);
        HttpGet get = new HttpGet(url);
        try {
            HttpResponse response =  httpClient.execute(get);
            int code = response.getStatusLine().getStatusCode();
            if(code==200){
                return EntityUtils.toString(response.getEntity());
            }

        } catch (IOException e) {
            throw new MessageException("网络链接异常,请检测网络！");
        }
        return null;
    }
}
