package com.jumbomob.invistoo.ui.component;

/*
 * Copyright (C) 2016 Sutachad Wichai
 * Copyright (C) 2016 layer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import com.jumbomob.invistoo.R;

/**
 * Modified by maiko.trindade<mt@ubby.io>
 * @since 4/12/17
 */
public class StepIndicator extends View {

    private static final int DEFAULT_STEP_RADIUS = 8;   //DP
    private static final int DEFAULT_STOKE_WIDTH = 1;  //DP
    private static final int DEFAULT_STEP_COUNT = 4;  //DP
    private static final int DEFAULT_BACKGROUND_COLOR = R.color.material_green_100;
    private static final int DEFAULT_STEP_COLOR = R.color.colorAccent;
    private static final int DEFAULT_CURRENT_STEP_COLOR = R.color.colorAccent;

    private int radius;
    private int strokeWidth;
    private int currentStepPosition;
    private int stepsCount = 1;
    private int backgroundColor;
    private int stepColor;
    private int currentColor;

    private int centerY;
    private int startX;
    private int endX;
    private int stepDistance;
    private float offset;
    private int offsetPixel;
    private int pagerScrollState;

    private Paint paint;
    private Paint pStoke;
    private OnClickListener onClickListener;
    private float[] hsvCurrent = new float[3];
    private float[] hsvBG = new float[3];
    private float[] hsvProgress = new float[3];

    private boolean clickable = true;
    private boolean withViewpager;
    private ViewPagerOnChangeListener viewPagerChangeListener;
    private boolean disablePageChange;

    public StepIndicator(Context context) {
        super(context);
        init(context, null);
    }

    public StepIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public StepIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public StepIndicator(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }


    public interface OnClickListener {
        void onClick(int position);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    private void init(Context context, AttributeSet attributeSet) {

        initAttributes(context, attributeSet);
        paint = new Paint();
        pStoke = new Paint();
        paint.setColor(stepColor);
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(radius * 80 / 100);
        pStoke.setColor(stepColor);
        pStoke.setStrokeWidth(strokeWidth);
        pStoke.setStyle(Paint.Style.STROKE);
        pStoke.setFlags(Paint.ANTI_ALIAS_FLAG);
        setMinimumHeight(radius * 3);
        Color.colorToHSV(currentColor, hsvCurrent);
        Color.colorToHSV(backgroundColor, hsvBG);
        Color.colorToHSV(stepColor, hsvProgress);
        invalidate();
    }

    private void initAttributes(Context context, AttributeSet attributeSet) {
        TypedArray attr = context.obtainStyledAttributes(attributeSet, R.styleable.StepIndicator, 0, 0);
        if (attr == null) {
            return;
        }

        try {
            radius = (int) attr.getDimension(R.styleable.StepIndicator_step_Radius, dp2px(DEFAULT_STEP_RADIUS));
            strokeWidth = (int) attr.getDimension(R.styleable.StepIndicator_step_StrokeWidth, dp2px(DEFAULT_STOKE_WIDTH));
            stepsCount = attr.getInt(R.styleable.StepIndicator_step_StepCount, DEFAULT_STEP_COUNT);
            stepColor = attr.getColor(R.styleable.StepIndicator_step_StepColor, ContextCompat.getColor(context, DEFAULT_STEP_COLOR));
            currentColor = attr.getColor(R.styleable.StepIndicator_step_CurrentStepColor, ContextCompat.getColor(context, DEFAULT_CURRENT_STEP_COLOR));
            backgroundColor = attr.getColor(R.styleable.StepIndicator_step_BackgroundColor, ContextCompat.getColor(context, DEFAULT_BACKGROUND_COLOR));
        } finally {
            attr.recycle();
        }
    }

