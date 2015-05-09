package com.zdn.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


import com.zdn.basicStruct.networkStatusEvent;

import de.greenrobot.event.EventBus;

public class NetworkReceiver  extends BroadcastReceiver {
	private static final String TAG = "SystemEventReceiver";
	private Context context;
	private static boolean gprsConnect;
	private static boolean wifiConnect;

	public NetworkReceiver( Context context )
	{
		this.context = context;

		ConnectivityManager connectMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mobNetInfo = connectMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		NetworkInfo wifiNetInfo = connectMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

		if (mobNetInfo.isConnected() )
		{// unconnect network
			gprsConnect = true;
		}
		else
		{
			gprsConnect = false;
		}
		if(wifiNetInfo.isConnected())
		{
			wifiConnect = true;
		}
		else
		{
			wifiConnect = false;
		}

		IntentFilter intentfilter = new IntentFilter();
		intentfilter.addAction(android.net.ConnectivityManager.CONNECTIVITY_ACTION);

		context.registerReceiver( this, intentfilter);


	}

	@Override
	protected void finalize() throws Throwable {
		context.unregisterReceiver(this);
		super.finalize();
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		ConnectivityManager connectMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mobNetInfo = connectMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		NetworkInfo wifiNetInfo = connectMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

		if (mobNetInfo.isConnected() )
		{// unconnect network
			gprsConnect = true;
		}
		else
		{
			gprsConnect = false;
		}
		if(wifiNetInfo.isConnected())
		{
			wifiConnect = true;
		}
		else
		{
			wifiConnect = false;
		}

		//send message via BusEvent

		EventBus.getDefault().post( new networkStatusEvent(gprsConnect,wifiConnect) );  // dispatch message to anyone who care about it

	}

	static boolean isConnect()
	{
		if( gprsConnect || wifiConnect )
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	static boolean isGprsConnect()
	{
		return  gprsConnect;
	}

	static boolean isWifiConnect()
	{
		return wifiConnect;
	}

}