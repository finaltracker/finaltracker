package com.logic;

import CommandParser.CommandE;

public interface ServerInterface {

	/* 流程管理 */
	public int registReq( String phoneNumber , String nickName  );
	
	//是否已经注册了
	public void isRegist( String imsi );
	
	//增加一个好友
	public int addA_Friend( String PhoneNumber , String comment );
	//好友加我的回复
	public void friendAddMeAnswer( int result );
	
	public CommandE requestFriendList();
	
	//向服务器发送自己的GPS信息
	public void updateGpsInfo( String phoneNumber , int longitude, int latitude );
	
	//向服务器请求friend的GPS信息
	public void requestFriendGpsInfo( String phoneNumber , int longitude, int latitude );

	
	/* ServerInterface Call back interface */
	/* String message : 增加朋友的时候，添加的信息*/
	public void friendAddMe( String callerPhoneNumber , String callerNickName , int validPeriod , String message );

	public void addA_FriendAnswer( int result );

}