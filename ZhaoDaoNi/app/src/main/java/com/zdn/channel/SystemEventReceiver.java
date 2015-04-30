package com.zdn.channel;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

public class SystemEventReceiver extends BroadcastReceiver {
	private static final String TAG = "SystemEventReceiver";
	private static SystemEventReceiver mReceiver;
	private static networkStateCallback mCallback;
	public static SystemEventReceiver getInstance(Context context){
		if (mReceiver == null) {
			mReceiver = new SystemEventReceiver();
		}
		
		return mReceiver;
	}
	
	public interface networkStateCallback {
		public void onNetworkStateChanged();
	}
	
	public static void registerForNetworkStateChange(Context context, networkStateCallback cb){
		Log.d(TAG, "registerForConnectionStateChange");
		unRegisterForNetworkStateChange(context);
		
		IntentFilter intentfilter = new IntentFilter();
		intentfilter.addAction(android.net.ConnectivityManager.CONNECTIVITY_ACTION);
		
			
		mCallback = cb;
		if(mReceiver == null) {
			mReceiver = new SystemEventReceiver();
		}
	
		context.registerReceiver(mReceiver, intentfilter);
	}
	
	public static void unRegisterForNetworkStateChange(Context context){
		Log.d(TAG, "unRegisterForConnectionStateChanges");
		
		if(mReceiver != null){
			try {
				context.unregisterReceiver(mReceiver);
			} catch (IllegalArgumentException e){
				
			}
		}
		
		mCallback = null;
	}
	
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		if (mCallback != null){
			mCallback.onNetworkStateChanged();
		}
	}	
}