    @SuppressLint("NewApi")
    protected float dp2px(float dp) {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public int getStepsCount() {
        return stepsCount;
    }

    public void setStepsCount(int stepsCount) {
        this.stepsCount = stepsCount;
        invalidate();
    }

    public int getCurrentStepPosition() {
        return currentStepPosition;
    }

    public void setCurrentStepPosition(int currentStepPosition) {
        this.currentStepPosition = currentStepPosition;
        invalidate();
    }

    @Override
    public boolean isClickable() {
        return clickable;
    }

    @Override
    public void setClickable(boolean clickable) {
        this.clickable = clickable;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public void setupWithViewPager(@NonNull ViewPager viewPager) {
        final PagerAdapter adapter = viewPager.getAdapter();
        if (adapter == null) {
            throw new IllegalArgumentException("ViewPager does not have a PagerAdapter set");
        }
        if (viewPagerChangeListener == null) {
            viewPagerChangeListener = new ViewPagerOnChangeListener(this);
        }
        withViewpager = true;
        // First we'll add Steps.
        setStepsCount(adapter.getCount());

        // Now we'll add our page change listener to the ViewPager
        viewPager.addOnPageChangeListener(viewPagerChangeListener);

        // Now we'll add a selected listener to set ViewPager's currentStepPosition item
        setOnClickListener(new ViewPagerOnSelectedListener(viewPager));

        viewPager.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getActionMasked() == MotionEvent.ACTION_MOVE) {
                    ((ViewPager) v).addOnPageChangeListener(viewPagerChangeListener);
                    disablePageChange = false;
                }
                return false;
            }
        });

        // Make sure we reflect the currently set ViewPager item
        if (adapter.getCount() > 0) {
            final int curItem = viewPager.getCurrentItem();
            if (getCurrentStepPosition() != curItem) {
                setCurrentStepPosition(curItem);
                invalidate();
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (stepsCount <= 1) {
            setVisibility(GONE);
            return;
        }
        super.onDraw(canvas);
        int pointX = startX;

        /**draw Circle */
        for (int i = 0; i < stepsCount; i++) {
            if (i < currentStepPosition) {
                //draw previous step
                paint.setColor(stepColor);
                canvas.drawCircle(pointX, centerY, radius, paint);

                //draw transition
                if (i == currentStepPosition - 1 && offsetPixel < 0 && pagerScrollState == 1) {
                    pStoke.setAlpha(255);
                    pStoke.setStrokeWidth(strokeWidth - Math.round(strokeWidth * offset));
                    canvas.drawCircle(pointX, centerY, radius, pStoke);
                }
            } else if (i == currentStepPosition) {
                //draw current step
                if (offsetPixel == 0 || pagerScrollState == 0) {
                    //set stroke default
                    paint.setColor(currentColor);
                    pStoke.setStrokeWidth(Math.round(strokeWidth));
                    pStoke.setAlpha(255);
                } else if (offsetPixel < 0) {
                    pStoke.setStrokeWidth(Math.round(strokeWidth * offset));
                    pStoke.setAlpha(Math.round(offset * 255f));
                    paint.setColor(getColorToBG(offset));
                } else {
                    //set stroke transition
                    paint.setColor(getColorToProgress(offset));
                    pStoke.setStrokeWidth(strokeWidth - Math.round(strokeWidth * offset));
                    pStoke.setAlpha(255 - Math.round(offset * 255f));
                }
                canvas.drawCircle(pointX, centerY, radius, paint);
                canvas.drawCircle(pointX, centerY, radius, pStoke);
            } else {
                //draw next step
                paint.setColor(backgroundColor);
                canvas.drawCircle(pointX, centerY, radius, paint);

                //draw transition
                if (i == currentStepPosition + 1 && offsetPixel > 0 && pagerScrollState == 1) {
                    pStoke.setStrokeWidth(Math.round(strokeWidth * offset));
                    pStoke.setAlpha(Math.round(offset * 255f));
                    canvas.drawCircle(pointX, centerY, radius, pStoke);
                }
            }
            pointX = pointX + stepDistance;
        }

    }

    private int getColorToBG(float offset) {
        offset = Math.abs(offset);
        float[] hsv = new float[3];
        hsv[0] = hsvBG[0] + (hsvCurrent[0] - hsvBG[0]) * offset;
        hsv[1] = hsvBG[1] + (hsvCurrent[1] - hsvBG[1]) * offset;
        hsv[2] = hsvBG[2] + (hsvCurrent[2] - hsvBG[2]) * offset;
        return Color.HSVToColor(hsv);
    }

    private int getColorToProgress(float offset) {
        offset = Math.abs(offset);
        float[] hsv = new float[3];
        hsv[0] = hsvCurrent[0] + (hsvProgress[0] - hsvCurrent[0]) * offset;
        hsv[1] = hsvCurrent[1] + (hsvProgress[1] - hsvCurrent[1]) * offset;
        hsv[2] = hsvCurrent[2] + (hsvProgress[2] - hsvCurrent[2]) * offset;
        return Color.HSVToColor(hsv);
    }

    private void setOffset(float offset, int position) {
        this.offset = offset;
        offsetPixel = Math.round(stepDistance * offset);
        if (currentStepPosition > position) {
            offsetPixel = offsetPixel - stepDistance;
        } else {
            currentStepPosition = position;
        }
        invalidate();
    }

    private void setPagerScrollState(int pagerScrollState) {
        this.pagerScrollState = pagerScrollState;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!clickable)
            return super.onTouchEvent(event);
        int pointX = startX;
        int xTouch;
        int yTouch;
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                xTouch = (int) event.getX(0);
                yTouch = (int) event.getY(0);
                for (int i = 0; i < stepsCount; i++) {
                    if (Math.abs(xTouch - pointX) < radius + 5 && Math.abs(yTouch - centerY) < radius + 5) {
                        if (!withViewpager) {
                            setCurrentStepPosition(i);
                        }

                        if (onClickListener != null) {
                            onClickListener.onClick(i);
                        }
                    }
                    pointX = pointX + stepDistance;
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, radius * 3);
        centerY = getHeight() / 2;
        startX = radius * 2;
        endX = getWidth() - (radius * 2);
        stepDistance = (endX - startX) / (stepsCount - 1);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerY = getHeight() / 2;
        startX = radius * 2;
        endX = getWidth() - (radius * 2);
        stepDistance = (endX - startX) / (stepsCount - 1);
        invalidate();
    }

