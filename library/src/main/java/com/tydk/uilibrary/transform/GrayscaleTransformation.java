package com.tydk.uilibrary.transform;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.support.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;


import java.security.MessageDigest;

/**
 * @author: zzs
 * @date: 2019/10/14 0014 下午 4:36
 * @description:
 */
public class GrayscaleTransformation extends BitmapTransformation {

    private static final int VERSION = 1;
    private static final String ID = "GrayscaleTransformation" + VERSION;

    @Override
    public String toString() {
        return "GrayscaleTransformation()";
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof GrayscaleTransformation;
    }

    @Override
    public int hashCode() {
        return ID.hashCode();
    }

    @Override
    protected Bitmap transform(@NonNull Context context, @NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        int width = toTransform.getWidth();
        int height = toTransform.getHeight();

        Bitmap.Config config =
                toTransform.getConfig() != null ? toTransform.getConfig() : Bitmap.Config.ARGB_8888;
        Bitmap bitmap = pool.get(width, height, config);

        setCanvasBitmapDensity(toTransform, bitmap);

        Canvas canvas = new Canvas(bitmap);
        ColorMatrix saturation = new ColorMatrix();
        saturation.setSaturation(0f);
        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(saturation));
        canvas.drawBitmap(toTransform, 0, 0, paint);

        return bitmap;
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update((ID).getBytes(CHARSET));
    }

}
