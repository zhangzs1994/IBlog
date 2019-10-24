package com.tydk.iblog;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class WaterMarkBg extends Drawable {
    private static final String TAG = "WaterMarkBg";

    private Paint paint = new Paint();
    private Bitmap mBitMap = null;

    private int mColor = Color.parseColor("#FFFFFF");
    private int mPaintColor = Color.parseColor("#30AEAEAE");
    private String logo = "水印";
    private boolean isCenter = false;

    public WaterMarkBg(String logo) {
        this.logo = logo;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {

        int width = getBounds().right;
        int height = getBounds().bottom;
        if (mBitMap == null) {
            canvas.drawColor(mColor);
        } else {
            canvas.drawBitmap(mBitMap, 0, 0, null);
        }
        paint.setColor(mPaintColor);
        paint.setAntiAlias(true);
        paint.setTextSize(60);
        canvas.save();
        canvas.rotate(-30, width / 2, height / 2);
        float textWidth = paint.measureText(logo);
        if (isCenter) {
            float fromX = (width - textWidth) / 2;
            float fromY = height / 2 + Math.abs(paint.ascent() + paint.descent()) / 2;
            canvas.drawText(logo, fromX, fromY, paint);
        } else {
            int index = 0;
            for (int positionY = height / 10; positionY <= height; positionY += height / 10) {
                float fromX = -width + (index++ % 2) * textWidth;
                for (float positionX = fromX; positionX < width; positionX += textWidth * 2) {
                    canvas.drawText(logo, positionX, positionY, paint);
                }
            }
        }
        canvas.restore();
    }

    public void setBitmap(Bitmap bitMap) {
        this.mBitMap = bitMap;
    }

    public void setColor(int color) {
        this.mColor = color;
    }

    public void setPaintColor(int color) {
        this.mPaintColor = color;
    }

    public void setIsCenter(boolean isCenter) {
        this.isCenter = isCenter;
    }

    @Override
    public void setAlpha(@IntRange(from = 0, to = 255) int alpha) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.UNKNOWN;
    }
}