    public class ViewPagerOnChangeListener implements ViewPager.OnPageChangeListener {
        private final StepIndicator stepIndicator;

        ViewPagerOnChangeListener(StepIndicator stepIndicator) {
            this.stepIndicator = stepIndicator;
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (!disablePageChange) {
                stepIndicator.setOffset(positionOffset, position);
            }
        }

        @Override
        public void onPageSelected(int position) {
            if (!disablePageChange) {
                stepIndicator.setCurrentStepPosition(position);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            stepIndicator.setPagerScrollState(state);
        }

    }

    public class ViewPagerOnSelectedListener implements OnClickListener {
        private final ViewPager mViewPager;

        ViewPagerOnSelectedListener(ViewPager viewPager) {
            mViewPager = viewPager;
        }

        @Override
        public void onClick(int position) {
            disablePageChange = true;
            setCurrentStepPosition(position);
            mViewPager.setCurrentItem(position);
        }
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState ss = new SavedState(superState);
        ss.radius = this.radius;
        ss.strokeWidth = this.strokeWidth;
        ss.currentStepPosition = this.currentStepPosition;
        ss.stepsCount = this.stepsCount;
        ss.backgroundColor = this.backgroundColor;
        ss.stepColor = this.stepColor;
        ss.currentColor = this.currentColor;
        return ss;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }

        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());

        this.radius = ss.radius;
        this.strokeWidth = ss.strokeWidth;
        this.currentStepPosition = ss.currentStepPosition;
        this.stepsCount = ss.stepsCount;
        this.backgroundColor = ss.backgroundColor;
        this.stepColor = ss.stepColor;
        this.currentColor = ss.currentColor;
    }

    static class SavedState extends BaseSavedState {
        int radius;
        int strokeWidth;
        int currentStepPosition;
        int stepsCount;
        int backgroundColor;
        int stepColor;
        int currentColor;

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            radius = in.readInt();
            strokeWidth = in.readInt();
            currentStepPosition = in.readInt();
            stepsCount = in.readInt();
            backgroundColor = in.readInt();
            stepColor = in.readInt();
            currentColor = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(radius);
            dest.writeInt(strokeWidth);
            dest.writeInt(currentStepPosition);
            dest.writeInt(stepsCount);
            dest.writeInt(backgroundColor);
            dest.writeInt(stepColor);
            dest.writeInt(currentColor);
        }

        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }
}
