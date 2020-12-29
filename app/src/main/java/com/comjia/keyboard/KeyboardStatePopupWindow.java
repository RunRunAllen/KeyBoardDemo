package com.comjia.keyboard;

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
public class KeyboardStatePopupWindow extends PopupWindow implements ViewTreeObserver.OnGlobalLayoutListener, ISoftKeyboardStateListener {


    private final Rect mRect;
    private int maxHeight = 0;
    private boolean isSoftKeyboardOpened = false;
    private Context mContext;

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
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(this);
        showAtLocation(rootView, Gravity.NO_GRAVITY, 0, 0);
        mRect = new Rect();

    }

    @Override
    public void onGlobalLayout() {
        getContentView().getWindowVisibleDisplayFrame(mRect);
        if (mRect.bottom > maxHeight) {
            maxHeight = mRect.bottom;
        }
        int screenHeight = DensityUtil.getScreenHeight(mContext);
        int keyboardHeight = maxHeight - mRect.bottom;
        boolean visible = keyboardHeight > screenHeight / 4;
        if (!isSoftKeyboardOpened && visible) {
            isSoftKeyboardOpened =true;
        }

//        contentView.getWindowVisibleDisplayFrame(rect)
//        if (rect.bottom > maxHeight) {
//            maxHeight = rect.bottom
//        }
//        val screenHeight: Int = DensityUtil.getScreenHeight(context)
//        //键盘的高度
//        val keyboardHeight = maxHeight - rect.bottom
//        val visible = keyboardHeight > screenHeight / 4
//        if (!isSoftKeyboardOpened && visible) {
//            isSoftKeyboardOpened = true
//            onKeyboardStateListener?.onOpened(keyboardHeight)
//            KeyboardHelper.keyboardHeight = keyboardHeight
//        } else if (isSoftKeyboardOpened && !visible) {
//            isSoftKeyboardOpened = false
//            onKeyboardStateListener?.onClosed()
//        }

    }

    @Override
    public void onSoftKeyboardOpened(int keyboardHeight) {

    }

    @Override
    public void onSoftKeyboardClosed() {

    }
}
