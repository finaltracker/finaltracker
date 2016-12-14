/**
 * 
 */
package com.tmac.onsite.utils;

import android.content.Context;

/**
 * @author tmac
 */
public class DimenConvert {

	public static int dipToPx(Context context, float dpValues){
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (scale * dpValues + scale * (dpValues >= 0 ? 1 : -1));
	}
	
}
