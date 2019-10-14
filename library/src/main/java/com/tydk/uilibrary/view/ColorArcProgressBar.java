package com.tydk.uilibrary.view;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import com.tydk.uilibrary.R;

/**
 * @author: zzs
 * @date: 2019-05-28 下午 3:09
 * @description: 自定义彩虹圆弧
*/
public class ColorArcProgressBar extends View {
    private final int DEGREE_PROGRESS_DISTANCE = dipToPx(8.0f);
    private Paint allArcPaint;
    private int aniSpeed = 1000;
    private String bgArcColor = "#30000000";
    private float bgArcWidth = ((float) dipToPx(2.0f));
    private RectF bgRect;
    private float centerX;
    private float centerY;
    private int[] colors = new int[]{Color.parseColor("#FFFF00"),
            Color.parseColor("#FFA500"),Color.parseColor("#FF0000")};
    private Paint curSpeedPaint;
    private float curSpeedSize = ((float) dipToPx(15.0f));
    private float curValues = 100.0f;
    private float currentAngle = 0.0f;
    private Paint degreePaint;
    private int diameter = 500;
    private String hintColor = "#676767";
    private Paint hintPaint;
    private float hintSize = ((float) dipToPx(18.0f));
    private String hintString;
    private boolean isNeedContent;
    private boolean isNeedDial;
    private boolean isNeedTitle;
    private boolean isNeedUnit;
    private boolean isShowCurrentSpeed = true;
    private float k;
    private float lastAngle;
    private String longDegreeColor = "#111111";
    private float longdegree = ((float) dipToPx(13.0f));
    private PaintFlagsDrawFilter mDrawFilter;
    private int mHeight;
    private int mWidth;
    private float maxValues = 100.0f;
    private ValueAnimator progressAnimator;
    private Paint progressPaint;
    private float progressWidth = ((float) dipToPx(10.0f));
    private Matrix rotateMatrix;
    private String shortDegreeColor = "#111111";
    private float shortdegree = ((float) dipToPx(5.0f));
    private float startAngle = -180.0f;
    private float sweepAngle = 180.0f;
    private SweepGradient sweepGradient;
    private float textSize = ((float) dipToPx(40.0f));
    private String titleString;
    private Paint vTextPaint;

    public ColorArcProgressBar(Context context) {
        super(context, null);
        initView();
    }

