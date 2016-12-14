package com.tmac.onsite.utils;

import android.R.integer;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnDismissListener;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.tmac.onsite.R;
import com.tmac.onsite.activity.DetailNoBeginActivity;
import com.tmac.onsite.activity.UploadImgActivity;

public class MyDialog {
	
	public static final int POSITIVE = 0;
	public static final int NEGATIVE = 1;
	private static final String TAG = "LC-MyDialog";
	private static boolean DBG = true;

	public static void showDialog(Context context, int message, int positiveStr, int negativeStr, 
			final Window window, final OnDialogClickListener listener, final int situation){
		lightOff(window);
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(message)
				.setPositiveButton(positiveStr, new DialogInterface.OnClickListener() {
						
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						listener.onDialog(POSITIVE, situation);
					}
					
				})
				.setNegativeButton(negativeStr, new DialogInterface.OnClickListener() {
						
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						listener.onDialog(NEGATIVE, situation);		
					}
				})
				.setOnDismissListener(new OnDismissListener() {
					@Override
					public void onDismiss(DialogInterface dialog) {
						// TODO Auto-generated method stub
						lightOn(window);
					}
				})
				.create()
				.show();
	}
	
	public static void lightOn(Window window) {
		// TODO Auto-generated method stub
		if(DBG) Log.d(TAG, "lightOff");
		WindowManager.LayoutParams lp = window.getAttributes();
		lp.alpha = 1.0f;
		window.setAttributes(lp);
	}
	
	
	public static void lightOff(Window window){
		if(DBG) Log.d(TAG, "lightOff");
		WindowManager.LayoutParams lp = window.getAttributes();
		lp.alpha = 0.5f;
		window.setAttributes(lp);
	}
	
	public interface OnDialogClickListener{
		void onDialog(int type, int situation);
	}

}
