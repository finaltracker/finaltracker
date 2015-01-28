package com.zdn.basicStruct;

import com.zdn.activity.MainControl;


//define member struct

public class friendMemberDataBasic {
	
	

	//for reflect: only string type to be define, all public field will be save to db

	// public for java reflect
	public String   teamName; 
	public String 	memberName;
	public String   phoneNumber;  // todo
	public String   nickName;
	public String   comment;
	public String   pictureAddress;
	
	
	
	public void setTeamName( String teamName )
	{
		this.teamName = teamName;
		
		notifyToMainControl( friendMemberData.TEAM_NAME );
	}
	
	public String getTeamName( )
	{
		return this.teamName;
	}

	public void setMemberName( String memberName )
	{
		this.memberName = memberName;
		notifyToMainControl(friendMemberData.MEMBER_NAME );
	}
	
	public String getMemberName( )
	{
		return this.memberName;
	}
	
	public void setPhoneNumber( String phoneNumber )
	{
		this.phoneNumber = phoneNumber;
		notifyToMainControl( friendMemberData.PHONE_NUMBER );
	}
	
	public String getPhoneNumber( )
	{
		return this.phoneNumber;
	}
	
	public void setNickName( String nickName )
	{
		this.nickName = nickName;
		notifyToMainControl( friendMemberData.NICK_NAME );
	}
	
	public String getNickName( )
	{
		return this.nickName;
	}
	
	public void setComment( String comment )
	{
		this.comment = comment;
		notifyToMainControl( friendMemberData.COMMENT );
	}
	
	public String getComment( )
	{
		return this.comment;
	}
	
	public void setPictureAddress( String pictureAddress )
	{
		this.pictureAddress = pictureAddress;
		notifyToMainControl( friendMemberData.PICTURE_ADDRESS );
	}
	
	
	public String getPictureAddress( )
	{
		return this.pictureAddress;
	}
	
	private void notifyToMainControl( int mask )
	{
		MainControl mc = MainControl.getInstance();
		
		if(mc != null )
		{
			mc.FriendBasicInfoChange( this , mask );
		}
	}
	
}