    public ColorArcProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        initCofig(context, attrs);
        initView();
    }

    public ColorArcProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initCofig(context, attrs);
        initView();
    }

    private void initCofig(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ColorArcProgressBar);
        int color1 = a.getColor(R.styleable.ColorArcProgressBar_front_color1, Color.parseColor("#FFFF00"));
        int color2 = a.getColor(R.styleable.ColorArcProgressBar_front_color2, Color.parseColor("#FFA500"));
        int color3 = a.getColor(R.styleable.ColorArcProgressBar_front_color3, Color.parseColor("#FF0000"));
        colors = new int[]{color1,color1,color2,color3,color3};

        sweepAngle = a.getInteger(R.styleable.ColorArcProgressBar_total_engle, 180);
        bgArcWidth = a.getDimension(R.styleable.ColorArcProgressBar_back_width, dipToPx(2));
        progressWidth = a.getDimension(R.styleable.ColorArcProgressBar_front_width, dipToPx(10));
        isNeedTitle = a.getBoolean(R.styleable.ColorArcProgressBar_is_need_title, false);
        isNeedContent = a.getBoolean(R.styleable.ColorArcProgressBar_is_need_content, false);
        isNeedUnit = a.getBoolean(R.styleable.ColorArcProgressBar_is_need_unit, false);
        isNeedDial = a.getBoolean(R.styleable.ColorArcProgressBar_is_need_dial, false);
        hintString = a.getString(R.styleable.ColorArcProgressBar_string_unit);
        titleString = a.getString(R.styleable.ColorArcProgressBar_string_title);
        curValues = a.getFloat(R.styleable.ColorArcProgressBar_current_value, 100f);
        maxValues = a.getFloat(R.styleable.ColorArcProgressBar_max_value, 100);
        setCurrentValues(curValues);
        setMaxValues(maxValues);
        a.recycle();
    }

    /* Access modifiers changed, original: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension((int) ((((this.longdegree * 2.0f) + this.progressWidth) + ((float) this.diameter)) + ((float) (this.DEGREE_PROGRESS_DISTANCE * 2))), (((int) ((((this.longdegree * 2.0f) + this.progressWidth) + ((float) this.diameter)) + ((float) (this.DEGREE_PROGRESS_DISTANCE * 2)))) / 2) + 50);
    }

    private void initView() {
        this.diameter = (getScreenWidth() * 3) / 6;
        this.bgRect = new RectF();
        this.bgRect.top = 20.0f;
        this.bgRect.left = (this.longdegree + (this.progressWidth / 2.0f)) + ((float) this.DEGREE_PROGRESS_DISTANCE);
        this.bgRect.right = ((float) this.diameter) + ((this.longdegree + (this.progressWidth / 2.0f)) + ((float) this.DEGREE_PROGRESS_DISTANCE));
        this.bgRect.bottom = ((float) this.diameter) + ((this.longdegree + (this.progressWidth / 2.0f)) + ((float) this.DEGREE_PROGRESS_DISTANCE));
        this.centerX = ((((this.longdegree * 2.0f) + this.progressWidth) + ((float) this.diameter)) + ((float) (this.DEGREE_PROGRESS_DISTANCE * 2))) / 2.0f;
        this.centerY = ((((this.longdegree * 2.0f) + this.progressWidth) + ((float) this.diameter)) + ((float) (this.DEGREE_PROGRESS_DISTANCE * 2))) / 2.0f;
        this.degreePaint = new Paint();
        this.degreePaint.setColor(Color.parseColor(this.longDegreeColor));
        this.allArcPaint = new Paint();
        this.allArcPaint.setAntiAlias(true);
        this.allArcPaint.setStyle(Style.STROKE);
        this.allArcPaint.setStrokeWidth(this.bgArcWidth);
        this.allArcPaint.setColor(Color.parseColor(this.bgArcColor));
        this.allArcPaint.setStrokeCap(Cap.ROUND);
        this.progressPaint = new Paint();
        this.progressPaint.setAntiAlias(true);
        this.progressPaint.setStyle(Style.STROKE);
        this.progressPaint.setStrokeCap(Cap.ROUND);
        this.progressPaint.setStrokeWidth(this.progressWidth);
        this.progressPaint.setColor(-16711936);
        this.vTextPaint = new Paint();
        this.vTextPaint.setTextSize(this.textSize);
        this.vTextPaint.setColor(Color.parseColor("#ffffff"));
        this.vTextPaint.setTextAlign(Align.CENTER);
        this.hintPaint = new Paint();
        this.hintPaint.setTextSize(this.hintSize);
        this.hintPaint.setColor(Color.parseColor("#ffffff"));
        this.hintPaint.setTextAlign(Align.CENTER);
        this.curSpeedPaint = new Paint();
        this.curSpeedPaint.setTextSize(this.curSpeedSize);
        this.curSpeedPaint.setColor(Color.parseColor("#ffffff"));
        this.curSpeedPaint.setTextAlign(Align.CENTER);
        this.mDrawFilter = new PaintFlagsDrawFilter(0, 3);
        this.sweepGradient = new SweepGradient(this.centerX, this.centerY, this.colors, null);
        this.rotateMatrix = new Matrix();
    }

    /* Access modifiers changed, original: protected */
    public void onDraw(Canvas canvas) {
        canvas.setDrawFilter(this.mDrawFilter);
        if (this.isNeedDial) {
            int i = 0;
            while (i < 40) {
                if (i <= 15 || i >= 25) {
                    if (i % 5 == 0) {
                        this.degreePaint.setStrokeWidth((float) dipToPx(2.0f));
                        this.degreePaint.setColor(Color.parseColor(this.longDegreeColor));
                        canvas.drawLine(this.centerX, ((this.centerY - ((float) (this.diameter / 2))) - (this.progressWidth / 2.0f)) - ((float) this.DEGREE_PROGRESS_DISTANCE), this.centerX, (((this.centerY - ((float) (this.diameter / 2))) - (this.progressWidth / 2.0f)) - ((float) this.DEGREE_PROGRESS_DISTANCE)) - this.longdegree, this.degreePaint);
                    } else {
                        this.degreePaint.setStrokeWidth((float) dipToPx(1.4f));
                        this.degreePaint.setColor(Color.parseColor(this.shortDegreeColor));
                        canvas.drawLine(this.centerX, (((this.centerY - ((float) (this.diameter / 2))) - (this.progressWidth / 2.0f)) - ((float) this.DEGREE_PROGRESS_DISTANCE)) - ((this.longdegree - this.shortdegree) / 2.0f), this.centerX, ((((this.centerY - ((float) (this.diameter / 2))) - (this.progressWidth / 2.0f)) - ((float) this.DEGREE_PROGRESS_DISTANCE)) - ((this.longdegree - this.shortdegree) / 2.0f)) - this.shortdegree, this.degreePaint);
                    }
                    canvas.rotate(9.0f, this.centerX, this.centerY);
                } else {
                    canvas.rotate(9.0f, this.centerX, this.centerY);
                }
                i++;
            }
        }
        canvas.drawArc(this.bgRect, this.startAngle, this.sweepAngle, false, this.allArcPaint);
        this.rotateMatrix.setRotate(130.0f, this.centerX, this.centerY);
        this.sweepGradient.setLocalMatrix(this.rotateMatrix);
        this.progressPaint.setShader(this.sweepGradient);
        canvas.drawArc(this.bgRect, this.startAngle, this.currentAngle, false, this.progressPaint);
        canvas.drawText(String.format("%.0f", new Object[]{Float.valueOf(this.curValues)}), this.centerX, this.centerY - this.textSize, this.vTextPaint);
        canvas.drawText("总人数", this.centerX, this.centerY - (this.textSize / 3.0f), this.curSpeedPaint);
        if (this.isNeedContent) {
            canvas.drawText(String.format("%.0f", new Object[]{Float.valueOf(this.curValues)}), this.centerX, this.centerY - (this.textSize / 3.0f), this.vTextPaint);
        }
        if (this.isNeedUnit) {
            canvas.drawText(this.hintString, this.centerX, this.centerY + (this.textSize / 6.0f), this.hintPaint);
        }
        if (this.isNeedTitle) {
            canvas.drawText(this.titleString, this.centerX, this.centerY - ((this.textSize * 2.0f) / 3.0f), this.curSpeedPaint);
        }
        invalidate();
    }

    public void setMaxValues(float maxValues) {
        this.maxValues = maxValues;
        this.k = this.sweepAngle / maxValues;
    }

    public void setCurrentValues(float currentValues) {
        setMaxValues(currentValues);
        if (currentValues > this.maxValues) {
            currentValues = this.maxValues;
        }
        if (currentValues < 0.0f) {
            currentValues = 0.0f;
        }
        this.curValues = currentValues;
        this.lastAngle = this.currentAngle;
        setAnimation(this.lastAngle, this.k * currentValues, this.aniSpeed);
    }

    public void setBgArcWidth(int bgArcWidth) {
        this.bgArcWidth = (float) bgArcWidth;
    }

    public void setProgressWidth(int progressWidth) {
        this.progressWidth = (float) progressWidth;
    }

    public void setTextSize(int textSize) {
        this.textSize = (float) textSize;
    }

    public void setHintSize(int hintSize) {
        this.hintSize = (float) hintSize;
    }

    public void setUnit(String hintString) {
        this.hintString = hintString;
        invalidate();
    }

    public void setDiameter(int diameter) {
        this.diameter = dipToPx((float) diameter);
    }

    private void setTitle(String title) {
        this.titleString = title;
    }

    private void setIsNeedTitle(boolean isNeedTitle) {
        this.isNeedTitle = isNeedTitle;
    }

    private void setIsNeedUnit(boolean isNeedUnit) {
        this.isNeedUnit = isNeedUnit;
    }

    private void setIsNeedDial(boolean isNeedDial) {
        this.isNeedDial = isNeedDial;
    }

    private void setAnimation(float last, float current, int length) {
        this.progressAnimator = ValueAnimator.ofFloat(new float[]{last, current});
        this.progressAnimator.setDuration((long) length);
        this.progressAnimator.setTarget(Float.valueOf(this.currentAngle));
        this.progressAnimator.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                ColorArcProgressBar.this.currentAngle = ((Float) animation.getAnimatedValue()).floatValue();
                ColorArcProgressBar.this.curValues = ColorArcProgressBar.this.currentAngle / ColorArcProgressBar.this.k;
            }
        });
        this.progressAnimator.start();
    }

    private int dipToPx(float dip) {
        return (int) ((((float) (dip >= 0.0f ? 1 : -1)) * 0.5f) + (dip * getContext().getResources().getDisplayMetrics().density));
    }

    private int getScreenWidth() {
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }
}
