package com.comjia.jlkeyboard;

/**
 * 事件埋点回调
 */
public interface IPanelEventCallBack {

    /**
     * 我的浏览
     */
    void browerMsg();

    /**
     * 虚位以待
     */
    void commingSoonMsg();

    /**
     * 添加更多面版
     */
    void addMoreMsg();

    /**
     * 发送消息
     */
    void sendContentMsg(String content);
}
