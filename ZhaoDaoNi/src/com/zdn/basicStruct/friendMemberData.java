package com.zdn.basicStruct;

import java.util.ArrayList;
import java.util.List;

import com.adn.db.DBHelper;
import com.adn.db.DBManager;
import com.zdn.chat.ZdnMessage;
import com.zdn.data.dataManager;
import com.zdn.util.ImgUtil;

import android.content.Context;
import android.graphics.Bitmap;

//define member struct

public class friendMemberData {

	//define here cause: friendMemberDataBasic will use reflect ,so only string type to be define
	static public int   	TEAM_NAME 		= 0x01; 
	static public int 		MEMBER_NAME		= 0x02;
	static public int   	PHONE_NUMBER	= 0x04; 
	static public int   	NICK_NAME		= 0x08;
	static public int   	COMMENT			= 0x10;
	static public int   	PICTURE_ADDRESS	= 0x20;
	
	public friendMemberDataBasic basic;
	public Bitmap	picture;
	private List<ZdnMessage> message = null;


	public friendMemberData( String tag )
	{
		message = new ArrayList<ZdnMessage>();
		basic = new friendMemberDataBasic();
		picture = null;
		basic.setTag(tag);
		
		constructMessageFromDb( dataManager.mainContext );
	}
	
	public void constructMessageFromDb( Context context )
	{
	
		DBHelper getDbHelper = DBManager.GetDbHelper( ZdnMessage.class );
		
		ArrayList<Object> miList = getDbHelper.searchData("groupTag" , basic.tag );
		
		
		
		for( int i = 0 ; i < miList.size() ; i++ )
		{
			ZdnMessage m = (ZdnMessage)(miList.get(i));
			
			message.add(m);
				
		}
		getDbHelper.closeDB();

	}
	public friendMemberData(friendMemberDataBasic basic )
	{
		message = new ArrayList<ZdnMessage>();
		this.basic =basic;
		picture = null;
	}
	
	
	//�����ݿ�õ�basic��Ҫ�����������ʱ���ô˺���
	public void rebuildFriendMemberData()
	{
		picture = null;
		if(basic.pictureAddress != null && !basic.pictureAddress.isEmpty() )
		{
			picture = ImgUtil.getInstance().loadBitmapFromCache(basic.pictureAddress);
		}
	}
	public void clone( friendMemberData one )
	{
		
		picture = one.picture;
		basic = one.basic;
	}
	
}