package com.qianyingjiuzhu.circleindicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author XuNeverMore
 * @QQ 1045530120
 * @create on 2017/10/24 0024
 * @github https://github.com/XuNeverMore
 */

public class CircleIndicator extends View implements ViewPager.OnPageChangeListener {

    private ViewPager mViewPager;
    private int mRadius = 5;//圆点半径
    private int mSpacing = 15;//圆心之间的间距
    private int count = 0;
    private int mCurrentPoi = 0;
    private float mPositionOffset = 0f;
    private Paint mPaint;

    private int colorNormal = 0xff999999;
    private int colorSelected = 0xff4caf65;
    private boolean snapEnable;

    public CircleIndicator(Context context) {
        this(context, null);
    }

    public CircleIndicator(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleIndicator, R.attr.cStyle, 0);

        mRadius = typedArray.getDimensionPixelSize(R.styleable.CircleIndicator_circleRadius, 5);
        mSpacing = typedArray.getDimensionPixelSize(R.styleable.CircleIndicator_circleSpacing, 15);

        colorNormal = typedArray.getColor(R.styleable.CircleIndicator_colorNormal, colorNormal);
        colorSelected = typedArray.getColor(R.styleable.CircleIndicator_colorSelected, colorSelected);

        snapEnable = typedArray.getBoolean(R.styleable.CircleIndicator_snapEnable, true);

        typedArray.recycle();
        init();
    }

    private void init() {

        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);

    }


    public void setUpWithPager(ViewPager viewPager) {
        if (viewPager == null || viewPager.getAdapter() == null) {
            return;
        }
        if (mViewPager != null) {
            mViewPager.removeOnPageChangeListener(this);
        }
        mViewPager = viewPager;
        mCurrentPoi = mViewPager.getCurrentItem();
        mViewPager.addOnPageChangeListener(this);
        invalidate();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mViewPager != null) {

            PagerAdapter adapter = mViewPager.getAdapter();
            count = adapter.getCount();
            int height = mRadius * 2;
            int width = mRadius * 2 + mSpacing * (count - 1);
            setMeasuredDimension(width, height);

        }


    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        if (snapEnable) {
            mCurrentPoi = position;
            mPositionOffset = positionOffset;
            invalidate();
        }
    }

    @Override
    public void onPageSelected(int position) {
        if (!snapEnable) {
            mCurrentPoi = position;
            invalidate();
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (count != 0) {
            for (int i = 0; i < count; i++) {
                drawCircle(canvas, i, 0, colorNormal);
            }

            drawCircle(canvas, mCurrentPoi, mPositionOffset, colorSelected);
        }
    }

    private void drawCircle(Canvas canvas, int position, float positionOffset, @ColorInt int color) {
        float x = (int) (mRadius + position * mSpacing + positionOffset * mSpacing);
        mPaint.setColor(color);
        canvas.drawCircle(x, mRadius, mRadius, mPaint);
    }
}
