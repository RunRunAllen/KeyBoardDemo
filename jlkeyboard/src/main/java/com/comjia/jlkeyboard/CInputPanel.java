package com.comjia.jlkeyboard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

/**
 * 输入面板
 */
public class CInputPanel extends LinearLayout implements IInputPanel, View.OnClickListener
        , View.OnTouchListener, TextView.OnEditorActionListener {
    private PanelType panelType = PanelType.NONE;
    private PanelType lastPanelType = PanelType.NONE;
    private boolean isKeyboardOpened = false;
    private final CEditText mEditText;
    private final ImageView mAddMore;
    private final TextView mHint;
    private final Context mContext;
    private IOnInputPanelStateChangedListener mOnInputPanelStateChangedListener;
    private IAnimatorHandleListener mOnLayoutAnimatorHandleListener;
    private boolean isActive = false;
    protected IPanelEventCallBack callBack;

    public CInputPanel(Context context) {
        this(context, null);
    }

    public CInputPanel(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CInputPanel(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        View mInputPanelView = initView(context);
        mEditText = mInputPanelView.findViewById(R.id.et_content);
        mAddMore = mInputPanelView.findViewById(R.id.btn_more);
        mHint = mInputPanelView.findViewById(R.id.tv_hint);
        initListener();
    }

    private View initView(Context context) {
        return LayoutInflater.from(context).inflate(R.layout.xj_input_panel, this, true);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initListener() {
        mEditText.setOnTouchListener(this);
        mEditText.setOnEditorActionListener(this);
        mAddMore.setOnClickListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == KeyEvent.ACTION_UP) {
            if (!isKeyboardOpened) {
                mHint.setVisibility(View.GONE);
                UIUtils.requestFocus(mEditText);
                UIUtils.showSoftInput(mContext, mEditText);
                mEditText.resetInputType();
                handleAnimator(PanelType.INPUT_MOTHOD);
                if (mOnInputPanelStateChangedListener != null) {
                    mOnInputPanelStateChangedListener.onShowInputMethodPanel();
                }
            }
        }
        return false;
    }


    private void handleAnimator(PanelType panelType) {
        if (lastPanelType == panelType) return;
        this.panelType = panelType;
        float fromValue = 0.0f;
        float toValue = 0.0f;
        isActive = true;
        if (panelType == PanelType.INPUT_MOTHOD) {
            if (lastPanelType == PanelType.MORE) {
                fromValue = -(float) KeyboardHelper.morePanelHeight;
                toValue = -(float) KeyboardHelper.inputPanelHeight;
            }
            if (lastPanelType == PanelType.NONE) {
                fromValue = 0.0f;
                toValue = -(float) KeyboardHelper.inputPanelHeight;
            }
        }

        if (panelType == PanelType.NONE) {
            if (lastPanelType == PanelType.INPUT_MOTHOD) {
                fromValue = -(float) KeyboardHelper.inputPanelHeight;
                toValue = 0.0f;
            }
            if (lastPanelType == PanelType.MORE) {
                fromValue = -(float) KeyboardHelper.morePanelHeight;
                toValue = 0.0f;
            }
        }

        if (panelType == PanelType.MORE) {
            if (lastPanelType == PanelType.INPUT_MOTHOD) {
                fromValue = -(float) KeyboardHelper.inputPanelHeight;
                toValue = -(float) KeyboardHelper.morePanelHeight;
            }
            if (lastPanelType == PanelType.NONE) {
                fromValue = 0.0f;
                toValue = -(float) KeyboardHelper.morePanelHeight;
            }
        }
        if (mOnLayoutAnimatorHandleListener != null) {
            mOnLayoutAnimatorHandleListener.handlePanelMoveAnimator(panelType, lastPanelType, fromValue, toValue);
        }
        lastPanelType = panelType;

    }

    @Override
    public void onSoftKeyboardOpened() {
        isKeyboardOpened = true;
        mEditText.resetInputType();
    }

    @Override
    public void onSoftKeyboardClosed() {
        isKeyboardOpened = false;
        if (lastPanelType == PanelType.INPUT_MOTHOD) {
            handleAnimator(PanelType.NONE);
        }
    }

    @Override
    public void setOnLayoutAnimatorHandleListener(IAnimatorHandleListener listener) {
        this.mOnLayoutAnimatorHandleListener = listener;
    }

    @Override
    public void setOnInputStateChangedListener(IOnInputPanelStateChangedListener listener) {
        this.mOnInputPanelStateChangedListener = listener;
    }

    @Override
    public void reset() {
        if (!isActive) {
            return;
        }
        if (mEditText.getText().length() <= 0) {
            mHint.setVisibility(View.VISIBLE);
        }
        UIUtils.loseFocus(mEditText);
        UIUtils.hideSoftInput(mContext, mEditText);
        postDelayed(() -> handleAnimator(PanelType.NONE), 100);
        isActive = false;
    }

    @Override
    public int getPanelHeight() {
        return KeyboardHelper.keyboardHeight;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_more) {
            if (callBack != null) {
                callBack.addMoreMsg();
            }
            if (lastPanelType == PanelType.MORE) {
                UIUtils.requestFocus(mEditText);
                UIUtils.showSoftInput(mContext, mEditText);
                handleAnimator(PanelType.INPUT_MOTHOD);
                mEditText.resetInputType();
            } else {
                UIUtils.loseFocus(mEditText);
                UIUtils.hideSoftInput(mContext, mEditText);
                handleAnimator(PanelType.MORE);
                if (mOnInputPanelStateChangedListener != null) {
                    mOnInputPanelStateChangedListener.onShowMorePanel();
                }
            }
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEND) {
            if (callBack != null) {
                callBack.sendContentMsg(v.getText().toString());
            }
            return true;
        }
        return false;
    }
}
