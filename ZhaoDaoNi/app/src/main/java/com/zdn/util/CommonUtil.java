package com.zdn.util;

import android.view.MotionEvent;
import android.view.View;

/**
 * Created by wanghp1 on 2015/9/17.
 */
public class CommonUtil {


    public static boolean inRangeOfView(View view, MotionEvent ev){
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];
        if(ev.getX() < x || ev.getX() > (x + view.getWidth()) || ev.getY() < y || ev.getY() > (y + view.getHeight())){
            return false;
        }
        return true;
    }
}
