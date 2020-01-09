package com.liutao.laud.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import com.liutao.laud.R;
import com.liutao.laud.utils.DisplayUtil;

import androidx.annotation.Nullable;

public class LaudDemo extends View implements View.OnClickListener {

    private Context mContext;
    Path path = new Path();
    //画图标用的画笔
    private Paint mBitMapPaint;
    //画线用的画笔
    private Paint mLinePaint;
    //画文字用的画笔
    private Paint mTextPaint;
    //边框线宽
    private float mStrokeWidth;
    //点赞数量
    private int mLaudCount;
    //圆的半径
    private float mRadius;
    //圆心X坐标
    private float mCenterX;
    //圆心的Y坐标
    private float mCenterY;
    //控件宽度
    private int mWidgetWidth;
    //控件高度
    private int mWidgetHeight;
    //文字方框
    private Rect mTextRect;
    //文字高度
    private float mTextHeight;
    //文字宽度
    private float mTextWidth;


    public LaudDemo(Context context) {
        this(context,null);
    }

    public LaudDemo(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LaudDemo(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray =context.obtainStyledAttributes(attrs,R.styleable.LaudDemo);
        mStrokeWidth = typedArray.getDimension(R.styleable.LaudDemo_ld_strokewidth,10f);
        mLaudCount = typedArray.getInteger(R.styleable.LaudDemo_ld_count,0);
        init(context);
    }
    
    private void init(Context context){
        mContext = context;
        mBitMapPaint = new Paint();
        mBitMapPaint.setStyle(Paint.Style.FILL);
        mLinePaint = new Paint();
        mLinePaint.setStyle(Paint.Style.FILL);
        mTextPaint = new Paint();
        mTextPaint.setStyle(Paint.Style.FILL);
        setOnClickListener(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        measureWidget();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBitmap(canvas);
        drawText(canvas);
        drawTextRect(canvas);
        drawMarkLine(canvas);
    }


    private void measureWidget(){
        mCenterX = getMeasuredWidth()/2;
        mCenterY = getMeasuredHeight()/2;
        mWidgetWidth = getMeasuredWidth();
        mWidgetHeight = getMeasuredHeight();
    }

    private void drawBitmap(Canvas canvas){
        Bitmap unLaudBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.ic_like_unselected);
        Bitmap laudBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.ic_like_selected);
        Bitmap shiningBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.ic_like_selected_shining);
        if(mIsLaud){
            int laudBitmapHeight = laudBitmap.getHeight();
            int laudBitmapWidth = laudBitmap.getWidth();
            int bitLeft = mWidgetWidth/2 - laudBitmapWidth;
            int bitTop = mWidgetHeight/2 - laudBitmapHeight/2;
            int bitRight = mWidgetWidth/2;
            int bitBottom = mWidgetHeight/2+laudBitmapHeight/2;
            canvas.drawBitmap(laudBitmap,bitLeft,bitTop,mBitMapPaint);

            int shiningBitmapHeight = shiningBitmap.getHeight();
            int shiningBitmapWidth = shiningBitmap.getWidth();
            int shineLeft = mWidgetWidth/2 - (shiningBitmapWidth/2 + laudBitmapWidth/2);
            int shineTop = mWidgetHeight/2 - (shiningBitmapHeight/2 + laudBitmapHeight/2);
            int shineRight = mWidgetHeight/2 - (laudBitmapWidth/2 - shiningBitmapWidth/2);
            int shineBottom = mWidgetHeight/2 - (laudBitmapHeight/2 - shiningBitmapHeight/2);
            canvas.drawBitmap(shiningBitmap,shineLeft,shineTop,mBitMapPaint);

        }else{
            int unLaudBitmapHeight = unLaudBitmap.getHeight();
            int unLaudBitmapWidth = unLaudBitmap.getWidth();
            int bitLeft = mWidgetWidth/2-unLaudBitmapWidth;
            int bitTop = mWidgetHeight/2-unLaudBitmapHeight/2;
            int bitRight = mWidgetWidth/2;
            int bitBottom = mWidgetHeight/2+unLaudBitmapHeight/2;
            canvas.drawBitmap(unLaudBitmap,bitLeft,bitTop,mBitMapPaint);
        }
    }

    private void drawText(Canvas canvas){
        String text = String.valueOf(mLaudCount);
        mTextPaint.setColor(getResources().getColor(R.color.black));
        mTextPaint.setTypeface(Typeface.DEFAULT_BOLD);
        mTextPaint.setTextSize(DisplayUtil.getInstance().dip2px(mContext,20));

        //获取文字显示范围(矩形)
        mTextRect = new Rect();
        mTextPaint.getTextBounds(text,0,text.length(),mTextRect);
        mTextHeight = mTextRect.bottom - mTextRect.top;
        mTextWidth = mTextRect.right - mTextRect.left;
        //将文字绘制在bitmap右侧、竖直方向的中心位置
        canvas.drawText(text,mWidgetWidth/2,mWidgetHeight/2+mTextHeight/2,mTextPaint);
    }

    /**
     * 绘制文字的方框
     * @param canvas
     */
    private void drawTextRect(Canvas canvas){
        mLinePaint.setColor(getResources().getColor(R.color.deepskyblue));
        mLinePaint.setStyle(Paint.Style.STROKE);
        mTextRect.left += mWidgetWidth/2;
        mTextRect.top += mWidgetHeight/2+mTextHeight/2;
        mTextRect.right += mWidgetWidth/2;
        mTextRect.bottom += mWidgetHeight/2+mTextHeight/2;
        canvas.drawRect(mTextRect,mLinePaint);
    }

    /**
     * 绘制文字的5条线:top、ascent、baseline、descent、bottom
     */
    private void drawMarkLine(Canvas canvas){
        //横向中线 蓝色
        mLinePaint.setColor(getResources().getColor(R.color.deepskyblue));
        canvas.drawLine(0,mWidgetHeight/2,mWidgetWidth,mWidgetHeight/2,mLinePaint);
        //竖向中线 蓝色
        mLinePaint.setColor(getResources().getColor(R.color.deepskyblue));
        canvas.drawLine(mWidgetWidth/2,0,mWidgetWidth/2,mWidgetHeight,mLinePaint);

        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        float linebase = mWidgetHeight/2+mTextHeight/2;
        float linetop = linebase+fontMetrics.top;
        float lineascent = linebase+fontMetrics.ascent;
        float linedescent = linebase+fontMetrics.descent;
        float linebottom = linebase+fontMetrics.bottom;

        //绘制baseline线
        mLinePaint.setColor(Color.parseColor("#000000"));//黑色
        canvas.drawLine(0,linebase,mWidgetWidth,linebase,mLinePaint);
        //绘制top线
        mLinePaint.setColor(Color.parseColor("#0000ff"));//蓝色
        canvas.drawLine(0,linetop,mWidgetWidth,linetop,mLinePaint);
        //绘制ascent线
        mLinePaint.setColor(Color.parseColor("#008000"));//绿色
        canvas.drawLine(0,lineascent,mWidgetWidth,lineascent,mLinePaint);
        //绘制descent线
        mLinePaint.setColor(Color.parseColor("#ffa500"));//橙色
        canvas.drawLine(0,linedescent,mWidgetWidth,linedescent,mLinePaint);
        //绘制bottom线
        mLinePaint.setColor(Color.parseColor("#ff0000"));//红色
        canvas.drawLine(0,linebottom,mWidgetWidth,linebottom,mLinePaint);
    }

    private boolean mIsLaud = false;

    @Override
    public void onClick(View v) {
        mLaudCount++;
        mIsLaud = !mIsLaud;
        invalidate();
    }

    
}
