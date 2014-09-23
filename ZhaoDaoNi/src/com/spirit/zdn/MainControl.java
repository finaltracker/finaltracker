package com.spirit.zdn;

import java.util.Set;

import jpush.ExampleUtil;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.TagAliasCallback;

import com.common.EventDefine;
import com.logic.InternetComponent;

import CommandParser.CommandE;
import CommandParser.Property;
import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.util.Log;

public class MainControl extends HandlerThread {
	MainActivity		mMainActivity;
	InternetComponent 	mInternetCom = null ;
	static public  MainControl me = null;
	static String imsi = null;
	
	final public int   STATE_NULL	= 0;
	final public int   STATE_WAIT_QUEUE_REGSIT_RESULT	= STATE_NULL + 1; //查询是否祖册了
	final public int   STATE_WAIT_UI_LOGIN				= STATE_WAIT_QUEUE_REGSIT_RESULT + 1; // wait UI launch login procedure
	final public int   STATE_WAIT_SERVER_REGSIT_RESULT			= STATE_WAIT_UI_LOGIN + 1; // wait UI launch login procedure
	final public int   STATE_LOGIN_NORMAL			= STATE_WAIT_SERVER_REGSIT_RESULT + 1; // LOGIN_NORMAL

	
	String TAG = "MainControl";
	final public int COMMAND_NULL	= 0;
	
	static public final int SEND_MESSAGE_TO_SERVER_RSP = 1;
	static public final int JPUSH_SERVER_TO_UE_COMMAND = 2;
	
	public MainControl(String name ,MainActivity ma ) {
		super(name);

		mMainActivity = ma;
		me = this;
		TelephonyManager mTelephonyMgr = (TelephonyManager) mMainActivity.getSystemService(Context.TELEPHONY_SERVICE);
		imsi = mTelephonyMgr.getSubscriberId();

	}


	int    state = 0;
	static public MainControl getInstance()
	{
		return me;
	}
	@Override
	public synchronized void start() {
		super.start();
		mInternetCom = new InternetComponent( this.getLooper() );
		CommandE e = new  CommandE("COMMAND_ULL");
		e.AddAProperty(new Property("EventDefine","0") );
		control( e );
	}
	
	public void go()
	{
		
	}
	
	public Message obtainMessage()
	{
		return handler.obtainMessage();
	}
	
	public void sendMessage( Message msg )
	{
		handler.sendMessage(msg);
	}
	
	// for state change 
	public Handler handler = new Handler( ) {
		public void handleMessage(Message msg) {
			//if (msg.what == SEND_MESSAGE_TO_SERVER_RSP ) {
			{
				CommandE e = (CommandE) msg.obj;
				control( e );
			}


		};
	};

