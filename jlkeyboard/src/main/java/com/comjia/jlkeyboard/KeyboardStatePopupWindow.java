package com.comjia.jlkeyboard;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * 透明PopupWindow
 */
public class KeyboardStatePopupWindow extends PopupWindow implements ViewTreeObserver.OnGlobalLayoutListener {


    private int maxHeight = 0;
    private boolean isSoftKeyboardOpened = false;
    private final Context mContext;
    private ISoftKeyboardStateListener mListener;

    public void setSoftKeyboardListener(ISoftKeyboardStateListener mListener) {
        this.mListener = mListener;
    }

    public KeyboardStatePopupWindow(Context context, View rootView) {
        super(context);
        mContext = context;
        View contentView = new View(context);
        setContentView(contentView);
        setWidth(0);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        ColorDrawable colorDrawable = new ColorDrawable();
        colorDrawable.setColor(Color.TRANSPARENT);
        setBackgroundDrawable(colorDrawable);
        //通过设置透明PopupWindow覆盖，可以动态获取软件盘高度
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setInputMethodMode(INPUT_METHOD_NEEDED);
        contentView.getViewTreeObserver().addOnGlobalLayoutListener(this);
        rootView.post(() -> showAtLocation(rootView, Gravity.NO_GRAVITY, 0, 0));

    }

    @Override
    public void onGlobalLayout() {
        //TODO:
        Rect mRect = new Rect();
        getContentView().getWindowVisibleDisplayFrame(mRect);
        if (mRect.bottom > maxHeight) {
            maxHeight = mRect.bottom;
        }
        int screenHeight = DensityUtil.getScreenHeight(mContext);
        //键盘的高度
        int keyboardHeight = maxHeight - mRect.bottom;
        boolean visible = keyboardHeight > screenHeight / 4;
        if (!isSoftKeyboardOpened && visible) {
            isSoftKeyboardOpened = true;
            if (mListener != null) {
                mListener.onOpened(keyboardHeight);
                KeyboardHelper.keyboardHeight = keyboardHeight;
            }
        }

        if (isSoftKeyboardOpened && !visible) {
            isSoftKeyboardOpened = false;
            if (mListener != null) {
                mListener.onClosed();
            }
        }
    }

    public void release() {
        View contentView = getContentView();
        if (contentView != null) {
            contentView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        }
    }
}
