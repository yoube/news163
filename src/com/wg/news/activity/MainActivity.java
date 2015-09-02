package com.wg.news.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.Toast;
import com.wg.news.R;
import com.wg.news.util.ToastUtils;

import static android.support.v4.app.FragmentTabHost.*;

public class MainActivity extends FragmentActivity {
    private FragmentTabHost mTabHost;
    private RadioGroup mTabs;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        intiView();

    }

    private void intiView() {
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this,getSupportFragmentManager(),android.R.id.tabcontent);
        //增加一页
        mTabHost.addTab(mTabHost.newTabSpec("tab1").setIndicator("1"),NewsFragment.class,null);
//        FragmentTabHost.TabSpec tabSpec = new FragmentTabHost.TabSpec();
        mTabs = (RadioGroup) findViewById(R.id.main_tabs_radiogroup);
//        mTabs.check(R.id.main_news);
        mTabs.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                String name = "";
                switch (i){
                    case R.id.main_tabs_faxian:
                        name= "发现";
                        break;
                    case R.id.main_tabs_shiting:
                        name= "试听";
                        break;
                    case R.id.main_tabs_reader:
                        name= "阅读";
                        break;
                    case R.id.main_tabs_news:
                        name= "新闻";
                        break;
                }
                Toast.makeText(MainActivity.this,name,Toast.LENGTH_SHORT).show();
            }
        });


    }
    private boolean backFlag = true;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(event.getKeyCode() == KeyEvent.KEYCODE_BACK){
            if(backFlag){
                ToastUtils.show(this,"再按一次退出客户端");
                new Thread(){
                    @Override
                    public void run() {
                        backFlag = false;
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        backFlag = true;
                    }
                }.start();
                return false;
            }else{
                return super.onKeyDown(keyCode,event);
            }
        }
        return super.onKeyDown(keyCode,event);
    }
}
