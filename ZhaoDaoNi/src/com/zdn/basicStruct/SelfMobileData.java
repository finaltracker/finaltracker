package com.zdn.basicStruct;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.zdn.util.PreferencesUtil;

//define self ue struct

public class SelfMobileData {

	String imsi;
	//public String phone; // phone number
	
	public PreferencesUtil     preferencesPara;
	public friendMemberData selfInfo;
	Context context;
	
	
	public SelfMobileData()
	{
		selfInfo = new friendMemberData();
		imsi ="";
		context = null;
	}
	
	public void init( Context context )
	{
		this.context = context;
		preferencesPara = new PreferencesUtil(context);
		TelephonyManager mTelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		setImsi(mTelephonyMgr.getSubscriberId());
	}
	public void setImsi( String imsi )
	{
		this.imsi = imsi;
		if(imsi== null || imsi.isEmpty() )
		{
			Toast.makeText(context, "«Î≤Â»ÎSIMø®", Toast.LENGTH_SHORT).show();
		}
		
	}
	
	public String getImsi() { return imsi ; }
}