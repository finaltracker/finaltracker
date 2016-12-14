/**
 * 
 */
package com.tmac.onsite.utils;

import android.R.string;
import android.app.Activity;
import android.content.Context;
import android.view.View;

/**
 * @author tmac
 */
public class FindViewById {
	
	public static <VT extends View> VT getView(Activity activity, int id){
		return (VT) activity.findViewById(id);
	}

}
