package com.swkj.common.util;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import androidx.annotation.UiThread;

import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.swkj.common.R;

import java.io.File;
import java.lang.ref.WeakReference;

/**
 * author: 17826
 * created on: 2018/12/26 11:04
 * description: 封装Glide加载图片工具类
 */
public class GlideUtils {

    private static Context mContext;
    private static GlideUtils sGlideUtils;

    private static WeakReference<Context> mWeakReference;

    private final RequestManager mManager;

    /**
     * 将构造方法私有
     */
    private GlideUtils() {
        mManager = Glide.with(Utils.getApp());
        init(Utils.getApp());
    }

    /**
     * 初始化Glide
     *
     * @param context 上下文
     */
    public static void init(Context context) {
        mWeakReference = new WeakReference<>(context);
        mContext = context;
    }

    /**
     * 双重锁单例模式
     *
     * @return GlideUtils对象
     */
    public static GlideUtils getInstance() {
        if (sGlideUtils == null) {
            synchronized (GlideUtils.class) {
                if (sGlideUtils == null) {
                    sGlideUtils = new GlideUtils();
                }
            }
        }
        return sGlideUtils;
    }

    public RequestManager getGlide() {
        return mManager;
    }

    /**
     * 加载图片
     *
     * @param imgUrl    图片地址
     * @param imageView 图片视图
     */
    public void loadImg(String imgUrl, ImageView imageView) {
        mManager.load(imgUrl)
                .error(R.drawable.ic_pic)
                .into(imageView);
    }

    /**
     * 加载图片
     *
     * @param imgUrl    图片地址
     * @param imageView 图片视图
     */
    public void loadWorkImg(String imgUrl, ImageView imageView) {
        mManager.load(imgUrl)
                .error(R.drawable.ic_burst)
                .into(imageView);
    }

    /**
     * 加载图片
     *
     * @param imgUrl    图片地址
     * @param imageView 图片视图
     */
    public void loadImg(Uri imgUrl, ImageView imageView) {
        mManager.load(imgUrl)
                .error(R.drawable.ic_pic)
                .into(imageView);
    }

    /**
     * 加载圆形图片
     *
     * @param imgUrl    图片地址
     * @param imageView 图片视图
     */
    public void loadCircleImg(String imgUrl, ImageView imageView) {
        mManager.load(imgUrl)
                .error(R.drawable.photo)
                .apply(new RequestOptions().circleCrop())
                .into(imageView);
    }

    /**
     * 加载圆形图片
     *
     * @param imgUrl    图片地址
     * @param imageView 图片视图
     */
    public void loadCircleImg(File imgUrl, ImageView imageView) {
        mManager.load(imgUrl)
                .apply(new RequestOptions().circleCrop())
                .error(R.drawable.ic_pic)
                .into(imageView);
    }

    /**
     * 加载上两个圆角图片
     *
     * @param radiusSize 圆角大小，单位px
     * @param imgUrl     图片地址
     * @param imageView  图片视图
     */
    public void loadRadiusTopImg(int radiusSize, String imgUrl, ImageView imageView) {
        GlideCircleTransform transform = new GlideCircleTransform(mContext, 1, radiusSize);
        transform.setExceptCorner(false, false, true, true);
        //Glide加载圆角图片，并且图片缩放全屏
        RequestOptions options = new RequestOptions().transforms(transform);

        mManager.asBitmap()
                .load(imgUrl)
                .apply(options)
                .error(R.drawable.ic_pic)
                .into(imageView);
    }

    /**
     * 加载圆角图片
     *
     * @param radiusSize 圆角大小，单位px
     * @param imgUrl     图片地址
     * @param imageView  图片视图
     */
    @UiThread
    public void loadRadiusImg(int radiusSize, String imgUrl, ImageView imageView) {
        loadRadiusImg(radiusSize, imgUrl, imageView, false);
    }

    /**
     * 加载圆角图片
     *
     * @param radiusSize 圆角大小，单位px
     * @param imgUrl     图片地址
     * @param imageView  图片视图
     */
    @UiThread
    public void loadRadiusImg(int radiusSize, String imgUrl, ImageView imageView, boolean isLocal) {
        GlideCircleTransform transform = new GlideCircleTransform(mContext, 1, radiusSize);
        transform.setExceptCorner(false, false, false, false);
        //Glide加载圆角图片，并且图片缩放全屏
        RequestOptions options = new RequestOptions().transforms(transform);
        DiskCacheStrategy strategy = DiskCacheStrategy.AUTOMATIC;
        if (isLocal) {
            strategy = DiskCacheStrategy.NONE;
        }
        mManager.asBitmap()
                .load(imgUrl)
                .apply(options)
                .diskCacheStrategy(strategy)
                .error(R.drawable.ic_pic)
                .into(imageView);
    }

    /**
     * 加载圆角图片
     *
     * @param radiusSize 圆角大小，单位px
     * @param imgUrl     图片地址
     * @param imageView  图片视图
     */
    @UiThread
    public void loadRadiusImg(int radiusSize, Uri imgUrl, ImageView imageView) {
        float density = mWeakReference.get()
                .getResources()
                .getDisplayMetrics().density;
        GlideCircleTransform transform = new GlideCircleTransform(mContext, 1, radiusSize);
        transform.setExceptCorner(false, false, false, false);
        //Glide加载圆角图片，并且图片缩放全屏
        RequestOptions options = new RequestOptions().transforms(transform);
        mManager.asBitmap()
                .load(imgUrl)
                .apply(options)
                .error(R.drawable.ic_pic)
                .into(imageView);
    }

    /**
     * 加载圆角图片
     *
     * @param radiusSize 圆角大小，单位px
     * @param imgUrl     图片地址
     * @param imageView  图片视图
     */
    public void loadRadiusImg(int radiusSize, File imgUrl, ImageView imageView) {
        float density = mWeakReference.get()
                .getResources()
                .getDisplayMetrics().density;
        GlideCircleTransform transform = new GlideCircleTransform(mContext, 1, radiusSize);
        transform.setExceptCorner(false, false, false, false);
        //Glide加载圆角图片，并且图片缩放全屏
        RequestOptions options = new RequestOptions().transforms(transform);

        mManager.asBitmap()
                .load(imgUrl)
                .apply(options)
                .error(R.drawable.ic_pic)
                .into(imageView);
    }
}
