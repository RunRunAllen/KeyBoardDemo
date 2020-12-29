package com.comjia.keyboard;

import android.content.Context;

/**
 *
 */
public class DensityUtil {


    /**
     * 获取屏幕高度
     *
     * @return
     */
    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

}
