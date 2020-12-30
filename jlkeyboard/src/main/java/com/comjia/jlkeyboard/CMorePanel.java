package com.comjia.jlkeyboard;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

/**
 * 拓展面板
 */
public class CMorePanel extends ConstraintLayout implements IPanel, View.OnClickListener {
    private Context mContext;
    private View mMorePanelView;
    private TextView mBrower;
    private TextView mWaitting;

    public CMorePanel(Context context) {
        this(context, null);
    }

    public CMorePanel(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CMorePanel(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mMorePanelView = LayoutInflater.from(context).inflate(R.layout.xj_more_panel, this, true);
        initView();
    }

    private void initView() {
        mBrower = mMorePanelView.findViewById(R.id.tv_brower);
        mWaitting = mMorePanelView.findViewById(R.id.tv_wait);
        mBrower.setOnClickListener(this);
        mWaitting.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_brower) {
            //TODO:
            Toast.makeText(mContext, "哈哈哈哈哈111", Toast.LENGTH_LONG).show();
        }
        if (v.getId() == R.id.tv_wait) {
            Toast.makeText(mContext, "哈哈哈哈哈222", Toast.LENGTH_LONG).show();
        }
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
        //TODO:
        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 111, mContext.getResources().getDisplayMetrics());
//        Log.i("haha", "====keyHeight===" + MyApplication.keyboardHeight + "=====height===" + height);
        return KeyboardHelper.keyboardHeight - height;
    }
}
