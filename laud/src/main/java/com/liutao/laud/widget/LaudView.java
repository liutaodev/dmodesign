package com.liutao.laud.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.liutao.laud.R;
import com.liutao.laud.utils.DisplayUtil;

import androidx.annotation.Nullable;

public class LaudView extends LinearLayout {

    private static final float DEFAULT_DRAWABLE_PADDING = 4f;
    //字体大小
    private float mTextSize;
    //文字和图片的间距
    private float mDrawablePadding;
    //是否点赞状态
    private boolean mIsLaud = false;
    //显示点赞数量的View
    private CountView mCountView;
    //显示大拇指的图片
    private ThumbView mThumbView;
    //顶部margin
    private int mTopmargin;
    //点赞数
    private int mLaudCount;

    public LaudView(Context context) {
        this(context, null);
    }

    public LaudView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LaudView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LaudView);
        mTextSize = typedArray.getDimension(R.styleable.LaudView_lv_text_size, DisplayUtil.getInstance().sp2px(context, CountView.DEFAULT_TEXT_SIZE));
        mDrawablePadding = typedArray.getDimension(R.styleable.LaudView_lv_drawable_padding, DisplayUtil.getInstance().dip2px(context, DEFAULT_DRAWABLE_PADDING));
        mLaudCount = typedArray.getInteger(R.styleable.LaudView_lv_laud_count,0);
        typedArray.recycle();
        init();
    }

    private void init() {
        removeAllViews();
        setOrientation(LinearLayout.HORIZONTAL);
        addThumbView();
        addCountView();

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mIsLaud = !mIsLaud;
                if(mIsLaud){
                    mCountView.setLaudCount(mLaudCount + 1);
                }else{
                    mCountView.setLaudCount(mLaudCount);
                }
                mThumbView.setLaud(mIsLaud);
                mThumbView.startAnim();
            }
        });
    }

    private void addThumbView() {
        mThumbView = new ThumbView(getContext());
        TuvPoint circlePoint = mThumbView.getCirclePoint();
        mTopmargin = (int) (circlePoint.y - mTextSize / 2);
        addView(mThumbView, getThumbParams());
    }

    private LayoutParams getThumbParams() {
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (mTopmargin < 0) {
            params.topMargin = -mTopmargin;
        }
        params.leftMargin = getPaddingLeft();
        params.bottomMargin = getPaddingBottom();
        params.topMargin += getPaddingTop();
        return params;
    }

    private void addCountView() {
        mCountView = new CountView(getContext());
        mCountView.setTextSize(mTextSize);
        mCountView.setLaudCount(mLaudCount);
        addView(mCountView, getCountParams());
    }

    private LayoutParams getCountParams() {
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (mTopmargin > 0) {
            params.topMargin = mTopmargin;
        }
        params.topMargin += getPaddingTop();
        params.leftMargin = (int) mDrawablePadding;
        params.bottomMargin = getPaddingBottom();
        params.rightMargin = getPaddingRight();
        return params;
    }

    public void setLaudCount(int count){
        this.mLaudCount = count;
        mCountView.setLaudCount(mLaudCount);
    }
}
