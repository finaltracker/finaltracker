package com.zdn.basicStruct;

import com.zdn.util.ImgUtil;

import android.graphics.Bitmap;

//define member struct

public class friendMemberData {

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