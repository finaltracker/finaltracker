package com.zdn.interf;

import android.os.Message;

import com.zdn.CommandParser.CommandE;

import java.io.File;

public interface ServerInterfaceCmd {

	/* 流程管理 */
	public int registReq( CommandE e  );
	
	//是否已经注册了
	public void isRegist( String imsi );
	
	public void login( CommandE e );
	//增加一个好友
	public int addA_Friend( CommandE e );
	//好友加我的回复
	public void friendAddMeAnswer( CommandE e );
	//获取好友列表
	public void getFriendList( CommandE e );
	//更新好友信息
	public void updateFriendInfomation( CommandE e);
	
	//删除一个好友
	public void deleteFriend( CommandE e );
	
	//向服务器发送自己的GPS信息
	public void updateGpsInfo( String phoneNumber , int longitude, int latitude );
	
	//向服务器请求friend的GPS信息
	public void requestFriendGpsInfo( String phoneNumber , int longitude, int latitude );
	
	public void searchFirendOrCircle(  CommandE e );

	public void sendTip( CommandE e ,Message m );
	
	public void getTip( CommandE e );

	public void uploadFile( CommandE e );

    //向服务器更新自己的位置信息
	public void locationUpdate( CommandE e );

    //获取好友的位置信息
	public void locationGet( CommandE e );

	public void downLoadAudio( CommandE e );

	public void startBallGame( CommandE e );

	public void getBallLocation( CommandE e );

	public void getCurrentBallPosition( CommandE e );

	public void getBallAll( CommandE e );

	public void getProfile( CommandE e );
}
