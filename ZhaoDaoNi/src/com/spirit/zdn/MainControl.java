package com.spirit.zdn;

import com.logic.InternetComponent;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.telephony.TelephonyManager;

public class MainControl extends HandlerThread {
	MainActivity		mMainActivity;
	InternetComponent 	mInternetCom = null ;
	
	final public int   STATE_NULL	= 0;
	final public int   STATE_WAIT_IS_REGSIT_RESULT	= STATE_NULL + 1;
	
	
	final public int COMMAND_NULL	= 0;
	public MainControl(String name ,MainActivity ma ) {
		super(name);

		mMainActivity = ma;
		

	}

	static MainControl me = null;
	

	int    state = 0;
	static public MainControl getInstance()
	{
		return me;
	}
	@Override
	public synchronized void start() {
		super.start();
		mInternetCom = new InternetComponent( this.getLooper() );
		control( COMMAND_NULL );
	}
	
	public void go()
	{

	}
	// for state change 
	public Handler handler = new Handler( ) {
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				// 动态分辨率
			} else if (msg.what == 2) { 

			} else if (msg.what == 3) {

				

			}
		};
	};

	// RcvCommand 收到的 command
	public void control( int RcvCommand )
	{
		switch(state)
		{
		case STATE_NULL:
			TelephonyManager mTelephonyMgr = (TelephonyManager) mMainActivity.getSystemService(Context.TELEPHONY_SERVICE);
			String imsi = mTelephonyMgr.getSubscriberId();
			
			mInternetCom.isRegist( imsi );
			state = STATE_WAIT_IS_REGSIT_RESULT;
			
			break;
		case STATE_WAIT_IS_REGSIT_RESULT:
			break;
		
		
		default:
			break;
		}
		
	}


}
