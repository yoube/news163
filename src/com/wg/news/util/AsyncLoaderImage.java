package com.wg.news.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.LruCache;
import android.widget.ImageView;
import com.wg.news.R;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.*;
import java.lang.ref.WeakReference;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by EXP on 2015/8/28.
 */
public class AsyncLoaderImage {


    //-----------------单例模式--------
    private static AsyncLoaderImage asyncLoaderImage;

    public static AsyncLoaderImage getInstances(Context context) {
        if (asyncLoaderImage == null) {
            asyncLoaderImage = new AsyncLoaderImage(context);
        }
        return asyncLoaderImage;
    }

    private AsyncLoaderImage() {
        //计算内存缓存的大小
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory / 8;
        //设置缓存大小为系统内存的八分之一
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            //复写获取资源的大小
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };

    }

    private AsyncLoaderImage(Context context) {
        this();
        //获取文件缓存文件夹
        File diksCacheDir = getDiskCashePath(context, "imageCache");

        if (!diksCacheDir.exists()) diksCacheDir.mkdir();

        try {
            mDiskLruCashe = DiskLruCache.open(diksCacheDir, getAppVersion(context), 1, DISK_CACHE_SIZE);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 内存缓存的核心类，使用lru（最近最少）算法进行缓存管理
     */
    private LruCache<String, Bitmap> mMemoryCache;

    private void addBitmapToMemoryCache(String key, Bitmap value) {
        if (key != null) {
            mMemoryCache.put(key, value);
        }
    }

    private Bitmap getBitmapFromMemoryCache(String key) {
        return mMemoryCache.get(key);
    }

    /**
     * 图片文件缓存
     */
    private DiskLruCache mDiskLruCashe;

    public static final int DISK_CACHE_SIZE = 1024 * 1024 * 10;//10M

    /**
     * 获取图片缓存文件夹，当sdcard可用时，调用getExternalCacheDir();
     * 否则用 getCacheDir();
     *
     * @param context
     * @param imageCacheDirPath
     * @return
     */
    private File getDiskCashePath(Context context, String imageCacheDirPath) {
        String path;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())//判断sdcard可用
                || !Environment.isExternalStorageRemovable()) {//判断sdcard不被移除时
            path = context.getExternalCacheDir().getAbsolutePath();
        } else {
            path = context.getCacheDir().getAbsolutePath();
        }
        return new File(path + File.separator + imageCacheDirPath);
    }

    /**
     * 获取软件的版本编号
     *
     * @param context
     * @return
     */
    public int getAppVersion(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

    /*
     *把字符串编码成MD5值
     */
    private String hashkeyForDisk(String url) {
        String cacheKey = null;
        try {
            MessageDigest msgDigest = MessageDigest.getInstance("MD5");
            msgDigest.update(url.getBytes());
            BigInteger bigint = new BigInteger(msgDigest.digest());
            cacheKey = Long.toHexString(bigint.longValue());
//            cacheKey = bytesToHexString(msgDigest.digest());
            Logs.d("url--" + url);
            Logs.d("CacheKey--" + cacheKey);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            Logs.e(e.getMessage());
            cacheKey = String.valueOf(url.hashCode());
        }

        return cacheKey;
    }

    private String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }


    //异步加载图片类
    public void loadImage(Resources res, String url, ImageView imageView) {
        //增加内存缓存，判断内存缓存中是否有图片资源
        Bitmap bitmap = getBitmapFromMemoryCache(url);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
            return;
        }
        if (cancelPotentialWork(url, imageView)) {//判断之前任务是否存在，存在返回false 反之亦然；
            ImageAsyncTask imageAsyncTask = new ImageAsyncTask(imageView);
            AsyncDrawable asyncDrawable = new AsyncDrawable(res, BitmapFactory.decodeResource(res, R.drawable.base_list_default_icon), imageAsyncTask);
            imageView.setImageDrawable(asyncDrawable);
//            Logs.d("图片地址--》" + url);
            imageAsyncTask.execute(url);
        }
    }

    private boolean cancelPotentialWork(String url, ImageView imageView) {
        ImageAsyncTask imageAsyncTask = getImageAsyncTask(imageView);
        if (imageAsyncTask != null) {
            String httpurl = imageAsyncTask.url;
            if (httpurl == null) return true;
            if (!httpurl.equals(url)) {
                //取消之前的任务
                imageAsyncTask.cancel(true);
            } else {
                return false;
            }
        }
        return true;
    }

    //获取图片的异步任务
    private ImageAsyncTask getImageAsyncTask(ImageView imageView) {
        if (imageView != null) {
            Drawable drawable = imageView.getDrawable();
            if (drawable instanceof AsyncDrawable) {
                AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
                return asyncDrawable.getImageAsyncTask();
            }
        }

        return null;
    }

    class ImageAsyncTask extends AsyncTask<String, Void, Bitmap> {
        /**
         * 图片的若引用
         */
        WeakReference<ImageView> imageViewReference;

        String url;

        //初始化image若引用
        public ImageAsyncTask(ImageView image) {
            imageViewReference = new WeakReference<ImageView>(image);
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            url = params[0];
            Logs.e(url);
            String key = hashkeyForDisk(url);
            DiskLruCache.Snapshot snapshot = null;
            FileInputStream input = null;
            FileDescriptor filedescriptor = null;
            try {
                Logs.e(key);
                snapshot = mDiskLruCashe.get(key);

                if (snapshot == null) {
                    DiskLruCache.Editor edit = mDiskLruCashe.edit(key);
                    OutputStream out = edit.newOutputStream(0);
                    if (downloadToDisk(url, out)) {
                        edit.commit();
                    } else {
                        edit.abort();
                    }
                    snapshot = mDiskLruCashe.get(key);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (snapshot != null) {
                input = (FileInputStream) snapshot.getInputStream(0);
                try {
                    filedescriptor = input.getFD();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return BitmapFactory.decodeFileDescriptor(filedescriptor);

        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            //如果任务取消状态
            if (isCancelled()) {
                bitmap = null;
            }
            if (imageViewReference != null && bitmap != null) {
                ImageView imageView = imageViewReference.get();
                ImageAsyncTask imageAsyncTask = getImageAsyncTask(imageView);
//                Logs.i("imageView-->" + imageView);
//                Logs.i("imageAsyncTask-->" + imageAsyncTask);
                if (imageAsyncTask == this && imageView != null) {
//                    Logs.d("bitmap -->" + bitmap);
                    imageView.setImageBitmap(bitmap);
                    //增加到内存缓存中
                    addBitmapToMemoryCache(url, bitmap);
                }

            }
        }

        private boolean downloadToDisks(String urlString, OutputStream out) {
            BufferedInputStream input = null;
            BufferedOutputStream output = null;
            HttpURLConnection conn = null;

            try {
                URL httpUrl = new URL(url);
                conn = (HttpURLConnection) httpUrl.openConnection();
                conn.connect();
                Logs.e("conn.getInputStream() >>>>>>>>>>>>   :" + conn.getInputStream());
                input = new BufferedInputStream(conn.getInputStream());
                output = new BufferedOutputStream(out);
                int temp;
                while ((temp = input.read()) != -1) {
                    output.write(temp);
                }
                return true;
            } catch (java.net.UnknownHostException hoste) {
                hoste.printStackTrace();
                Logs.e("UnknownHostException >>> "+hoste.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (conn != null) conn.disconnect();
                try {
                    if (input != null)
                        input.close();
                    if (output != null)
                        output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return false;
        }


        private boolean downloadToDisk(String urlString, OutputStream out) {
            BufferedInputStream input = null;
            BufferedOutputStream output = null;
            HttpURLConnection conn = null;

            try {
                Logs.e("url --- "+urlString);
                HttpClient httpClient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(urlString);
                HttpResponse response = httpClient.execute(httpGet);
                int statusCode = response.getStatusLine().getStatusCode();
                if(statusCode == 200){
                    InputStream inputStream = response.getEntity().getContent();
                    input = new BufferedInputStream(inputStream);
                    output = new BufferedOutputStream(out);
                    int temp;
                    while ((temp = input.read()) != -1) {
                        output.write(temp);
                }
                    return true;
                }

            } catch (java.net.UnknownHostException hoste) {
//                hoste.printStackTrace();
                Logs.e("UnknownHostException >>> "+hoste.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (conn != null) conn.disconnect();
                try {
                    if (input != null)
                        input.close();
                    if (output != null)
                        output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return false;
        }



        //联网获取Bitmap对象
        private Bitmap getBitmapByHttp(String url) {
            InputStream input = null;
            try {
                URL httpUrl = new URL(url);
                input = httpUrl.openStream();
                Bitmap bitmap = BitmapFactory.decodeStream(input);
                return bitmap;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (input != null)
                        input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

    }

    //创建一个专用异步图片的类，包含任务对象 ，当图片需要重新加载时可复用此任务
    class AsyncDrawable extends BitmapDrawable {
        //包含图片异步任务对象的若引用
        WeakReference<ImageAsyncTask> imageAsyncTaskReference;

        public AsyncDrawable(Resources res, Bitmap bitmap, ImageAsyncTask imageAsyncTask) {
            super(res, bitmap);
            imageAsyncTaskReference = new WeakReference<ImageAsyncTask>(imageAsyncTask);
        }

        public ImageAsyncTask getImageAsyncTask() {
            return imageAsyncTaskReference.get();
        }
    }
}
