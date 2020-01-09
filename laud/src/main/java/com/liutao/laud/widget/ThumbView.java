package com.liutao.laud.widget;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.OvershootInterpolator;

import com.liutao.laud.R;
import com.liutao.laud.utils.DisplayUtil;

public class ThumbView extends View {
    //圆圈的默认颜色
    private static final String DEFAULT_CIRCLE_COLOR = "#cccccc";
    //圆圈扩散动画的时间
    private static final int RADIUS_DURING = 100;
    private static final float SCALE_MIN = 0.7f;
    private static final float SCALE_MAX = 1f;
    //缩放动画的时间
    private static final int SCALE_DURING = 150;

    private Bitmap mUnLaudBitmap ;
    private Bitmap mLaudBitmap ;
    private Bitmap mShiningBitmap ;
    //画图标用的画笔
    private Paint mBitMapPaint;
    //是否点赞状态
    private boolean mIsLaud = false;
    //灰色图片尺寸
    private float mUnLaudWidth;
    private float mUnLaudHeight;

    //亮色点赞图片吃餐
    private float mLaudWidth;
    private float mLaudHeight;

    //shining图片尺寸
    private float mShiningWidth;
    private float mShiningHeight;

    //描述大拇指位置的point
    private TuvPoint mLaudPoint;
    //描述shining位置的point
    private TuvPoint mShiningPoint;
    //点赞时圆圈动画的中心点
    private TuvPoint mCirclePoint;
    //圆圈半径
    private float mCircleRadius;
    //画圆用的画笔
    private Paint mCirclePaint;
    private float mRadiusMin ;
    private float mRadiusMax ;
    

    public ThumbView(Context context){
        this(context,null);
    }

    public ThumbView(Context context, AttributeSet attributeSet){
        this(context,attributeSet,0);
    }

    public ThumbView(Context context,AttributeSet attributeSet,int defStyleAttr){
        super(context,attributeSet,defStyleAttr);
        init();
    }

    private void init(){
        //设置好要绘制的Bitmap
        mUnLaudBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_like_unselected);
        mLaudBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.ic_like_selected);
        mShiningBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.ic_like_selected_shining);

        //初始化画笔
        mBitMapPaint = new Paint();
        mBitMapPaint.setAntiAlias(true);
        mBitMapPaint.setStyle(Paint.Style.FILL);

        //初始化画圆用的画笔
        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setColor(Color.parseColor(DEFAULT_CIRCLE_COLOR));
        mCirclePaint.setStrokeWidth(DisplayUtil.getInstance().dip2px(getContext(),2f));

        initSizeInfo();
    }

    private void initSizeInfo(){
        mUnLaudWidth = mUnLaudBitmap.getWidth();
        mUnLaudHeight = mUnLaudBitmap.getWidth();

        mLaudWidth = mLaudBitmap.getWidth();
        mLaudHeight = mLaudBitmap.getHeight();

        mShiningWidth = mShiningBitmap.getWidth();
        mShiningHeight = mShiningBitmap.getHeight();

        mLaudPoint = new TuvPoint();
        mLaudPoint.x = getPaddingLeft();
        mLaudPoint.y = getPaddingTop() + mShiningHeight/2;

        mShiningPoint = new TuvPoint();
        mShiningPoint.x = getPaddingLeft();
        mShiningPoint.y = getPaddingTop();

        mCirclePoint = new TuvPoint();
        mCirclePoint.x = mLaudPoint.x + mLaudWidth/2;
        mCirclePoint.y = mLaudPoint.y + mLaudHeight/2;

        mRadiusMax = Math.max(mCirclePoint.x - getPaddingLeft(),mCirclePoint.y - getPaddingTop());
        mRadiusMin = DisplayUtil.getInstance().dip2px(getContext(),8);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(DisplayUtil.getInstance().getDefaultSize(widthMeasureSpec, getContentWidth() + getPaddingLeft() + getPaddingRight()),
                DisplayUtil.getInstance().getDefaultSize(widthMeasureSpec,getContentHeight() + getPaddingTop() + getPaddingBottom()));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        measureWidget();
    }

    private float mWidgetWidth;
    private float mWidgetHeight;

    private void measureWidget(){
        mWidgetWidth = getMeasuredWidth();
        mWidgetHeight = getMeasuredHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(mIsLaud){
            canvas.drawCircle(mCirclePoint.x,mCirclePoint.y,mCircleRadius,mCirclePaint);
            canvas.drawBitmap(mShiningBitmap,mShiningPoint.x,mShiningPoint.y,mBitMapPaint);
            canvas.drawBitmap(mLaudBitmap,mLaudPoint.x,mLaudPoint.y,mBitMapPaint);
        }else{
            canvas.drawBitmap(mUnLaudBitmap,mLaudPoint.x,mLaudPoint.y,mBitMapPaint);
        }
    }

    private int getContentWidth() {
        float minLeft = Math.min(mShiningPoint.x, mLaudPoint.x);
        float maxRight = Math.max(mShiningPoint.x + mShiningWidth, mLaudPoint.x + mLaudWidth);
        return (int) (maxRight - minLeft);
    }

    private int getContentHeight(){
        float minTop = Math.min(mShiningPoint.y,mLaudPoint.y);
        float maxHeight = Math.max(mShiningPoint.y + mShiningHeight,mLaudPoint.y + mLaudHeight);
        float minHeight = Math.min(mShiningPoint.y + mShiningHeight,mLaudPoint.y + mLaudHeight);
        float bottom = maxHeight + minHeight/2;
        return (int) (bottom - minTop);
    }

    public TuvPoint getCirclePoint(){
        return mCirclePoint;
    }

    //todo:测试点赞图片绘制用，稍后删除
    public void setLaud(boolean isLaud){
        this.mIsLaud = isLaud;
        invalidate();
    }

    private AnimatorSet mThumbUpAnimSet;
    
    public void startAnim(){
        mThumbUpAnimSet = new AnimatorSet();
        ObjectAnimator circleAnim = ObjectAnimator.ofFloat(this,"circleScale",mRadiusMin,mRadiusMax);
        circleAnim.setDuration(RADIUS_DURING);
        
        ObjectAnimator thumbUpScale = ObjectAnimator.ofFloat(this,"thumbUpScale",SCALE_MIN,SCALE_MAX);
        thumbUpScale.setDuration(SCALE_DURING);
        thumbUpScale.setInterpolator(new OvershootInterpolator());
        
        
        mThumbUpAnimSet.play(thumbUpScale).with(circleAnim);
        mThumbUpAnimSet.start();
    }


    public void setCircleScale(float radius){
        mCircleRadius = radius;
        float percent = (mCircleRadius - mRadiusMax)/(mRadiusMin - mRadiusMax);
        mCirclePaint.setAlpha((int) (255 * percent));
        postInvalidate();
    }

    public void setThumbUpScale(float scale){
        Matrix matrix = new Matrix();
        matrix.postScale(scale,scale);
        mLaudBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_like_selected);
        mLaudBitmap = Bitmap.createBitmap(mLaudBitmap,0,0,mLaudBitmap.getWidth(),mLaudBitmap.getHeight(),matrix,true);
        postInvalidate();
    }
}
