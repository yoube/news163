package com.wg.news.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.daimajia.slider.library.SliderLayout;
import com.google.gson.Gson;
import com.wg.news.R;
import com.wg.news.adapter.NewsPageAdapter;
import com.wg.news.bean.NewsBean;
import com.wg.news.bean.NewsRootBean;
import com.wg.news.util.*;
import me.maxwin.view.XListView;

import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * Created by EXP on 2015/8/26.
 */
public class NewsTopFragment extends Fragment implements XListView.IXListViewListener {
    private String url = "http://c.m.163.com/nc/article/headline/";
    private String newsType ;
    NewsPageAdapter mAdapter;
    private XListView xListView;
    private int curreatPage;
    private int pageSize = 20;
    private Context mContext;
    private SharedPreferences sharedPreferences;
    public NewsTopFragment(Map<String, Object> map) {
        newsType = (String) map.get("newsType");
    }
    public static NewsTopFragment newInstense(Bundle bundle){
        NewsTopFragment fragment = new NewsTopFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public NewsTopFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
        sharedPreferences = mContext.getSharedPreferences("NEWS_CONTENT",Context.MODE_PRIVATE);
        newsType = (String) getArguments().get("newsType");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_xlistview_layout, null);
        xListView = (XListView) view.findViewById(R.id.fragment_xlistview);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAdapter = new NewsPageAdapter(getActivity());

        xListView.setPullLoadEnable(true);
        xListView.setPullRefreshEnable(true);
        //设置适配器
        xListView.setAdapter(mAdapter);
        //设置缓存数据
        mAdapter.setData(jsonFromList(sharedPreferences.getString(url + newsType + "/0-" + pageSize + ".html",null)));
        //设置监听
        xListView.setXListViewListener(this);
        xListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                NewsBean bean = (NewsBean) parent.getAdapter().getItem(position);
                Intent intent = new Intent(mContext,NewsContentActvity.class);
                intent.putExtra("docId", bean.getDocid());
                startActivity(intent);
            }
        });
        //获取json任务内容
        new HttpAsyncTask() {
            @Override
            public void postExecute(String jsonString) {
                Logs.e("saveURL--L>"+url);
                SharedPreferences.Editor edit = sharedPreferences.edit();
                edit.putString(url + newsType + "/0-" + pageSize + ".html",jsonString).commit();

                mAdapter.setData(jsonFromList(jsonString));
            }

            @Override
            public void errorExecute(String e) {
                ToastUtils.show(mContext, e);
            }
        }.executeJsonTask(url + newsType + "/0-" + pageSize + ".html");

    }

    @Override
    public void onRefresh() {

        new HttpAsyncTask() {
            @Override
            public void postExecute(String jsonString ) {
                mAdapter.setData(jsonFromList(jsonString));
                xListView.stopRefresh();
                xListView.setRefreshTime("刚刚");
                ToastUtils.show(mContext, "刷新成功！");
                curreatPage = 0;
            }

            @Override
            public void errorExecute(String e) {
                ToastUtils.show(mContext, e);
                xListView.stopRefresh();
            }
        }.executeJsonTask(url+newsType+"/0-"+pageSize+".html");

    }

    @Override
    public void onStop() {
        super.onStop();

        //使用sliders时必须停止图片滚动线程，否则会资源浪费造成内存溢出
        SliderLayout sliderLayout = mAdapter.getSliderLayout();
        if(sliderLayout!=null){
            sliderLayout.stopAutoCycle();
        }
    }

    public String getNextPageUrl() {
        curreatPage++;
        StringBuffer sbuf = new StringBuffer();
        sbuf.append(url);
        sbuf.append(newsType);
        sbuf.append("/");
        sbuf.append(curreatPage*pageSize);
        sbuf.append("-");
        sbuf.append(pageSize);
        sbuf.append(".html");
        Logs.d("next url-->"+sbuf.toString());
        return sbuf.toString();
    }

    @Override
    public void onLoadMore() {

        new HttpAsyncTask() {
            @Override
            public void postExecute(String jsonString) {
                mAdapter.addData(jsonFromList(jsonString));
                xListView.stopLoadMore();
                ToastUtils.show(mContext, "加载成功！");
            }

            @Override
            public void errorExecute(String msg) {
                ToastUtils.show(mContext, msg);
                xListView.stopLoadMore();
            }
        }.executeJsonTask(getNextPageUrl());
    }
    public List jsonFromList(String json){
        if(json==null)return null;
        Gson gson = new Gson();
        Class<NewsRootBean> clazz = NewsRootBean.class;
        NewsRootBean rootBean = gson.fromJson(json, clazz);
//        gson.fromJson()
        try {
            Method method = clazz.getMethod("get"+newsType);
            return (List) method.invoke(rootBean);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
