package com.zdn.logic;

import com.zdn.CommandParser.CommandE;
import com.zdn.CommandParser.Property;
import com.zdn.activity.MainControl;
import com.zdn.channel.Http;
import com.zdn.event.EventDefine;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public class InternetComponent implements ServerInterface{
	/* web site address define */
	String WEBSITE_ADDRESS_BASE	= "http://10.4.65.164/";
	String WEBSITE_ADDRESS_QUERY = WEBSITE_ADDRESS_BASE + "user/check_register/";
	String WEBSITE_ADDRESS_ACCOUNT_REQ = WEBSITE_ADDRESS_BASE + "user/register/";
	String WEBSITE_ADDRESS_ADD_A_FRIEND_REQ = WEBSITE_ADDRESS_BASE + "friend/add_friend/";
	String WEBSITE_ADDRESS_ADD_A_FRIEND_ANSWER_REQ = WEBSITE_ADDRESS_BASE + "user/register/";
	
	
	
	public ThreadTaskHandler handler;
	
	int STATE_NULL    = 0;

	
	public InternetComponent( Looper looper )
	{
		handler = new ThreadTaskHandler(looper);
	}
	@Override
	public int registReq(String phoneNumber, String passWord ,String imsi ) {
		Message msg = handler.obtainMessage(); 
		msg.what = ThreadTaskHandler.SEND_MESSAGE_TO_SERVER;
        
		CommandE e = new CommandE("SEND_MESSAGE_TO_SERVER");
		e.AddAProperty(new Property("EventDefine" ,Integer.toString(EventDefine.IS_ACCOUNT_REQ ) ) );
		e.AddAProperty(new Property("URL" ,WEBSITE_ADDRESS_ACCOUNT_REQ ) );
		e.AddAProperty(new Property("mobile",phoneNumber ) );
		e.AddAProperty(new Property("password",passWord ) );
		e.AddAProperty(new Property("confirmpass",passWord ) );
		e.AddAProperty(new Property("imsi",imsi ) );
		
		msg.obj = e;
		handler.sendMessage(msg);
		return 0;
	}

	@Override
	public void isRegist(String imsi) {
		Message msg = handler.obtainMessage(); 
		msg.what = ThreadTaskHandler.SEND_MESSAGE_TO_SERVER;
        
		CommandE e = new CommandE("SEND_MESSAGE_TO_SERVER");
		e.AddAProperty(new Property("EventDefine" ,Integer.toString(EventDefine.IS_ACCOUNT_QUEUE_REQ ) ) );
		e.AddAProperty(new Property("URL" ,WEBSITE_ADDRESS_QUERY ) );
		e.AddAProperty(new Property("imsi",imsi ) );
        msg.obj = e;   //
        
        handler.sendMessage(msg);

	}

	@Override
	public int addA_Friend(CommandE e ) {
		Message msg = handler.obtainMessage(); 
		msg.what = ThreadTaskHandler.SEND_MESSAGE_TO_SERVER;
        
		e.GetProperty("URL").SetPropertyContext( WEBSITE_ADDRESS_ADD_A_FRIEND_REQ );
		msg.obj = e;   //
        
        handler.sendMessage(msg);
		return 0;
	}

	@Override
	public void friendAddMeAnswer(CommandE e) {
		Message msg = handler.obtainMessage(); 
		msg.what = ThreadTaskHandler.SEND_MESSAGE_TO_SERVER;
        
		e.GetProperty("URL").SetPropertyContext( WEBSITE_ADDRESS_ADD_A_FRIEND_ANSWER_REQ );
		msg.obj = e;   //
        
        handler.sendMessage(msg);
		
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
				String reponse = Http.httpReq( e );
			
			
				Message msg_rsp = MainControl.getInstance().obtainMessage(); 
				msg_rsp.what = MainControl.SEND_MESSAGE_TO_SERVER_RSP;
		        //��Message�����arg1�����ֵ����Ϊi
				CommandE e_r = new CommandE("SEND_MESSAGE_TO_SERVER_RSP");
				e_r.AddAProperty( new Property("HTTP_REQ_RSP",reponse ) );
				
				int rsp_event =  Integer.parseInt(e.GetPropertyContext("EventDefine")) + 1;
				e_r.AddAProperty( new Property("EventDefine", Integer.toString( rsp_event) ) );
	
				msg_rsp.obj = e_r;   
		        
				MainControl.getInstance().sendMessage(msg_rsp);
			}
			super.handleMessage(msg);
		}

	};
}
