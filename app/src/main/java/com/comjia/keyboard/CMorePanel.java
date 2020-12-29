package com.comjia.keyboard;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 拓展面板
 */
public class CMorePanel extends FrameLayout implements IPanel {
    private Context mContext;
    private View mMorePanelView;
    private ImageView mBrower;

    public CMorePanel(Context context) {
        this(context, null);
    }

    public CMorePanel(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CMorePanel(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mMorePanelView = LayoutInflater.from(context).inflate(R.layout.more_panel, this, true);
        initView();
    }

    private void initView() {
        mBrower = mMorePanelView.findViewById(R.id.iv_brower);
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = getPanelHeight();
        setLayoutParams(layoutParams);
    }

    @Override
    public void reset() {
        mMorePanelView.setVisibility(View.GONE);
    }

    @Override
    public int getPanelHeight() {
        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 111, mContext.getResources().getDisplayMetrics());
        Log.i("haha", "====keyHeight===" + MyApplication.keyboardHeight + "=====height===" + height);
        return MyApplication.keyboardHeight - height;
    }
}
