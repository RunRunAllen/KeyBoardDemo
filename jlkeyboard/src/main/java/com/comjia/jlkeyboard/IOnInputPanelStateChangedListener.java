package com.comjia.jlkeyboard;

/**
 *
 */
interface IOnInputPanelStateChangedListener {
    /**
     * 显示语音面板
     */
    default void onShowVoicePanel() {
    }

    /**
     * 显示软键盘面板
     */
    void onShowInputMethodPanel();

    /**
     * 显示表情面板
     */
    default void onShowExpressionPanel() {
    }

    /**
     * 显示更多面板
     */
    void onShowMorePanel();
}
