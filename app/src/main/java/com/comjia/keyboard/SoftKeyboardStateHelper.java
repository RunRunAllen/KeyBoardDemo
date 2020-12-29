package com.comjia.keyboard;

import android.graphics.Rect;
import android.media.MediaDrm;
import android.view.View;
import android.view.ViewTreeObserver;

import java.util.LinkedList;

/**
 * 测试高度用
 */
public class SoftKeyboardStateHelper implements ViewTreeObserver.OnGlobalLayoutListener {


    private LinkedList<ISoftKeyboardStateListener> mListeners = new LinkedList<>();
    private View activityRootView;
    private int lastSoftKeyboardHeightInPx;
    private boolean isSoftKeyboardOpened;
    private int maxHeight;
    private Rect mRect;

    public SoftKeyboardStateHelper(View activityRootView) {
        this.activityRootView = activityRootView;
        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(this);
        mRect = new Rect();
    }

    @Override
    public void onGlobalLayout() {
        if (activityRootView != null) {
            //获取当前窗口可视区域大小
            activityRootView.getWindowVisibleDisplayFrame(mRect);
        }
        //TODO:这里涉及到虚拟键盘/虚拟按键，可能需要适配
        if (mRect.bottom > maxHeight) {
            maxHeight = mRect.bottom;
        }
        int screenHeight = DensityUtil.getScreenHeight(MyApplication.getInstance());
        int heightDifference = maxHeight - mRect.bottom;
        boolean visible = heightDifference > screenHeight / 4;
        if (!isSoftKeyboardOpened && visible) {
            isSoftKeyboardOpened = true;
            notifyOnSoftKeyboardOpened(heightDifference);
        } else if (isSoftKeyboardOpened && !visible) {
            isSoftKeyboardOpened = false;
            notifyOnSoftKeyboardClosed();
        }
    }

    public boolean isSoftKeyboardOpened() {
        return isSoftKeyboardOpened;
    }

    public void setSoftKeyboardOpened(boolean softKeyboardOpened) {
        isSoftKeyboardOpened = softKeyboardOpened;
    }

    public int getLastSoftKeyboardHeightInPx() {
        return lastSoftKeyboardHeightInPx;
    }

    public void addSoftKeyboardStateListener(ISoftKeyboardStateListener listener) {
        if (mListeners == null) return;
        mListeners.add(listener);
    }

    public void removeSoftKeyboardStateListener(ISoftKeyboardStateListener listener) {
        if (mListeners == null) return;
        mListeners.remove(listener);
    }

    public void notifyOnSoftKeyboardOpened(int keyboardHeightInPx) {
        lastSoftKeyboardHeightInPx = keyboardHeightInPx;
        if (mListeners == null) return;
        for (ISoftKeyboardStateListener mListener : mListeners) {
            mListener.onSoftKeyboardOpened(keyboardHeightInPx);
        }
    }

    public void notifyOnSoftKeyboardClosed() {
        if (mListeners == null) return;
        for (ISoftKeyboardStateListener mListener : mListeners) {
            mListener.onSoftKeyboardClosed();
        }
    }

    public void release() {
        if (activityRootView == null) return;
        activityRootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }
}
