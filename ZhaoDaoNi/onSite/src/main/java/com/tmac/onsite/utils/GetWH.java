/**
 * 
 */
package com.tmac.onsite.utils;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

/**
 * @author tmac
 */
public class GetWH {

	private static final String TAG = GetWH.class.getSimpleName();
	
	// 获取屏幕的宽高方法一s
    public static int getWindowWidth(WindowManager wm) {
        WindowManager windowManager = wm;
        Display display = windowManager.getDefaultDisplay();
        int screenHeight = display.getHeight();
        int screenWidth = display.getWidth();
        
        Log.i(TAG, "screenHeight--->" + screenHeight);
        Log.i(TAG, "screenWidth--->" + screenWidth);
        return screenWidth;

    }

    // 获取屏幕的宽高方法二
    public static int getWindowWidthExcept(WindowManager wm) {
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int heightPixels = dm.heightPixels;
        int widthPixels = dm.widthPixels;
        Log.i(TAG, "heightPixels--->" + heightPixels);
        Log.i(TAG, "widthPixels--->" + widthPixels);
        return widthPixels;
    }
	
}
