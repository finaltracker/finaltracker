package com.zdn.interf;

import com.zdn.CommandParser.CommandE;

public interface ServerInterfaceCmd {

	/* 流程管理 */
	public int registReq( CommandE e  );
	
	//是否已经注册了
	public void isRegist( String imsi );
	
	//增加一个好友
	public int addA_Friend( CommandE e );
	//好友加我的回复
	public void friendAddMeAnswer( CommandE e );
	//获取好友列表
	public void getFriendList( String imsi , int mobile_friend_version );
	//更新好友信息
	public void updateFriendInfomation( CommandE e);
	//向服务器发送自己的GPS信息
	public void updateGpsInfo( String phoneNumber , int longitude, int latitude );
	
	//向服务器请求friend的GPS信息
	public void requestFriendGpsInfo( String phoneNumber , int longitude, int latitude );
	
	public void searchFirendOrCircle(  CommandE e );

	
}
