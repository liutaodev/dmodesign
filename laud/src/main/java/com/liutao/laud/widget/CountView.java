package com.liutao.laud.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.liutao.laud.R;
import com.liutao.laud.utils.DisplayUtil;

public class CountView extends View {
    private static final String DEFAULT_TEXT_COLOR = "#cccccc";
    public static final float DEFAULT_TEXT_SIZE = 17f;
    //画文字的画笔
    private Paint mTextPaint;
    //点赞数量
    private int mCount;
    //文字大小
    private float mTextSize;
    //文字的位置信息
    private TuvPoint mTextPoint;
    //字体颜色
    private int mTextColor;

    public CountView(Context context){
        this(context,null);
    }

    public CountView(Context context, AttributeSet attributeSet){
        this(context,attributeSet,0);
    }

    public CountView(Context context,AttributeSet attributeSet,int defStyleAttr){
        super(context,attributeSet,defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(R.styleable.CountView);
        mCount = typedArray.getInt(R.styleable.CountView_cv_count, 99);
        mTextSize = typedArray.getDimension(R.styleable.CountView_cv_text_size,DisplayUtil.getInstance().sp2px(context,DEFAULT_TEXT_SIZE));
        mTextColor = typedArray.getColor(R.styleable.CountView_cv_text_color, Color.parseColor(DEFAULT_TEXT_COLOR));
        typedArray.recycle();
        init();
    }

    private void init(){
        mTextPaint = new Paint();
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(mTextColor);
        mTextPoint = new TuvPoint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(DisplayUtil.getInstance().getDefaultSize(widthMeasureSpec, getContentWidth() + getPaddingLeft() + getPaddingRight()),
                DisplayUtil.getInstance().getDefaultSize(heightMeasureSpec, getContentHeight() + getPaddingTop() + getPaddingBottom()));
    }

    private int getContentWidth(){
        return (int) Math.ceil(mTextPaint.measureText(String.valueOf(mCount)));
    }

    private int getContentHeight(){
        return (int) mTextSize;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        locateText();
    }

    private void locateText(){

        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        float textY = getPaddingTop() + (getContentHeight() - fontMetrics.bottom - fontMetrics.top)/2;
        mTextPoint.y = textY;
        mTextPoint.x = getPaddingLeft();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText(String.valueOf(mCount),mTextPoint.x,mTextPoint.y,mTextPaint);
    }

    public void setTextSize(float textSize) {
        this.mTextSize = textSize;
        requestLayout();
    }

    public void setLaudCount(int count){
        this.mCount = count;
        requestLayout();
    }
}
