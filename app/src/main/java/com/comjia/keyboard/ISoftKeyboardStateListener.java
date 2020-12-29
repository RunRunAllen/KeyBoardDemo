package com.comjia.keyboard;

/**
 *
 */
public interface ISoftKeyboardStateListener {

    /**
     * 打开
     */
    void onOpened(int keyboardHeight);

    /**
     * 关闭
     */
    void onClosed();

}
