package com.wg.news.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.wg.news.R;
import com.wg.news.bean.NewsBean;
import com.wg.news.bean.NewsContentBean;
import com.wg.news.util.Logs;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.Socket;
import java.security.AccessControlContext;
import java.util.List;

/**
 * Created by EXP on 2015/8/31.
 */
public class NewsContentActvity extends Activity {
    private String url = "http://c.m.163.com/nc/article/?/full.html";
    String dicId ;
    private TextView mTitleText;
    private TextView mRaplyCount;
    private TextView mNewsSorces;
    private TextView mNewsTime;
    private ImageView mNewsImage;
    private TextView mImageAlt;
    private TextView mNewsBody;

    private View mReflahLayout;
    private View mErrorLayout;
    private ImageView mFlashImage;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
         dicId = intent.getStringExtra("docId");
        url = url.replace("?",dicId);
        setContentView(R.layout.activity_news_content);

        initView();


        requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(getContentRequest());


//        new AsyncTask<String,Void,String>(){
//
//            @Override
//            protected String doInBackground(String... params) {
//                StringBuffer buf = new StringBuffer();
//                InputStream input = null;
//                Reader reader = null;
//                Socket socket = null;
//                try {
//                    socket = new Socket("51037fd0.all123.net",8080);
//                    input = socket.getInputStream();
//                    reader  = new InputStreamReader(input);
//                    int count ;
//                    char[] charbuf = new char[100];
//                    while((count = reader.read(charbuf))!=-1){
//                        Logs.e(new String(charbuf));
//                        buf.append(charbuf,0,count);
//                    }
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }finally {
//                    try {
//                        if(input!=null)
//                            input.close();
//                        if(reader!=null) reader.close();
//                        if(socket!=null) socket.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//
//
//                }
//
//                return buf.toString();
//            }
//
//            @Override
//            protected void onPostExecute(String s) {
//                textView.setText(s);
//            }
//        }.execute();
//
    }
    public StringRequest getContentRequest(){
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jboj = new JSONObject(response);
                    JSONObject jo = jboj.getJSONObject(dicId);
                    Gson gson = new Gson();
                    NewsContentBean contentBean = gson.fromJson(jo.toString(), NewsContentBean.class);
                    setView(contentBean);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mReflahLayout.setVisibility(View.GONE);
                mErrorLayout.setVisibility(View.VISIBLE);
            }
        });
        //设置不缓存
        stringRequest.setShouldCache(false);
        return stringRequest;
    }
    public void initView(){

        mTitleText = (TextView) findViewById(R.id.news_content_title);
        mRaplyCount = (TextView) findViewById(R.id.news_content_reply_count_text);
        mReflahLayout = findViewById(R.id.news_content_reflah_rl);
        mErrorLayout = findViewById(R.id.news_content_error_rl);
        mNewsSorces = (TextView) findViewById(R.id.news_content_sorce_text);
        mNewsTime = (TextView) findViewById(R.id.news_content_time_text);
        mImageAlt = (TextView) findViewById(R.id.news_content_image_alt);
        mNewsBody = (TextView) findViewById(R.id.news_content_body);
        mNewsImage = (ImageView) findViewById(R.id.news_content_image);
        mFlashImage = (ImageView) findViewById(R.id.news_content_reflah_img);
        mFlashImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mReflahLayout.setVisibility(View.VISIBLE);
                mErrorLayout.setVisibility(View.GONE);
                requestQueue.add(getContentRequest());
            }
        });


    }
    public void setView(NewsContentBean bean){
        //隐藏进度控件
        mReflahLayout.setVisibility(View.GONE);

        mTitleText.setText(bean.getTitle());
        mRaplyCount.setText(bean.getReplyCount() + "跟帖");
        mRaplyCount.setVisibility(View.VISIBLE);
        mNewsSorces.setText(bean.getSource());
        mNewsTime.setText(bean.getPtime());
        mNewsBody.setText(Html.fromHtml(bean.getBody()));
        List<NewsContentBean.NewsImage> list = bean.getImg();
        if(!list.isEmpty()){
            NewsContentBean.NewsImage img = list.get(0);
            ImageRequest imgrequest = new ImageRequest(img.getSrc(), new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap response) {
                    mNewsImage.setImageBitmap(response);
                    mNewsImage.setVisibility(View.VISIBLE);
                }
            }, 0,0, Bitmap.Config.RGB_565,new Response.ErrorListener(){

                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            imgrequest.setShouldCache(false);
            requestQueue.add(imgrequest);
            mImageAlt.setText(img.getAlt());
        }

    }

}
