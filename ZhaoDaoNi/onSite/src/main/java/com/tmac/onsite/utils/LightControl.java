package com.tmac.onsite.utils;

import android.view.Window;
import android.view.WindowManager;

public class LightControl {


	public static void lightOn(Window window) {
		// TODO Auto-generated method stub
		WindowManager.LayoutParams lp = window.getAttributes();
		lp.alpha = 1.0f;
		window.setAttributes(lp);
	}
	
	
	public static void lightOff(Window window){
		WindowManager.LayoutParams lp = window.getAttributes();
		lp.alpha = 0.5f;
		window.setAttributes(lp);
	}
	
}
