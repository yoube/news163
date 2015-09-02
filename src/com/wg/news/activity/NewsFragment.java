package com.wg.news.activity;

import android.os.Binder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.wg.news.R;
import com.wg.news.util.Logs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by EXP on 2015/8/25.
 */
public class NewsFragment extends Fragment {
    private ViewPager mViewPager;
    private RadioGroup mRadioBroup;
    private HorizontalScrollView mHorizontalScrollView;
    private RadioButton[] rButs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_layout, null);

        mViewPager = (ViewPager) view.findViewById(R.id.news_vewPager);
        mRadioBroup = (RadioGroup) view.findViewById(R.id.fragment_news_radiogroup);
        mHorizontalScrollView = (HorizontalScrollView) view.findViewById(R.id.fragment_news_horizontalScrollView);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //---数据源
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("newsType", "T1348647909107");
        map.put("name", "头条");
        data.add(map);

        map = new HashMap<String, Object>();
        map.put("newsType", "T1348648517839");
        map.put("name", "头条");
        data.add(map);
        map = new HashMap<String, Object>();
        map.put("newsType", "T1348649079062");
        map.put("name", "头条");
        data.add(map);
        map = new HashMap<String, Object>();
        map.put("newsType", "T1348648756099");
        map.put("name", "头条");
        data.add(map);
        map = new HashMap<String, Object>();
        map.put("newsType", "T1348649580692");
        map.put("name", "头条");
        data.add(map);


        mViewPager.setAdapter(new FragmentPagerAdapter(getFragmentManager()) {
            @Override
            public Fragment getItem(int i) {

                Bundle binder =new Bundle();
                binder.putString("newsType", (String) data.get(i).get("newsType"));
                NewsTopFragment fragment =  NewsTopFragment.newInstense(binder);
                return fragment;

            }

            @Override
            public int getCount() {
                return data.size();
            }
        });

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                //设置滚动条滚动
                mHorizontalScrollView.setScrollX((int)((i*158)+((v*100)*1.56)));

            }

            @Override
            public void onPageSelected(int i) {
                RadioButton radioButton = (RadioButton) mRadioBroup.getChildAt(i);
                Logs.d("--radioButton.getLayoutParams().height--" + radioButton.getLayoutParams().height);
                Logs.w("--mHorizontalScrollView.getScrollX()--" + mHorizontalScrollView.getScrollX());

                radioButton.setChecked(true);

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        ((RadioButton)mRadioBroup.getChildAt(0)).setChecked(true);
        mRadioBroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.news_scroll_newstop:
                        mViewPager.setCurrentItem(0);
                        break;
                    case R.id.news_scroll_yule:
                        mViewPager.setCurrentItem(1);

                        break;
                    case R.id.news_scroll_tiyu:
                        mViewPager.setCurrentItem(2);

                        break;
                    case R.id.news_scroll_caijing:
                        mViewPager.setCurrentItem(3);

                        break;
                    case R.id.news_scroll_keji:
                        mViewPager.setCurrentItem(4);
                        break;

                }
            }
        });

//        int count = data.size();
//        rButs = new RadioButton[count];
//        for(int i=0;i<count;i++){
//            Map<String,Object> m = data.get(i);
//        RadioButton radioButton = (RadioButton) getActivity().getLayoutInflater().inflate(R.layout.view_news_item_radio_button,null);
//            radioButton.setText(m.get("name").toString());
//
//            rButs[i] = radioButton;
//            mRadioBroup.addView(radioButton,  radioButton.getLayoutParams());
//        }
//        rButs[0].setChecked(true);

    }
}
