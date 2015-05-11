package com.zdn.logic;

import org.json.JSONException;
import org.json.JSONObject;

import com.zdn.CommandParser.CommandE;
import com.zdn.CommandParser.ExpCommandE;
import com.zdn.CommandParser.Property;
import com.zdn.activity.MainActivity;
import com.zdn.activity.PeopleActivity;
import com.zdn.activity.searchFriendResultForAddActivity;
import com.zdn.basicStruct.SendMessageRspEvent;
import com.zdn.basicStruct.friendMemberData;
import com.zdn.basicStruct.friendMemberDataBasic;
import com.zdn.basicStruct.getMessageRspEvent;
import com.zdn.basicStruct.networkStatusEvent;
import com.zdn.chat.ZdnMessage;
import com.zdn.data.dataManager;
import com.zdn.event.EventDefine;
import com.zdn.receiver.NetworkReceiver;
import com.zdn.util.ObjectConvertTool;

import de.greenrobot.event.EventBus;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class MainControl extends HandlerThread {
	Context mContext;
	
	InternetComponent 	mInternetCom = null ;
	static public  MainControl me = null;
	
	
	final public int   STATE_NULL	= 0;
	final public int   STATE_WAIT_QUEUE_REGSIT_RESULT	= STATE_NULL + 1; //查询是否祖册了
	final public int   STATE_WAIT_UI_LOGIN				= STATE_WAIT_QUEUE_REGSIT_RESULT + 1; // wait UI launch login procedure
	final public int   STATE_WAIT_SERVER_REGSIT_RESULT			= STATE_WAIT_UI_LOGIN + 1; // wait UI launch login procedure
	final public int   STATE_LOGIN_NORMAL			= STATE_WAIT_SERVER_REGSIT_RESULT + 1; // LOGIN_NORMAL
	final public int   STATE_OUT_OF_NETWORK           = STATE_LOGIN_NORMAL + 1;   // 没有网络

	int    preState = 0;
	int    state = 0;

	String TAG = "MainControl";
	final public int COMMAND_NULL	= 0;
	
	static public final int SEND_MESSAGE_TO_SERVER_RSP = 1;
	private NetworkReceiver networkReceiver = null;
	
	
	public MainControl(String name ,Context ma ) {
		super(name);
		
		mContext = ma;
		dataManager.init(mContext);

		me = this;
		networkReceiver=new NetworkReceiver( ma );

		EventBus.getDefault().register(this);

	}



	static public MainControl getInstance()
	{
		return me;
	}
	@Override
	public synchronized void start(){
		super.start();
		mInternetCom = new InternetComponent( this.getLooper() );

	}

    private void setState( int newState )
    {
        preState = state;
        state = newState;
		Log.d( "MainControl","setState " + newState );
        
    }
	private void restart()
	{
		setState( STATE_NULL );
		ExpCommandE e = new  ExpCommandE("COMMAND_NULL");
		e.AddAExpProperty(new Property("EventDefine", "0"));
		control( e );
	}

	public void onEvent(Object event) {

		if( event instanceof networkStatusEvent)
		{ // 网络状态改变
			networkStatusEvent e = (networkStatusEvent)event;
			ExpCommandE command_e = new ExpCommandE("COMMAND_NETWORK_STATE");
			command_e.AddAExpProperty(new Property("EventDefine", Integer.toString(EventDefine.COMMAND_NETWORK_STATE)));

			if( (false == e.getwifiConnect()) &&  ( false == e.getGprsConnect() ))
			{ // no connect
				command_e.AddAProperty(new Property("CONNECT", "false"));
				control(command_e);
				Toast.makeText( this.mContext , "out of network",Toast.LENGTH_SHORT).show();
			}
			else
			{
				command_e.AddAProperty(new Property("CONNECT", "true"));

			}

			control( command_e );

		}


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
// some commandE don't care state machine
	
	//return true : handled
    //       false: no handle
	private boolean commonStateHandle(CommandE e  )
	{
		boolean ret = false;

		int RcvCommand = Integer.parseInt(((ExpCommandE )e).GetExpPropertyContext("EventDefine"));

		switch (RcvCommand) {
			case EventDefine.COMMAND_NETWORK_STATE:
				String state = e.GetPropertyContext("CONNECT");

				if( state.equals("true") )
				{
					if(	 STATE_LOGIN_NORMAL == preState	 )
					{ // just do state change
						setState( STATE_LOGIN_NORMAL );
					}
					else
					{
						restart();
					}
				}
				else
				{
					//just do state change
					setState( STATE_OUT_OF_NETWORK );
				}
				ret = true;
				break;
		}
		return ret;
	}
	

	private void stateNull( CommandE e  )
	{
		if ( NetworkReceiver.isConnect() )
		{
			mInternetCom.isRegist(dataManager.self.getImsi());
			setState(STATE_WAIT_QUEUE_REGSIT_RESULT);
		}
		else
		{ // no network
			setState( STATE_OUT_OF_NETWORK );
		}
	}

	private void stateWaitQueueRegsitResult( CommandE e  )
	{
		int RcvCommand = Integer.parseInt(((ExpCommandE )e).GetExpPropertyContext("EventDefine"));
		
		switch (RcvCommand)
		{
		case EventDefine.CHECK_REGIST_RSP:
			String rep = e.GetPropertyContext("HTTP_REQ_RSP");
			if( rep == null || ( rep.isEmpty()) )
			{
				Log.d("MainControl", "HTTP_REQ_RSP = null" );

				break;
			}
			JSONObject  json_obj = null;
			int queueRsp = -1;
			String username= "";
			try {
				json_obj = new JSONObject(rep);
				queueRsp = json_obj.getInt("status");
				username = json_obj.getString("username");
				
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
				setState(STATE_WAIT_UI_LOGIN);
			}
			else if( queueRsp == EventDefine.IS_REQIST_RSP_HAS_REGIST )
			{// 
				// 请求好友列表
				dataManager.self.preferencesPara.savePhoneNumber( username );
				mInternetCom.getFriendList(packGetFriendListCommandE() );
				
				setState( STATE_LOGIN_NORMAL );
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
		
		
	}

	private void stateWaitUiLogin( CommandE e  )
	{
		int RcvCommand = Integer.parseInt(((ExpCommandE )e).GetExpPropertyContext("EventDefine"));
		
		switch (RcvCommand)
		{
		case EventDefine.REGIST_REQ:
			dataManager.self.preferencesPara.savePhoneNumber(  e.GetProperty("mobile").GetPropertyContext() );
			dataManager.self.preferencesPara.savePassWord(  e.GetProperty("password").GetPropertyContext() );
			
			mInternetCom.registReq( e );
			setState(STATE_WAIT_SERVER_REGSIT_RESULT);
		break;
		default:
		break;
		
		}
		
	}

	private void stateWaitServerRegistResult( CommandE e )
	{
		int RcvCommand = Integer.parseInt(((ExpCommandE )e).GetExpPropertyContext("EventDefine"));
		
		switch (RcvCommand)
		{
		case EventDefine.REGIST_RSP:
			//获得注册结果
			String rep = e.GetPropertyContext("HTTP_REQ_RSP");
			
			
			if( rep == null || ( rep.isEmpty()) )
			{
				//no internet connection or server no response 
			}
			else
			{
				JSONObject  json_obj = null;
				int queueRsp = -1;
				String error = "";
				
				try {
					json_obj = new JSONObject(rep);
					queueRsp = json_obj.getInt("status");
					error    = json_obj.getString("error");
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
						Log.d("MainControl","regist success" );
						Toast.makeText(mContext, "regist success", Toast.LENGTH_SHORT).show();
						setState(STATE_LOGIN_NORMAL);
						break;
						
					default:
						Log.d("MainControl","regist a account server response:" + queueRsp );
						setState(STATE_WAIT_UI_LOGIN);
						break; //exception
				}

				MainActivity.getInstance().handler.sendMessage( m );
				
			}
			break;
		default:
			break;
		}
	}

	private void stateLoginNormal( CommandE e )
	{
		ExpCommandE exp_e = (ExpCommandE)e;
		int RcvCommand = Integer.parseInt(exp_e.GetExpPropertyContext("EventDefine"));
		

		switch( RcvCommand )
		{
		case EventDefine.ADD_A_FRIEND_REQ:
		{
			Log.d("MainControl" , "ADD_A_FRIEND: " );
			mInternetCom.addA_Friend( e );
			break;
		}
		case EventDefine.ADD_A_FRIEND_RSP:
		{
			int queueRsp = parseHttpReqRspStatus(e);
			
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
			case 202:
				
				Log.d("MainControl" , "jpush server call me ,update friend " );
				mInternetCom.getFriendList( packGetFriendListCommandE() );
				//
				break;
			
			case 302:
				Log.d("MainControl" , "jpush server call me ,new message comming " );
				String Extra = e.GetPropertyContext("Extra");
				JSONObject json_obj;
				String from ;
				String id;
				try {
				json_obj = new JSONObject(Extra);
				from = json_obj.getString("from");
				id = json_obj.getString("id");
				
				} catch (JSONException e1) {

					Log.d("MainControl" , "server response error: " + e1.getMessage() );
					e1.printStackTrace();
					break ;
				}
				//TO DO 
				getMessageToServer( from , id );
				break;
				
			default:
				Log.d("MainControl" , "jpush server call me  , but command undefine" );
				break;
			}
			break;
		}
		case EventDefine.GET_FRIEND_LIST_REQ:
		{
			Log.d("MainControl" , "invalid GET_FRIEND_LIST_REQ message" );
		
			assert(false);
			break;
		}

			
		case EventDefine.GET_FRIEND_LIST_RSP:
		{
			//update UI
			//获得注册结果
			String rep = e.GetPropertyContext("HTTP_REQ_RSP");
			
			
			if( rep == null || ( rep.isEmpty()) )
			{
				//no internet connection or server no response 
			}
			else
			{
				JSONObject  json_obj = null;
				String error = "";
				Log.d("MainControl" , "GET_FRIEND_LIST_RSP: " );
				try {
					json_obj = new JSONObject(rep);
					
					int status = json_obj.getInt("status");
					if( status != 0 )
					{
						Log.d("MainControl", "GET_FRIEND_LIST_RSP status = " + status );
					}
					dataManager.self.preferencesPara.saveFriendListVersion(json_obj.getInt("server_friend_version"));
					
					dataManager.updateFriendListFromServer( json_obj.getInt("update_type") , json_obj.getJSONArray("friends") , mContext);
					//send it to PeopleActivity
					if( PeopleActivity.getInstance() != null )
					{
						Message m = PeopleActivity.getInstance().handler.obtainMessage();
						m.what = PeopleActivity.UPDATE_VIEW_FROM_REMOT ;
						m.obj = null;
						
						PeopleActivity.getInstance().handler.sendMessage(m);
					}
					else
					{
						Log.e("MainControl" , "PeopleActivity.getInstance() == null " );
					}
				} catch (JSONException e1) {

					Log.d("MainControl" , "server response error: " + e1.getMessage() );
					e1.printStackTrace();
				}
			}
		}
			break;
		case EventDefine.ADD_A_FRIEND_ANSWER_REQ:
			Log.d("MainControl" , "ADD_A_FRIEND_ANSWER_REQ: " );
			mInternetCom.friendAddMeAnswer(e);
			break;
		case EventDefine.ADD_A_FRIEND_ANSWER_RSP:
			{
				Log.d("MainControl" , "ADD_A_FRIEND_ANSWER_RSP: " );
				int queueRsp = parseHttpReqRspStatus(e);
				// 请求好友列表
				mInternetCom.getFriendList( packGetFriendListCommandE() );
				
				if( queueRsp == 0 )
				{

					
					Log.d("MainControl" , "send ADD_A_FRIEND_ANSWER_REQ queueRsp ok" );

				}
				else
				{
					Log.d("MainControl" , "send ADD_A_FRIEND_ANSWER_REQ queueRsp err queueRsp = " + queueRsp );
				}
			}
			break;
		case EventDefine.UPDATE_FRIEND_INFORMATION_REQ:
			Log.d("MainControl" , "UPDATE_FRIEND_INFORMATION_REQ: " );
			mInternetCom.updateFriendInfomation(e);
			break;
			
		case EventDefine.UPDATE_FRIEND_INFORMATION_RSP:
			{
				Log.d("MainControl" , "UPDATE_FRIEND_INFORMATION_RSP: " );
				int queueRsp = parseHttpReqRspStatus(e);
				
				if( queueRsp == 0 )
				{
					Log.d("MainControl" , "send UPDATE_FRIEND_INFORMATION_REQ ok" );

				}
			}
			break;
		case EventDefine.DELETE_FRIEND_REQ:
			Log.d("MainControl" , "DELETE_FRIEND_REQ: " );
			mInternetCom.deleteFriend(e);
			break;
			
		case EventDefine.DELETE_FRIEND_RSP:
			{
				Log.d("MainControl" , "DELETE_FRIEND_RSP: " );
				int queueRsp = parseHttpReqRspStatus(e);
				
				if( queueRsp == 0 )
				{
					Log.d("MainControl" , "send DELETE_FRIEND_REQ ok" );
					int serverVersion = getIntFromJasonObj( parseHttpReqRsp(e) , "server_friend_version" );
					if( dataManager.self.preferencesPara.getFriendListVersion() +1 == serverVersion )
					{
						
					}
					else
					{
						// 请求好友列表
						mInternetCom.getFriendList( packGetFriendListCommandE() );
						
					}

				}
				else
				{
					// 请求好友列表
					mInternetCom.getFriendList( packGetFriendListCommandE() );
					
				}
			}
			break;	
		case EventDefine.SEARCH_FRIEND_OR_CIRCLE_REQ:
			Log.d("MainControl" , "SEARCH_FRIEND_OR_CIRCLE_REQ: " );
			mInternetCom.searchFirendOrCircle( e );
			
			break;
		case EventDefine.SEARCH_FRIEND_OR_CIRCLE_RSP:
			{
				Log.d("MainControl" , "SEARCH_FRIEND_OR_CIRCLE_RSP: " );
				//update UI
				//获得注册结果
				String rep = e.GetPropertyContext("HTTP_REQ_RSP");
				
				
				if( rep == null || ( rep.isEmpty()) )
				{
					//no internet connection or server no response 
				}
				else
				{
					JSONObject  json_obj = null;
					try {
						json_obj = new JSONObject(rep);
						
						//send it to searchFriendResultForAddActivity
						if( searchFriendResultForAddActivity.getInstance() != null )
						{
							Message m = searchFriendResultForAddActivity.getInstance().handler.obtainMessage();
							m.what = searchFriendResultForAddActivity.UPDATE_VIEW_FROM_REMOT;
							m.obj = json_obj;
							searchFriendResultForAddActivity.getInstance().handler.sendMessage(m);
						}
						else
						{
							Log.e("MainControl" , "PeopleActivity.getInstance() == null " );
						}
					} catch (JSONException e1) {

						Log.d("MainControl" , "server response error: " + e1.getMessage() );
						e1.printStackTrace();
					}
				}
			}
			break;
			
		case EventDefine.SEND_MESSAGE_REQ:
			Log.d("MainControl" , "SEND_MESSAGE_REQ: " );
			mInternetCom.searchFirendOrCircle( e );
			
			break;
		case EventDefine.SEND_MESSAGE_RSP:
			{
				Log.d("MainControl" , "SEND_MESSAGE_RSP: " );
				
				ExpCommandE Exp_e = (ExpCommandE)(e);
				String rep = Exp_e.GetPropertyContext("HTTP_REQ_RSP");
				String status = Exp_e.GetPropertyContext("STATUS");
				ZdnMessage m = (ZdnMessage) Exp_e.getUserData();
				if(  0 != Integer.parseInt(status))
				{
					//no internet connection or server no response 
					m.setState( ZdnMessage.MSG_STATE_FAIL);
				}
				else
				{
					m.setState( ZdnMessage.MSG_STATE_SUCCESS );
					
				}
				SendMessageRspEvent smre = new SendMessageRspEvent();
				smre.m = m;
				EventBus.getDefault().post( smre );  // dispatch message to anyone who care about it

				m.SaveToDb();
			}
			break;
		
		case EventDefine.GET_MESSAGE_REQ:
			Log.d("MainControl" , "SEND_MESSAGE_REQ: " );
			mInternetCom.getTip( e );
			
			break;
		case EventDefine.GET_MESSAGE_RSP:
			{
				Log.d("MainControl" , "GET_MESSAGE_RSP: " );
				getMessageRspHandle( e);
			}
			break;
		default:
			break;
		}
	
	}
	
	private void stateMachineHandle(CommandE e  )
	{

		Log.d("MainControl", "stateMachineHandle:RcvCommand " + e.GetPropertyContext("EventDefine") );

		switch(state)
		{
		case STATE_NULL:
			
			stateNull(e);
			break;
		case STATE_WAIT_QUEUE_REGSIT_RESULT:  // waiting queue gegist result
			stateWaitQueueRegsitResult(e);
			break;
		
		case STATE_WAIT_UI_LOGIN:
			stateWaitUiLogin(e);
			break;
			
		case STATE_WAIT_SERVER_REGSIT_RESULT:  // waiting query regist result
			stateWaitServerRegistResult(e);
			
			break;
		case STATE_LOGIN_NORMAL:
			stateLoginNormal(e);
			break;
		default:
			break;
		}
		
	
	}
	
	
	// RcvCommand  command
	public void control( CommandE e )
	{
		ExpCommandE expE = (ExpCommandE)e;
		Log.d("MainControl", "control:RcvCommand " + expE.GetExpPropertyContext("EventDefine") );
		
		
		if( !commonStateHandle(e) )
		{
			stateMachineHandle(e);
		}
	
	}
	
	private JSONObject parseHttpReqRsp( CommandE e  )
	{
		String rep = e.GetPropertyContext("HTTP_REQ_RSP");
		JSONObject  json_obj = null;
		try {
			json_obj = new JSONObject(rep);
		} catch (JSONException e1) {

			Log.d("MainControl" , "server response error: " + e1.getMessage() );
			e1.printStackTrace();
		}
		
		return json_obj;
	}
	
	private int parseHttpReqRspStatus( CommandE e  )
	{
		int queueRsp = -1;
		JSONObject  json_obj = parseHttpReqRsp(e);
		try {
			queueRsp = json_obj.getInt("status");
		} catch (JSONException e1) {
	
			Log.d("MainControl" , "server response error: " + e1.getMessage() );
			e1.printStackTrace();
		}
		
		
		return queueRsp;
	}	

	private int getIntFromJasonObj( JSONObject obj , String what )
	{
		int ret = -1;
		
		try {
			ret = obj.getInt(what);
		} catch (JSONException e1) {
	
			Log.d("MainControl" , "getIntFromJasonObj what = " + what + "error: " + e1.getMessage() );
			e1.printStackTrace();
		}
		
		return ret;
	}
	
	private String getStringFromJasonObj( JSONObject obj , String what )
	{
		String ret = "";
		
		try {
			ret = obj.getString( what );
		} catch (JSONException e1) {
	
			Log.d("MainControl" , "getIntFromJasonObj what = " + what + "error: " + e1.getMessage() );
			e1.printStackTrace();
		}
		
		return ret;
	}
	
	static private void addMessageToFriendData( ZdnMessage m )
	{
		ZdnMessage zdn_m ;
		
		
		friendMemberData fmd = dataManager.getFrilendList().getMemberDataByPhoneNumber(m.getBelogTag());
		
		if( fmd != null )
		{
			fmd.getMessageList().add(m);
		}
	}
	//handle
	private void getMessageRspHandle(CommandE e)
	{
		String rep = e.GetPropertyContext("HTTP_REQ_RSP");
		String status = e.GetPropertyContext("STATUS");
		
		if(  0 != Integer.parseInt(status))
		{
			Log.e( this.getClass().getSimpleName(),"get tip status = " + status );
			return ;
		}
		
		JSONObject  json_obj = null;
		try {
			json_obj = new JSONObject(rep);
			
			
			int		type             = 0;  //0-text
			int		state            = 1; //success
			String	fromUserName     = getStringFromJasonObj(json_obj,"friend_mobile");
			String	fromUserAvatar   = "";
			String	toUserName       = getStringFromJasonObj(json_obj,"mobile");
			String	toUserAvatar     = "";
			String	content          = getStringFromJasonObj(json_obj,"message");
			boolean	isSend           = true;
			boolean	sendSucces       = true;
			String	time             = getStringFromJasonObj(json_obj,"create_time");

					
			ZdnMessage zdn_m = new ZdnMessage( fromUserName , 
												type,
												state,
												fromUserName,
												fromUserAvatar,
												toUserName,
												toUserAvatar,
												content,
												isSend,
												sendSucces,
												time
												);
			
			zdn_m.SaveToDb();
			//add the message to friend's data
			addMessageToFriendData(zdn_m);
			getMessageRspEvent gmre = new  getMessageRspEvent();
			gmre.m = zdn_m;
			
			EventBus.getDefault().post( gmre ); // publish event to listener
			
		} catch (JSONException e1) {

			Log.d("MainControl" , "get tip response error: " + e1.getMessage() );
			e1.printStackTrace();
		}
	}

	@Override
	public boolean quit() {

		if( networkReceiver != null )
		{
			networkReceiver.unregist();
		}
		return super.quit();
	}

	private CommandE packGetFriendListCommandE()
	{

		CommandE e = InternetComponent.packA_CommonExpCommandE_ToServer( 
				EventDefine.GET_FRIEND_LIST_REQ , 
				InternetComponent.WEBSITE_ADDRESS_GET_FRIEND_LIST 
				);
		
        
        return e;

	}
	
	
	
	public void FriendBasicInfoChange( friendMemberDataBasic fmdb , int mask )
	{
		
		//send a message to server : notify a friend member'sdata changed
		updateFriendInfo( fmdb );

		//modify friend list view
		if( PeopleActivity.getInstance() != null )
		{
			PeopleActivity.getInstance().update();
		}
		
		dataManager.getFrilendList().updateDataToDb(mContext);
		

	}
	
	public void FriendsHasBeenRemoved( friendMemberData fmd )
	{
		//send a message to server
		deleteA_Friend(fmd);
		
		//modify friend list view
		if( PeopleActivity.getInstance() != null )
		{
			PeopleActivity.getInstance().update();
		}
		
		dataManager.getFrilendList().updateDataToDb(mContext);

	}
	
	//COMMON API
	
	/* add a friend */
	static public void addA_Friend( String phoneNumner ,String attachMentContext )
	{
		CommandE e = InternetComponent.packA_CommonExpCommandE_ToServer( 
				EventDefine.ADD_A_FRIEND_REQ , 
				InternetComponent.WEBSITE_ADDRESS_ADD_A_FRIEND_REQ 
				);
		e.AddAProperty(new Property("friend_mobile",phoneNumner ) );
		e.AddAProperty(new Property("attament",attachMentContext ) );
		
		Message m = MainControl.getInstance().handler.obtainMessage();
		m.obj = e;
		MainControl.getInstance().handler.sendMessage(m);
	}

	static public void registReq( String Id , String passWord )
	{
		Message m = MainControl.getInstance().handler.obtainMessage();

		CommandE e = InternetComponent.packA_CommonExpCommandE_ToServer(EventDefine.REGIST_REQ, InternetComponent.WEBSITE_ADDRESS_REGIST_REQ);

		e.AddAProperty(new Property("mobile",Id ) );
		e.AddAProperty(new Property("password",passWord ) );
		e.AddAProperty(new Property("confirmpass",passWord ) );
		e.AddAProperty(new Property("imsi",dataManager.self.getImsi() ) );
		e.AddAProperty(new Property("nick_name","小迷糊" ) );
		
		m.obj = e;
		MainControl.getInstance().handler.sendMessage(m);
		
		return ;
		
	}
	
	//result 1 : agree
	//result 0 :disagree
	static public void addA_FriendConfirm( String result , String targetUser  )
	{
		CommandE e = InternetComponent.packA_CommonExpCommandE_ToServer( 
				EventDefine.ADD_A_FRIEND_ANSWER_REQ , 
				InternetComponent.WEBSITE_ADDRESS_ADD_A_FRIEND_ANSWER_REQ 
				);
		
		e.AddAProperty(new Property("nok",result ) );
		e.AddAProperty(new Property("friend_mobile",targetUser ) );

		Message m = MainControl.getInstance().handler.obtainMessage();
		m.obj = e;
		MainControl.getInstance().handler.sendMessage(m);
	}
	
	static public void searchFirendOrCircle(String search_str) {

		CommandE e = InternetComponent.packA_CommonExpCommandE_ToServer( 
				EventDefine.SEARCH_FRIEND_OR_CIRCLE_REQ , 
				InternetComponent.WEBSITE_SEARCH_FRIEND_OR_CIRCLE 
				);
		e.AddAProperty(new Property("search_str",search_str ) );

		Message m = MainControl.getInstance().handler.obtainMessage();
		m.obj = e;   //
        
		MainControl.getInstance().handler.sendMessage(m);

	}
	
	static public void updateFriendInfo( friendMemberDataBasic fmdBasic ) {

		CommandE e = InternetComponent.packA_CommonExpCommandE_ToServer( 
					EventDefine.UPDATE_FRIEND_INFORMATION_REQ , 
					InternetComponent.WEBSITE_ADDRESS_UPDATE_FRIEND 
					);
		ObjectConvertTool.friendMemberDataPackToCommandE(fmdBasic,e);
		
		Message m = MainControl.getInstance().handler.obtainMessage();
		m.obj = e;   //
        
		MainControl.getInstance().handler.sendMessage(m);

	}

	static public void deleteA_Friend( friendMemberData fmd ) {

		CommandE e = InternetComponent.packA_CommonExpCommandE_ToServer( 
				EventDefine.DELETE_FRIEND_REQ , 
				InternetComponent.WEBSITE_ADDRESS_DELETE_FRIEND 
				);
		ObjectConvertTool.friendMemberDataPackToCommandE(fmd.basic,e);
	
		
		Message m = MainControl.getInstance().handler.obtainMessage();
		m.obj = e;   //
        
		MainControl.getInstance().handler.sendMessage(m);

	}

	static public void sendMessageToServer( ZdnMessage sendMsg ,String targetTo ) {

		
			addMessageToFriendData(sendMsg);
			
			ExpCommandE e = InternetComponent.packA_CommonExpCommandE_ToServer( 
					EventDefine.SEND_MESSAGE_REQ , 
					InternetComponent.WEBSITE_ADDRESS_SEND_TIP 
					);
			e.setUserData( sendMsg );
			
			ObjectConvertTool.messagePackToCommandForSendToServer(sendMsg, e, targetTo );
		
			Message m = MainControl.getInstance().handler.obtainMessage();
			m.obj = e;   //
	        
			MainControl.getInstance().handler.sendMessage(m);
		}
	
	static public void getMessageToServer( String from , String msgId ) {

		ExpCommandE e = InternetComponent.packA_CommonExpCommandE_ToServer( 
				EventDefine.GET_MESSAGE_REQ , 
				InternetComponent.WEBSITE_ADDRESS_GET_TIP 
				);
		e.AddAProperty(new Property("friend_mobile", from ));
		e.AddAProperty(new Property("mesg_id", msgId ));
		
		
		Message m = MainControl.getInstance().handler.obtainMessage();
		m.obj = e;   //
        
		MainControl.getInstance().handler.sendMessage(m);
	}

}