package com.zdn.channel;


import android.telephony.SmsManager;
import android.util.Log;


public class Sms  {

	public Sms( ) {
		 
	}

	public void send(String target, String  jsString) {
		
		if (jsString != null){
			SmsManager sms = SmsManager.getDefault();
			Log.i("Sms", "sms send msg;" + jsString);
			sms.sendTextMessage(target, null, jsString, null, null);
		}
	}
}
