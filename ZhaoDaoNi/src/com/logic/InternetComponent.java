package com.logic;

import com.channel.Http;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import CommandParser.CommandE;
import CommandParser.Property;

public class InternetComponent implements ServerInterface{
	String WEBSITE_ADDRESS_BASE	= "http://10.4.65.41/";
	String WEBSITE_ADDRESS_QUERY = WEBSITE_ADDRESS_BASE + "/user/check";
	public ThreadTaskHandler handler;
	
	int STATE_NULL    = 0;

	
	public InternetComponent( Looper looper )
	{
		handler = new ThreadTaskHandler(looper);
	}
	@Override
	public int registReq(String phoneNumber, String nickName ) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void isRegist(String imsi) {
		Message msg = handler.obtainMessage(); 
		msg.what = ThreadTaskHandler.SEND_MESSAGE_TO_SERVER;
        //将Message对象的arg1参数的值设置为i
		CommandE e = new CommandE("SEND_MESSAGE_TO_SERVER");
		e.AddAProperty(new Property("URL",WEBSITE_ADDRESS_QUERY ) );
		e.AddAProperty(new Property("IMSI",imsi ) );
        msg.obj = e;   //用arg1、arg2这两个成员变量传递消息，优点是系统性能消耗较少  
        
        handler.sendMessage(msg);

	}

	@Override
	public int addA_Friend(String PhoneNumber, String comment) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void friendAddMeAnswer(int result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public CommandE requestFriendList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateGpsInfo(String phoneNumber, int longitude, int latitude) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void requestFriendGpsInfo(String phoneNumber, int longitude,
			int latitude) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void friendAddMe(String callerPhoneNumber, String callerNickName,
			int validPeriod, String message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addA_FriendAnswer(int result) {
		// TODO Auto-generated method stub
		
	}

	
	class ThreadTaskHandler extends Handler {
		
		static public final int SEND_MESSAGE_TO_SERVER = 1;

		public ThreadTaskHandler( Looper looper )
		{
			super(looper);
		}
		@Override
		public void handleMessage(Message msg) {
			
			if(SEND_MESSAGE_TO_SERVER == msg.what )
			{
				CommandE e = (CommandE) msg.obj;
				Http.httpReq( e );
			}
			super.handleMessage(msg);
		}

		};
}
