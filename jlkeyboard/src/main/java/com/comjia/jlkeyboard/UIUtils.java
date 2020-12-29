package com.comjia.jlkeyboard;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.inputmethod.InputMethodManager;

/**
 * 键盘焦点工具类
 */
public class UIUtils {

    /**
     * 使控件获取焦点
     *
     * @param view
     */
    public static void requestFocus(View view) {
        if (view != null) {
            view.setFocusable(true);
            view.setFocusableInTouchMode(true);
            view.requestFocus();
        }
    }

    /**
     * 使控件失去焦点
     *
     * @param view
     */
    public static void loseFocus(View view) {
        if (view != null) {
            ViewParent parent = view.getParent();
            if (parent instanceof ViewGroup) {
                ((ViewGroup) parent).setFocusable(true);
                ((ViewGroup) parent).setFocusableInTouchMode(true);
                ((ViewGroup) parent).requestFocus();
            }
        }
    }

    /**
     * 隐藏键盘
     */
    public static void hideSoftInput(Context context, View view) {
        if (context == null || view == null) return;
        InputMethodManager service = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        service.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
    }

    /**
     * 显示键盘
     */
    public static void showSoftInput(Context context, View view) {
        if (context == null || view == null) return;
        InputMethodManager service = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        service.showSoftInput(view, 0);

    }
}
