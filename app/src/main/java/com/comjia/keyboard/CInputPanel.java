package com.comjia.keyboard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

/**
 * 输入面板
 */
public class CInputPanel extends LinearLayout implements IInputPanel, View.OnClickListener, View.OnFocusChangeListener, View.OnTouchListener {
    private PanelType panelType = PanelType.NONE;
    private PanelType lastPanelType = panelType;
    private boolean isKeyboardOpened = false;
    private View mInputPanelView;
    private CEditText mEditText;
    private ImageView mAddMore;
    private Context mContext;

    public CInputPanel(Context context) {
        this(context, null);
    }

    public CInputPanel(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CInputPanel(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mInputPanelView = initView(context);
        initData();
        initListener();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initListener() {
        mEditText.setOnFocusChangeListener(this);
        mEditText.setOnTouchListener(this);
        mAddMore.setOnClickListener(this);
    }

    private View initView(Context context) {
        return LayoutInflater.from(context).inflate(R.layout.input_panel, this, true);
    }

    private void initData() {
        mEditText = mInputPanelView.findViewById(R.id.et_content);
        mAddMore = mInputPanelView.findViewById(R.id.btn_more);
        mEditText.setInputType(InputType.TYPE_NULL);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            mEditText.setHint("");
        }

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mEditText.setGravity(Gravity.CENTER_VERTICAL);
        UIUtils.requestFocus(mEditText);
        if (event.getAction() == KeyEvent.ACTION_UP) {
            if (!isKeyboardOpened) {
                //延迟100
                UIUtils.showSoftInput(mContext, mEditText);
            }
            mEditText.resetInputType();
            handleAnimator(PanelType.INPUT_MOTHOD);
            return true;
        }
        return false;
    }

    private void handleAnimator(PanelType panelType) {
        if (lastPanelType == PanelType.MORE) return;
        this.panelType = panelType;
//        if (panelType == PanelType.INPUT_MOTHOD) {
//
//        }


    }

    @Override
    public void onSoftKeyboardOpened() {

    }

    @Override
    public void onSoftKeyboardClosed() {

    }

    @Override
    public void setOnLayoutAnimatorHandleListener() {

    }

    @Override
    public void setOnInputStateChangedListener(OnInputPanelStateChangedListener listener) {

    }

    @Override
    public void reset() {

    }

    @Override
    public int getPanelHeight() {
        return 0;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_more) {

        }

    }
}
