package com.zdn.logic;

import com.zdn.CommandParser.CommandE;
import com.zdn.CommandParser.ExpCommandE;
import com.zdn.CommandParser.Property;
import com.zdn.channel.xUtilsHttp;
import com.zdn.data.dataManager;
import com.zdn.event.EventDefine;
import com.zdn.interf.ServerInterfaceCmd;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.io.File;

public class InternetComponent implements ServerInterfaceCmd {
	/* web site address define */
	//static public String WEBSITE_ADDRESS_BASE	= "http://10.4.65.41/";
	static public String WEBSITE_ADDRESS_BASE	= "http://10.4.65.32/";
	static public String WEBSITE_ADDRESS_CHECK_REGIST_REQ = WEBSITE_ADDRESS_BASE + "user/check_register/";
	static public String WEBSITE_ADDRESS_REGIST_REQ = WEBSITE_ADDRESS_BASE + "user/register/";
	static public String WEBSITE_ADDRESS_ADD_A_FRIEND_REQ = WEBSITE_ADDRESS_BASE + "friend/add_friend/";
	static public String WEBSITE_ADDRESS_ADD_A_FRIEND_ANSWER_REQ = WEBSITE_ADDRESS_BASE + "friend/accept_friend/";
	static public String WEBSITE_ADDRESS_GET_FRIEND_LIST = WEBSITE_ADDRESS_BASE + "friend/get_friend/";
	static public String WEBSITE_SEARCH_FRIEND_OR_CIRCLE = WEBSITE_ADDRESS_BASE + "friend/search_friend/";
	static public String WEBSITE_ADDRESS_UPDATE_FRIEND = WEBSITE_ADDRESS_BASE + "friend/update_friend/";
	static public String WEBSITE_ADDRESS_DELETE_FRIEND = WEBSITE_ADDRESS_BASE + "friend/delete_friend/";
	
	static public String WEBSITE_ADDRESS_SEND_TIP = WEBSITE_ADDRESS_BASE + "tips/send_tip/";
	static public String WEBSITE_ADDRESS_GET_TIP = WEBSITE_ADDRESS_BASE + "tips/get_tip/";
	static public String WEBSITE_ADDRESS_UPLOAD_FILE = WEBSITE_ADDRESS_BASE + "user/upload_avatar/";
	
	
	public ThreadTaskHandler handler;
	
	int STATE_NULL    = 0;

	
	public InternetComponent( Looper looper )
	{
		handler = new ThreadTaskHandler(looper);
	}
	
	@Override
	public int registReq( CommandE e   ) {
		Message msg = handler.obtainMessage(); 
		msg.what = ThreadTaskHandler.SEND_MESSAGE_TO_SERVER;
        
		msg.obj = e;   //
        
        handler.sendMessage(msg);
		return 0;
	}

	@Override
	public void isRegist(String imsi) {
		Message msg = handler.obtainMessage(); 
		msg.what = ThreadTaskHandler.SEND_MESSAGE_TO_SERVER;
        
		CommandE e = packA_CommonExpCommandE_ToServer(EventDefine.CHECK_REGIST_REQ, WEBSITE_ADDRESS_CHECK_REGIST_REQ);

		e.AddAProperty(new Property("imsi",imsi ) );
        msg.obj = e;   //
        
        handler.sendMessage(msg);

	}

	@Override
	public int addA_Friend(CommandE e ) {
		Message msg = handler.obtainMessage(); 
		msg.what = ThreadTaskHandler.SEND_MESSAGE_TO_SERVER;
        
		//e.GetProperty("URL").SetPropertyContext( WEBSITE_ADDRESS_ADD_A_FRIEND_REQ );
		msg.obj = e;   //
        
        handler.sendMessage(msg);
		return 0;
	}

	@Override
	public void friendAddMeAnswer(CommandE e) {
		Message msg = handler.obtainMessage(); 
		msg.what = ThreadTaskHandler.SEND_MESSAGE_TO_SERVER;
        
		//e.GetProperty("URL").SetPropertyContext( WEBSITE_ADDRESS_ADD_A_FRIEND_ANSWER_REQ );
		msg.obj = e;   //
        
        handler.sendMessage(msg);
		
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
	public void getFriendList( CommandE e )
	{
		
		Message msg = handler.obtainMessage(); 
		msg.what = ThreadTaskHandler.SEND_MESSAGE_TO_SERVER;
        
		msg.obj = e;   //
        
        handler.sendMessage(msg);
		
		
	};
	
	@Override
	public void searchFirendOrCircle( CommandE e ) {
		Message msg = handler.obtainMessage(); 
		msg.what = ThreadTaskHandler.SEND_MESSAGE_TO_SERVER;
        
		msg.obj = e;   //
        
        handler.sendMessage(msg);

	}


	@Override
	public void updateFriendInfomation(CommandE e) {
		Message msg = handler.obtainMessage(); 
		msg.what = ThreadTaskHandler.SEND_MESSAGE_TO_SERVER;
        
		msg.obj = e;   //
        
        handler.sendMessage(msg);

		
	}

	@Override
	public void deleteFriend(CommandE e) {
		Message msg = handler.obtainMessage(); 
		msg.what = ThreadTaskHandler.SEND_MESSAGE_TO_SERVER;
        
		msg.obj = e;   //
        
        handler.sendMessage(msg);

	}
	

	@Override
	public void login(CommandE e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendTip(CommandE e , Message m ) {
		Message msg = handler.obtainMessage(); 
		msg.what = ThreadTaskHandler.SEND_MESSAGE_TO_SERVER;
        
		msg.obj = e;   //
        
        handler.sendMessage(msg);
		
	}

	@Override
	public void getTip(CommandE e) {
		Message msg = handler.obtainMessage(); 
		msg.what = ThreadTaskHandler.SEND_MESSAGE_TO_SERVER;
        
		msg.obj = e;   //
        
        handler.sendMessage(msg);
		
	}

	@Override
	public void uploadFile(CommandE e) {
		Message msg = handler.obtainMessage();
		msg.what = ThreadTaskHandler.SEND_MESSAGE_TO_SERVER;

		msg.obj = e;   //

		handler.sendMessage(msg);

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
			    ExpCommandE e = (ExpCommandE) msg.obj;
				xUtilsHttp.httpReq( e  );
			}
			super.handleMessage(msg);
		}
		
		
	}


	static public ExpCommandE packA_CommonExpCommandE_ToServer( int eventDefine , String URL )
	{
		ExpCommandE e = new ExpCommandE("SEND_MESSAGE_TO_SERVER");
		e.AddAExpProperty(new Property("EventDefine" ,Integer.toString( eventDefine) ) );
		e.AddAExpProperty(new Property("URL" ,URL ) );
		
		e.AddAProperty(new Property("local_friend_version",Integer.toString( dataManager.self.preferencesPara.getFriendListVersion() )) );
		e.AddAProperty(new Property("imsi",dataManager.self.getImsi() ) );
		e.AddAProperty(new Property("mobile",dataManager.self.preferencesPara.getPhoneNumber() ) );
		
		
		return e;
	}

	
	static public ExpCommandE packA_CommonExpCommandE_ToMainControl( String name , int eventDefine )
	{
		ExpCommandE e = new ExpCommandE(name);
		e.AddAExpProperty(new Property("EventDefine" ,Integer.toString( eventDefine) ) );
		
		return e;
	}


	

}
