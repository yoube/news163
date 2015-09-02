package com.wg.news.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.wg.news.R;
import com.wg.news.bean.NewsBean;
import com.wg.news.util.AsyncLoaderImage;
import com.wg.news.util.AsyncMemoryFileCacheImageLoader;
import com.wg.news.util.Logs;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EXP on 2015/8/26.
 */
public class NewsPageAdapter extends BaseAdapter {
    private List<NewsBean> data = new ArrayList<NewsBean>();
    private Context mContext;
    private LayoutInflater inflater;
    private boolean isChanged = true;
    private SliderLayout sliderLayout;
    public static final int typeCount = 3;
    public static final int PUTONG = 0;
    public static final int TOP = 1;
    public static final int PHOTOSET = 2;


    public NewsPageAdapter(Context context) {
        mContext = context;
        inflater = LayoutInflater.from(context);
    }

    public void setData(List<NewsBean> data) {
        Logs.w(data);
        if(data==null)return;
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        isChanged = true;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public int getViewTypeCount() {
        return typeCount;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) return TOP;
        String skipType = data.get(position).getSkipType();
        if (skipType == null || "".equals(skipType))
            return PUTONG;
        else if (skipType.equals("photoset"))
            return PHOTOSET;

        return PUTONG;
    }

    @Override
    public Object getItem(int i) {


        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        switch (getItemViewType(i)) {
            case PUTONG:
                return setPutongView(i, view, viewGroup);
            case TOP:
                return setTopView(i, view, viewGroup);
            case PHOTOSET:
                return setPhotosetView(i, view, viewGroup);
        }
        return null;
    }

    private View setTopView(int i, View view, ViewGroup viewGroup) {
        NewsBean bean = data.get(i);
        if (view == null && isChanged) {
            view = inflater.inflate(R.layout.new_top_slider, null);
            sliderLayout = (SliderLayout) view.findViewById(R.id.news_top_slider);
            //设置页面切换动画
            sliderLayout.setPresetTransformer(SliderLayout.Transformer.Default);
            //设置指示器位置
            sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom);
            //轮播时间
            sliderLayout.setDuration(3000);
            view.setTag(sliderLayout);

            sliderLayout.removeAllSliders();
            for (int il = 0; il < 3; il++) {
                NewsBean newsBean = data.get(il);
                TextSliderView sliderView = new TextSliderView(mContext);
                sliderView.description(newsBean.getTitle())
                        .image(newsBean.getImgsrc())
                        .setScaleType(BaseSliderView.ScaleType.CenterCrop);
                sliderLayout.addSlider(sliderView);
            }
            isChanged = false;
        }
//        else {
//            sliderLayout = (SliderLayout) view.getTag();
//        }

        return view;
    }
    public SliderLayout getSliderLayout(){
        return sliderLayout;
    }

    private View setPhotosetView(int i, View view, ViewGroup viewGroup) {
        NewsBean bean = data.get(i);
        PhotosetHoder photosetHoder = null;
        if (view == null) {
            photosetHoder = new PhotosetHoder();
            view = inflater.inflate(R.layout.view_photoset_layout, null);
            photosetHoder.newsTitle = (TextView) view.findViewById(R.id.news_title_text);
            photosetHoder.image1 = (ImageView) view.findViewById(R.id.news_list_photoset_1);
            photosetHoder.image2 = (ImageView) view.findViewById(R.id.news_list_photoset_2);
            photosetHoder.image3 = (ImageView) view.findViewById(R.id.news_list_photoset_3);

            view.setTag(photosetHoder);
        } else {
            photosetHoder = (PhotosetHoder) view.getTag();
        }
        photosetHoder.newsTitle.setText(bean.getTitle());
        AsyncLoaderImage asyncLoaderImage =  AsyncLoaderImage.getInstances(mContext);
//        asyncLoaderImage.loadImage(mContext.getResources(), bean.getImgsrc(), photosetHoder.image1);
//        asyncLoaderImage.loadImage(mContext.getResources(), bean.getImgextra().get(0).getImgsrc(), photosetHoder.image2);
//        asyncLoaderImage.loadImage(mContext.getResources(), bean.getImgextra().get(1).getImgsrc(), photosetHoder.image3);
        AsyncMemoryFileCacheImageLoader.getInstanceAsycnHttpImageView(mContext)
                .loadBitmap(mContext.getResources(), bean.getImgsrc(), photosetHoder.image1);
        if(bean.getImgextra().isEmpty()){
//            AsyncMemoryFileCacheImageLoader.getInstanceAsycnHttpImageView(mContext)
//                    .loadBitmap(mContext.getResources(), bean.getImgextra().get(0).getImgsrc(), photosetHoder.image2);
//            AsyncMemoryFileCacheImageLoader.getInstanceAsycnHttpImageView(mContext)
//                    .loadBitmap(mContext.getResources(), bean.getImgextra().get(0).getImgsrc(), photosetHoder.image3);
        }else{

            AsyncMemoryFileCacheImageLoader.getInstanceAsycnHttpImageView(mContext)
                    .loadBitmap(mContext.getResources(), bean.getImgextra().get(0).getImgsrc(), photosetHoder.image2);
            AsyncMemoryFileCacheImageLoader.getInstanceAsycnHttpImageView(mContext)
                    .loadBitmap(mContext.getResources(), bean.getImgextra().get(1).getImgsrc(), photosetHoder.image3);
        }
        return view;
    }


    private View setPutongView(int i, View view, ViewGroup viewGroup) {
        NewsBean bean = data.get(i);
        PutongHodler hodler = null;
        if (view == null) {
            hodler = new PutongHodler();
            view = inflater.inflate(R.layout.view_news_putong_item, null);
            hodler.newsImage = (ImageView) view.findViewById(R.id.news_img);
            hodler.newsTitle = (TextView) view.findViewById(R.id.news_title_text);
            hodler.newsDiscription = (TextView) view.findViewById(R.id.news_discription_text);
            hodler.replyCount = (TextView) view.findViewById(R.id.news_replycount);
            view.setTag(hodler);
        } else {
            hodler = (PutongHodler) view.getTag();
        }

//        AsyncLoaderImage.getInstances(mContext).loadImage(mContext.getResources(), bean.getImgsrc(), hodler.newsImage);
        AsyncMemoryFileCacheImageLoader.getInstanceAsycnHttpImageView(mContext).loadBitmap(mContext.getResources(), bean.getImgsrc(), hodler.newsImage);
        hodler.newsTitle.setText(bean.getTitle());
        hodler.newsDiscription.setText(bean.getDigest());
        hodler.replyCount.setText(bean.getReplyCount() + "回帖");

        return view;
    }

    public void addData(List<NewsBean> list) {
        if(list==null){
            return;
        }else if(data==null){
            data = list;
        }
        data.addAll(list);
        notifyDataSetChanged();
    }

    class PutongHodler {
        ImageView newsImage;
        TextView newsTitle;
        TextView newsDiscription;
        TextView replyCount;
    }

    class PhotosetHoder {
        TextView newsTitle;
        ImageView image1;
        ImageView image2;
        ImageView image3;
    }

}
