package com.hardware.view;

/**
 * Created by Administrator on 2016/4/9.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hardware.R;


public class RatingBar extends LinearLayout {

    private int mStartsPadding = 8;

    private Drawable mDisableStar;
    private Drawable mEmptyStar;
    private Drawable mHalfStar;
    private Drawable mFullStar;

    private int mNumberStarts;

    private float mRating=0;

    private boolean mIsIndicator;
    private boolean mEnable;

    private OnRatingBarChangeListener mOnRatingBarChangeListener;
    private OnDisableClickedlListener mOnDisableClickedListener;

    public interface OnRatingBarChangeListener{
        void onRatingChanged(RatingBar ratingBar,float rating,boolean byUser);
    }

    public interface OnDisableClickedlListener{
        void onDisableChanged();
    }

    public RatingBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RatingBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RatingBar, defStyle, 0);

        mDisableStar=a.getDrawable(R.styleable.RatingBar_disable_star);
        mEmptyStar=a.getDrawable(R.styleable.RatingBar_empty_star);
        mHalfStar=a.getDrawable(R.styleable.RatingBar_half_star);
        mFullStar=a.getDrawable(R.styleable.RatingBar_full_star);

        mNumberStarts=a.getInt(R.styleable.RatingBar_numStars, 1);

        mRating=a.getFloat(R.styleable.RatingBar_rating, 0);

        mIsIndicator=a.getBoolean(R.styleable.RatingBar_isIndicator, true);
        mStartsPadding = a.getDimensionPixelSize(R.styleable.RatingBar_startsPadding, mStartsPadding);
        mEnable=a.getBoolean(R.styleable.RatingBar_enable, true);

        a.recycle();

        inflateItems(context);

        setRating(mRating);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int height=mFullStar.getIntrinsicHeight();

        int width=mNumberStarts*mFullStar.getIntrinsicWidth()+mStartsPadding*(mNumberStarts+1);
        for(int i=0;i<mNumberStarts;i++){
            View child=getChildAt(i);
            child.measure(height, mFullStar.getIntrinsicWidth());
        }

        setMeasuredDimension(width, height);
    }

    private void inflateItems(Context context){

        setOrientation(LinearLayout.HORIZONTAL);

        setPadding(mStartsPadding, 0, mStartsPadding, 0);

        for(int i=0;i<mNumberStarts;i++){
            ImageView child=new ImageView(context);
            child.setOnClickListener(mOnStartsClickListener);

            child.setImageDrawable(mEmptyStar);
            LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
            if(i!=mNumberStarts-1){
                lp.setMarginEnd(mStartsPadding);
            }
            addView(child, lp);
        }
    }

    public void setRating(float rating){
        setRating(rating,false);
    }

    public void setRating(float rating,boolean byUser){
        float realRating=Math.max(0, Math.min(rating, mNumberStarts));
        float preRating=mRating;
        mRating=realRating;
        if(mOnRatingBarChangeListener!=null&&preRating!=mRating){
            mOnRatingBarChangeListener.onRatingChanged(this, mRating,byUser);
        }
        refreshItems();
    }

    public float getRating(){
        return mRating;
    }

    public void setOnRatingBarChangeListener(OnRatingBarChangeListener listener){
        mOnRatingBarChangeListener=listener;
    }

    public void setOnDisableClickedListener(OnDisableClickedlListener listener){
        mOnDisableClickedListener=listener;
    }

    public void setEnable(boolean enable){
        mEnable=enable;
        refreshItems();
    }

    public void setIsIndicator(boolean isIndicator){
        mIsIndicator=isIndicator;
    }

    private void refreshItems(){
        int fullStartIndex=mRating>0?((int) mRating-1):-1;
        //小数点有余数，显示半星
        int halfStartsIndex=-1;
        if(mRating-(int) mRating>0){
            halfStartsIndex=fullStartIndex+1;
        }

        for(int i=0;i<mNumberStarts;i++){
            ImageView child=(ImageView)getChildAt(i);
            if(!mEnable){
                child.setImageDrawable(mDisableStar);
            }else{
                if(i<=fullStartIndex){
                    child.setImageDrawable(mFullStar);
                }else if(i>fullStartIndex){
                    child.setImageDrawable(mEmptyStar);
                }
                if(i==halfStartsIndex){
                    child.setImageDrawable(mHalfStar);
                }
            }
        }
    }


    private OnClickListener mOnStartsClickListener=new OnClickListener(){
        @Override
        public void onClick(View v) {
            if(!mIsIndicator&&mEnable){
                int index=getChildIndex(v);
                setRating(index+1,true);
            }else if(mOnDisableClickedListener!=null){
                mOnDisableClickedListener.onDisableChanged();
            }
        }
    };

    private int getChildIndex(View v){
        int index=0;
        for(int i=0;i<getChildCount();i++){
            if(getChildAt(i)==v){
                index=i;
                break;
            }
        }
        return index;
    }
}
