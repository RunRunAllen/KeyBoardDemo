package com.comjia.keyboard;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

import androidx.recyclerview.widget.RecyclerView;

/**
 *
 */
public class KeyboardHelper {

    private ViewGroup rootLayout;
    private RecyclerView recyclerView;
    private IPanel morePanel;
    private IInputPanel inputPanel;
    private KeyboardStatePopupWindow keyboardStatePopupWindow;
    private boolean scrollBodyLayout = false;
    private ISoftKeyboardStateListener onKeyboardStateListener;

    public static int keyboardHeight = 0;
    public static int inputPanelHeight = 0;
    public static int morePanelHeight = 0;

    public KeyboardHelper(Builder builder) {
        this.rootLayout = builder.rootLayout;
        this.recyclerView = builder.recyclerView;
        this.morePanel = builder.morePanel;
        this.inputPanel = builder.inputPanel;
        this.onKeyboardStateListener = builder.onKeyboardStateListener;
        this.scrollBodyLayout = builder.scrollBodyLayout;
    }

    public static class Builder {
        private Context mContext;
        private ViewGroup rootLayout;
        private RecyclerView recyclerView;
        private IPanel morePanel;
        private IInputPanel inputPanel;
        private boolean scrollBodyLayout = false;
        private ISoftKeyboardStateListener onKeyboardStateListener;

        public Builder(Context mContext) {
            this.mContext = mContext;
        }

        public Builder setSoftKeyboardListener(ISoftKeyboardStateListener mListener) {
            this.onKeyboardStateListener = mListener;
            return this;
        }

        public Builder setKeyboardHeight(int height) {
            keyboardHeight = height;
            if (inputPanelHeight == 0) {
                inputPanelHeight = keyboardHeight;
            }
            return this;
        }

        public Builder setScrollBodyLayout(boolean scrollBodyLayout) {
            this.scrollBodyLayout = scrollBodyLayout;
            return this;
        }

        public Builder bindRootLayout(ViewGroup rootLayout) {
            this.rootLayout = rootLayout;
            KeyboardStatePopupWindow keyboardStatePopupWindow = new KeyboardStatePopupWindow(mContext, rootLayout);
            keyboardStatePopupWindow.setSoftKeyboardListener(new ISoftKeyboardStateListener() {
                //TODO:是否要添加keyboard 监听
                @Override
                public void onOpened(int height) {
                    keyboardHeight = height;
                    if (inputPanel != null) {
                        inputPanel.onSoftKeyboardOpened();
                        if (onKeyboardStateListener != null) {
                            onKeyboardStateListener.onOpened(keyboardHeight);
                        }
                        inputPanelHeight = inputPanel.getPanelHeight();
                    }
                    if (morePanel != null) {
                        morePanelHeight = morePanel.getPanelHeight();
                    }
                }

                @Override
                public void onClosed() {
                    if (inputPanel != null) {
                        inputPanel.onSoftKeyboardClosed();
                        if (onKeyboardStateListener != null) {
                            onKeyboardStateListener.onClosed();
                        }
                    }
                }
            });
            return this;
        }

        public Builder bindRecyclerView(RecyclerView recyclerView) {
            this.recyclerView = recyclerView;
            return this;
        }

        public Builder bindMorePanel(IPanel morePanel) {
            this.morePanel = morePanel;
            return this;
        }

        public Builder bindInputPanel(IInputPanel inputPanel) {
            this.inputPanel = inputPanel;
            inputPanelHeight = inputPanel.getPanelHeight();
            inputPanel.setOnInputStateChangedListener(new OnInputPanelStateChangedListener() {

                @Override
                public void onShowInputMethodPanel() {
                    if (morePanel instanceof ViewGroup) {
                        ((ViewGroup) morePanel).setVisibility(View.GONE);
                    }
                }

                @Override
                public void onShowMorePanel() {
                    if (morePanel instanceof ViewGroup) {
                        ((ViewGroup) morePanel).setVisibility(View.VISIBLE);
                    }
                }
            });
            inputPanel.setOnLayoutAnimatorHandleListener(new IAnimatorHandleListener() {
                @Override
                public void handlePanelMoveAnimator(PanelType panelType, PanelType lastPanelType, Float fromValue, Float toValue) {
                    initAnimator(panelType, fromValue, toValue);
                }

                @SuppressLint("ObjectAnimatorBinding")
                private void initAnimator(PanelType panelType, Float fromValue, Float toValue) {
                    ObjectAnimator recyclerViewTranslationYAnimator = ObjectAnimator.ofFloat(recyclerView
                            , "translationY", fromValue, toValue);
                    ObjectAnimator inputPanelTranslationYAnimator = ObjectAnimator.ofFloat(inputPanel
                            , "translationY", fromValue, toValue);
                    ObjectAnimator panelTranslationYAnimator = null;
                    if (panelType == PanelType.INPUT_MOTHOD) {
                        if (morePanel != null) {
                            morePanel.reset();
                        }
                    }
                    if (panelType == PanelType.MORE) {
                        panelTranslationYAnimator = ObjectAnimator.ofFloat(morePanel, "translationY", fromValue, toValue);
                    }

                    AnimatorSet animatorSet = new AnimatorSet();
                    animatorSet.setDuration(250);
                    animatorSet.setInterpolator(new DecelerateInterpolator());
                    if (panelTranslationYAnimator == null) {
                        if (scrollBodyLayout) {
                            animatorSet.play(inputPanelTranslationYAnimator).with(recyclerViewTranslationYAnimator);
                        } else {
                            animatorSet.play(inputPanelTranslationYAnimator);
                        }
                    } else {
                        if (scrollBodyLayout) {
                            animatorSet.play(inputPanelTranslationYAnimator).with(recyclerViewTranslationYAnimator).with(panelTranslationYAnimator);
                        } else {
                            animatorSet.play(inputPanelTranslationYAnimator).with(panelTranslationYAnimator);
                        }
                    }
                    animatorSet.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            if (recyclerView != null && recyclerView instanceof ViewGroup) {
                                recyclerView.requestLayout();
                            }
                            if (morePanel != null && morePanel instanceof ViewGroup) {
                                ViewGroup morePanel = (ViewGroup) Builder.this.morePanel;
                                morePanel.requestLayout();
                            }
                        }
                    });
                    animatorSet.start();
                }
            });
            return this;
        }

        public KeyboardHelper create() {
            return new KeyboardHelper(this);
        }
    }


    public void reset() {
        if (inputPanel != null) {
            inputPanel.reset();
        }
        if (morePanel != null) {
            morePanel.reset();
        }
    }

    public void release() {
        reset();
        inputPanel = null;
        morePanel = null;
        if (keyboardStatePopupWindow != null) {
            keyboardStatePopupWindow.release();
            keyboardStatePopupWindow.dismiss();
            keyboardStatePopupWindow = null;
        }
    }
}
