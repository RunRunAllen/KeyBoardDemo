package com.comjia.keyboard;

/**
 *
 */
interface OnInputPanelStateChangedListener {
    /**
     * 显示语音面板
     */
    void onShowVoicePanel();

    /**
     * 显示软键盘面板
     */
    void onShowInputMethodPanel();

    /**
     * 显示表情面板
     */
    void onShowExpressionPanel();

    /**
     * 显示更多面板
     */
    void onShowMorePanel();
}
