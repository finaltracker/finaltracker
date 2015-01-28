package com.zdn.basicStruct;

import com.zdn.util.ImgUtil;

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
	


	public friendMemberData()
	{
		basic = new friendMemberDataBasic();
		picture = null;
	}
	
	//从数据库得到basic后要填充其它内容时调用此函数
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