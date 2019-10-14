package com.tydk.uilibrary.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.tydk.uilibrary.R;
import com.tydk.uilibrary.transform.BlurTransformation;
import com.tydk.uilibrary.transform.GlideCircleTransform;
import com.tydk.uilibrary.transform.GrayscaleTransformation;
import com.youth.banner.loader.ImageLoader;

/**
 * @author: zzs
 * @date: 2019-08-02 上午 10:00
 * @description: 图片加载器(简单封装Glide)
 */
public class GlideUtils extends ImageLoader {
    static GlideUtils instance;

    public GlideUtils() {
    }

    public static GlideUtils getInstance() {
        if (null == instance) {
            synchronized (GlideUtils.class) {
                if (null == instance) {
                    instance = new GlideUtils();
                }
            }
        }
        return instance;
    }

    /**
     * 普通加载
     *
     * @param context
     * @param url
     * @param imageView
     */
    public void loadImage(Context context, Object url, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.default_img)
                .error(R.drawable.default_img);
        Glide.with(context).load(url).apply(options).into(imageView);
    }

    /**
     * 加载圆形图片
     *
     * @param context
     * @param url
     * @param imageView
     */
    public void loadCircleImage(Context context, String url, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .circleCrop()
                .placeholder(R.drawable.default_img)
                .error(R.drawable.default_img);
        Glide.with(context).load(url).apply(options).into(imageView);
    }

    /**
     * 加载圆形带圆边图片
     *
     * @param context
     * @param url
     * @param imageView
     */
    public void loadCircleWithFrameImage(Context context, Object url, ImageView imageView, int borderWidth, int borderColor) {
        RequestOptions options = RequestOptions
                .bitmapTransform(new GlideCircleTransform(borderWidth, borderColor))
                .placeholder(R.drawable.default_img)
                .error(R.drawable.default_img);
        Glide.with(context).load(url).apply(options).into(imageView);
    }

    /**
     * 加载圆形带圆边图片
     *
     * @param context
     * @param url
     * @param defaults
     * @param imageView
     */
    public void loadCircleWithFrameImage(Context context, Object url, int defaults, ImageView imageView, int borderWidth, int borderColor) {
        RequestOptions options = RequestOptions
                .bitmapTransform(new GlideCircleTransform(borderWidth, borderColor))
                .placeholder(defaults)
                .error(defaults);
        Glide.with(context).load(url).apply(options).into(imageView);
    }

    /**
     * 加载圆角图片
     *
     * @param context
     * @param url
     * @param radius
     * @param imageView
     */
    public void loadRoundImage(Context context, String url, int radius, ImageView imageView) {
        RequestOptions options = RequestOptions
                .bitmapTransform(new RoundedCorners(radius))
                .placeholder(R.drawable.default_img)
                .error(R.drawable.default_img);
        Glide.with(context).load(url).apply(options).into(imageView);
    }

    /**
     * 指定图片大小;使用override()方法指定了一个图片的尺寸。
     * Glide现在只会将图片加载成width*height像素的尺寸，而不会管你的ImageView的大小是多少了。
     * 如果你想加载一张图片的原始尺寸的话，可以使用Target.SIZE_ORIGINAL关键字----override(Target.SIZE_ORIGINAL)
     *
     * @param context
     * @param url
     * @param imageView
     * @param width
     * @param height
     */
    public void loadImageSize(Context context, String url, ImageView imageView, int width, int height) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.default_img)
                .error(R.drawable.default_img)
                .override(width, height)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context).load(url).apply(options).into(imageView);
    }

    /**
     * 禁用内存缓存功能
     * diskCacheStrategy()方法基本上就是Glide硬盘缓存功能的一切，它可以接收五种参数：
     * <p>
     * DiskCacheStrategy.NONE： 表示不缓存任何内容。
     * DiskCacheStrategy.DATA： 表示只缓存原始图片。
     * DiskCacheStrategy.RESOURCE： 表示只缓存转换过后的图片。
     * DiskCacheStrategy.ALL ： 表示既缓存原始图片，也缓存转换过后的图片。
     * DiskCacheStrategy.AUTOMATIC： 表示让Glide根据图片资源智能地选择使用哪一种缓存策略（默认选项）。
     */

    public void loadImageSizeSkipMemoryCache(Context context, String url, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.default_img)
                .error(R.drawable.default_img)
                .skipMemoryCache(true)//禁用掉Glide的内存缓存功能
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context).load(url).apply(options).into(imageView);
    }

    /**
     * 预先加载图片
     * 在使用图片之前，预先把图片加载到缓存，调用了预加载之后，我们以后想再去加载这张图片就会非常快了，
     * 因为Glide会直接从缓存当中去读取图片并显示出来
     */
    public void preloadImage(Context context, String url) {
        Glide.with(context)
                .load(url)
                .preload();
    }

    /**
     * 加载模糊图片（自定义透明度）
     *
     * @param context
     * @param url
     * @param imageView
     * @param blur      模糊度，一般1-100够了，越大越模糊
     */
    public void loadBlurImage(Context context, String url, ImageView imageView, int blur) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.default_img)
                .error(R.drawable.default_img)
                .bitmapTransform(new BlurTransformation(blur))
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context).load(url).apply(options).into(imageView);
    }

    /*
     *加载灰度(黑白)图片（自定义透明度）
     */
    public void loadBlackImage(Context context, String url, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.default_img)
                .error(R.drawable.default_img)
                .bitmapTransform(new GrayscaleTransformation())
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context).load(url).apply(options).into(imageView);
    }

    /**
     * Glide.with(this).asGif()    //强制指定加载动态图片
     * 如果加载的图片不是gif，则asGif()会报错， 当然，asGif()不写也是可以正常加载的。
     * 加入了一个asBitmap()方法，这个方法的意思就是说这里只允许加载静态图片，不需要Glide去帮我们自动进行图片格式的判断了。
     * 如果你传入的还是一张GIF图的话，Glide会展示这张GIF图的第一帧，而不会去播放它。
     *
     * @param context
     * @param url       例如：https://image.niwoxuexi.com/blog/content/5c0d4b1972-loading.gif
     * @param imageView
     */
    private void loadGif(Context context, String url, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.default_img)
                .error(R.drawable.default_img);
        Glide.with(context)
                .load(url)
                .apply(options)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(imageView);

    }

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        loadImage(context, path, imageView);
    }
}