	// RcvCommand  command
	public void control( CommandE e )
	{
		int RcvCommand = Integer.parseInt(e.GetPropertyContext("EventDefine"));
		Log.d("MainControl", "control:RcvCommand " + e.GetPropertyContext("EventDefine") );
		switch(state)
		{
		case STATE_NULL:
			
			
			mInternetCom.isRegist( imsi );
			state = STATE_WAIT_QUEUE_REGSIT_RESULT;
			
			break;
		case STATE_WAIT_QUEUE_REGSIT_RESULT:  // waiting queue gegist result
			switch (RcvCommand)
			{
			case EventDefine.IS_ACCOUNT_QUEUE_RSP:
				String rep = e.GetPropertyContext("HTTP_REQ_RSP");
				if( rep == null || ( rep.isEmpty()) )
				{
					Log.d("MainControl", "HTTP_REQ_RSP = null" );
					
					{ // for test
						Message m = MainActivity.getInstance().handler.obtainMessage();
						m.what = MainActivity.EVENT_UI_LOG_IN_START;
						MainActivity.getInstance().handler.sendMessage( m );
						state = STATE_WAIT_UI_LOGIN;
					}
					break;
				}
				JSONObject  jason_obj = null;
				int queueRsp = -1;
				try {
					jason_obj = new JSONObject(rep);
					queueRsp = jason_obj.getInt("status");
				} catch (JSONException e1) {

					Log.d("MainControl" , "server response error: " + e1.getMessage() );
					e1.printStackTrace();
				}
				
				if ( queueRsp == EventDefine.IS_REQIST_RSP_NO_REGIST )
				{
					//  regist a account
					Message m = MainActivity.getInstance().handler.obtainMessage();
					m.what = MainActivity.EVENT_UI_LOG_IN_START;
					MainActivity.getInstance().handler.sendMessage( m );
					state = STATE_WAIT_UI_LOGIN;
				}
				else if( queueRsp == EventDefine.IS_REQIST_RSP_HAS_REGIST )
				{// 
					// 请求好友列表
					mInternetCom.requestFriendList();
					state = STATE_LOGIN_NORMAL;
					//TODO
				}
				else
				{
					Log.d("MainControl" , "server response error queueRsp =  " + queueRsp );
				}
				break;
			default:
				Log.d("MainControl", "unknow RcvCommand " + RcvCommand );
				break;
			}
			break;
		
		case STATE_WAIT_UI_LOGIN:
			switch (RcvCommand)
			{
			case EventDefine.UI_TO_CTRL_ACCOUNT_REQUEST:
				String Id = e.GetPropertyContext("ID");
				String passWord = e.GetPropertyContext("PASS_WORD");

				mInternetCom.registReq(Id,  passWord ,imsi );
				state = STATE_WAIT_SERVER_REGSIT_RESULT;
			break;
			default:
			break;
			
			}
			
		case STATE_WAIT_SERVER_REGSIT_RESULT:  // waiting queue regist result
			switch (RcvCommand)
			{
			case EventDefine.IS_ACCOUNT_RSP:
				//获得注册结果
				String rep = e.GetPropertyContext("HTTP_REQ_RSP");
				
				
				if( rep == null || ( rep.isEmpty()) )
				{
					//no internet connection or server no response 
				}
				else
				{
					JSONObject  jason_obj = null;
					int queueRsp = -1;
					String error = "";
					
					try {
						jason_obj = new JSONObject(rep);
						queueRsp = jason_obj.getInt("status");
						error    = jason_obj.getString("error");
					} catch (JSONException e1) {

						Log.d("MainControl" , "server response error: " + e1.getMessage() );
						e1.printStackTrace();
					}
					
					Message m = MainActivity.getInstance().handler.obtainMessage();
					m.what = MainActivity.EVENT_UI_REGIST_RESULT;
					m.arg1 = queueRsp;
					m.obj = error;
					
					switch(queueRsp)
					{
						case 0: // success
							state = STATE_LOGIN_NORMAL;
							break;
						default:
							Log.d("MainControl","regist a account server response:" + queueRsp );
							state = STATE_WAIT_UI_LOGIN;
							break; //exception
					}
	
					MainActivity.getInstance().handler.sendMessage( m );
					
				}
				break;
			default:
				break;
			}
			break;
		case STATE_LOGIN_NORMAL:
		{
			switch( RcvCommand )
			{
			case EventDefine.ADD_A_FRIEND:
				mInternetCom.addA_Friend( e );
				break;
			case EventDefine.ADD_A_FRIEND_RSP:
			{
				String rep = e.GetPropertyContext("HTTP_REQ_RSP");
				JSONObject  jason_obj = null;
				int queueRsp = -1;
				try {
					jason_obj = new JSONObject(rep);
					queueRsp = jason_obj.getInt("status");
				} catch (JSONException e1) {

					Log.d("MainControl" , "server response error: " + e1.getMessage() );
					e1.printStackTrace();
				}
				
				if( queueRsp == 0 )
				{
					Log.d("MainControl" , "add a friend ，server accept!" );
					//request OK , only need wait the friend feedback
				}
				else if (queueRsp == 34 )
				{
					Log.d("MainControl" , "add a friend ，server response: friend have not exist!" );
					//TODO
					//发送短信提醒
				}
				else
				{
					Log.d("MainControl" , "add a friend ，user do not exist" );
				}
				
				break;
			}
			case EventDefine.JPUSH_SERVER_COMMAND:
			{
			
				int cmd =  Integer.parseInt(e.GetPropertyContext("Command"));
				
				switch (cmd )
				{
				case 201:
					
					Log.d("MainControl" , "jpush server call me ,someone add me " );
					//request OK , only need wait the friend feedback
					break;
				default:
					Log.d("MainControl" , "jpush server call me  , but command undefine" );
					break;
				}
				break;
			}
			case EventDefine.ADD_A_FRIEND_ANSWER:
				mInternetCom.friendAddMeAnswer(e);
				break;
			
			default:
				break;
			}
			break;
		}
		default:
			break;
		}
		
	}
	

}
