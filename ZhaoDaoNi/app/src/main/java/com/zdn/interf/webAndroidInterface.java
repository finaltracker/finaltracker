package com.zdn.interf;

import android.content.Context;
import android.webkit.JavascriptInterface;

public class webAndroidInterface {

	private Context context;
	public webAndroidInterface(Context cxt) {
		context = cxt;
	}
	
	@JavascriptInterface
	public void selfFuncton(String title, String message) {
		

	}

}
