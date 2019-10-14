package com.tydk.uilibrary.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import com.tydk.uilibrary.R;

/**
 * @author: zzs
 * @date: 2019-06-26 上午 9:48
 * @description: 自定义水印
 */
public class WatermarkView extends View {
    private int strokeOutWidth = 10;
    private int strokeInWidth = 2;
    private int textSize = 40;
    private int borderCol;
    private String text = "黄色";

    private Paint outPaint;
    private Paint inPaint;
    private Paint paint;

    public WatermarkView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.WatermarkView, 0, 0);
        strokeOutWidth = a.getInteger(R.styleable.WatermarkView_strokeOutWidth, 10);
        strokeInWidth = a.getInteger(R.styleable.WatermarkView_strokeInWidth, 2);
        borderCol = a.getInteger(R.styleable.WatermarkView_borderColor, Color.WHITE);
        textSize = a.getInteger(R.styleable.WatermarkView_textSize, 40);
        text = a.getString(R.styleable.WatermarkView_text);

        outPaint = new Paint();
        outPaint.setStyle(Paint.Style.STROKE);
        outPaint.setStrokeWidth(strokeOutWidth);
        outPaint.setAntiAlias(true);

        inPaint = new Paint();
        inPaint.setStyle(Paint.Style.STROKE);
        inPaint.setStrokeWidth(strokeInWidth);
        inPaint.setAntiAlias(true);

        paint = new Paint();
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(textSize);
        paint.setFakeBoldText(true);
        paint.setTypeface(Typeface.DEFAULT_BOLD);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        outPaint.setColor(borderCol);
        inPaint.setColor(borderCol);
        paint.setColor(borderCol);
        int w = this.getMeasuredWidth();
        int h = this.getMeasuredHeight();
        RectF or = new RectF(2, 2, w - 2, h - 2);
        canvas.drawRect(or, outPaint);

        RectF ir = new RectF(25, 25, w - 25, h - 25);
        canvas.drawRect(ir, inPaint);

        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float top = fontMetrics.top;//为基线到字体上边框的距离,即上图中的top
        float bottom = fontMetrics.bottom;//为基线到字体下边框的距离,即上图中的bottom
        int baseLineY = (int) (or.centerY() - top / 2 - bottom / 2);//基线中间点的y轴计算公式
        canvas.drawText(text, or.centerX(), baseLineY, paint);
        super.onDraw(canvas);
    }

    public int getBorderColor() {
        return borderCol;
    }

    public void setBorderColor(int color) {
        this.borderCol = color;
        invalidate();
        requestLayout();
    }

    public void setTextValue(String text) {
        this.text = text;
        invalidate();
        requestLayout();
    }

}