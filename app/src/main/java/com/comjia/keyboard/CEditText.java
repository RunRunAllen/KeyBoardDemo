package com.comjia.keyboard;

import android.content.Context;
import android.graphics.Color;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.inputmethod.EditorInfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;

/**
 *
 */
public class CEditText extends AppCompatEditText {

    private final Context mContext;
    private final int mLeftRightPadding;
    private final int mTopBottomPadding;
    private final int mTopBottomMargin;
    private final int mLeftRightMargin;

    public CEditText(@NonNull Context context) {
        this(context, null);
    }

    public CEditText(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CEditText(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        mLeftRightPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, context.getResources().getDisplayMetrics());
        mTopBottomPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 9, context.getResources().getDisplayMetrics());
        mTopBottomMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, context.getResources().getDisplayMetrics());
        mLeftRightMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12, context.getResources().getDisplayMetrics());
        init();
    }

    private void init() {
        setImeOptions(EditorInfo.IME_ACTION_SEND);
        resetInputType();
        setPadding(mLeftRightPadding, mTopBottomPadding, mLeftRightPadding, mTopBottomPadding);
        setGravity(Gravity.CENTER);
        setHintTextColor(Color.parseColor("#C4CBCC"));
        setTextColor(Color.parseColor("#031A1F"));
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f);
        setHorizontallyScrolling(false);
        setBackgroundResource(R.drawable.shape_common_edittext_bg);
    }

    public void resetInputType() {
        setInputType(InputType.TYPE_CLASS_TEXT);
    }
}
