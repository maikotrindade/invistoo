package com.jumbomob.invistoo.ui.component;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.Button;

import com.jumbomob.invistoo.R;
import com.jumbomob.invistoo.util.InvistooUtil;

public class InvistooButton extends Button {

    public final static int WHITE_BUTTON = 1;
    public final static int GREY_BUTTON = 2;
    public final static int RED_BUTTON = 3;
    public final static int WHITE_BORDERED_BUTTON = 4;

    private CharSequence originalText = "";
    boolean hasSpacing = true;
    private Context context;

    public InvistooButton(Context context) {
        super(context);
        this.context = context;
    }

    public InvistooButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(attrs);
    }

    public InvistooButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public InvistooButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.InvistooButton);
        try {
            originalText = super.getText();
            this.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);

            boolean isUpperCase = typedArray.getBoolean(R.styleable.InvistooButton_isUpperCase, false);
            if (isUpperCase) {
                originalText = originalText.toString().toUpperCase();
            }

            hasSpacing = typedArray.getBoolean(R.styleable.InvistooButton_hasSpacing, true);
            if (hasSpacing) {
                applySpacing();
            }

        } finally {
            typedArray.recycle();
        }
    }

    public void setText(CharSequence text, boolean hasStyle) {
        originalText = text;
        if (hasSpacing && hasStyle) {
            originalText = originalText.toString().toUpperCase();
            applySpacing();
        } else {
            this.setTypeface(Typeface.SANS_SERIF, Typeface.NORMAL);
        }
    }

    @Override
    public CharSequence getText() {
        return originalText;
    }

    private void applySpacing() {
        if (this == null || this.originalText == null) return;
        super.setText(InvistooUtil.getSpacedText(originalText).toString(), BufferType.SPANNABLE);
        super.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
    }

}
