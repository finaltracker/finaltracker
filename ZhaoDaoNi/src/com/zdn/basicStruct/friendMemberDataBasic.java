package com.zdn.basicStruct;

import com.zdn.activity.MainControl;


//define member struct

public class friendMemberDataBasic {
	
	
	static public int   	TEAM_NAME 		= 0x01; 
	static public int 		MEMBER_NAME		= 0x02;
	static public int   	PHONE_NUMBER	= 0x04; 
	static public int   	NICK_NAME		= 0x08;
	static public int   	COMMENT			= 0x10;
	static public int   	PICTURE_ADDRESS	= 0x20;
	
	
	protected String   teamName; 
	protected String 	memberName;
	protected String   phoneNumber;  // todo
	protected String   nickName;
	protected String   comment;
	protected String   pictureAddress;
	
	
	
	public void setTeamName( String teamName )
	{
		this.teamName = teamName;
		
		notifyToMainControl( TEAM_NAME );
	}
	
	public String getTeamName( )
	{
		return this.teamName;
	}

	public void setMemberName( String memberName )
	{
		this.memberName = memberName;
		notifyToMainControl( MEMBER_NAME );
	}
	
	public String getMemberName( )
	{
		return this.memberName;
	}
	
	public void setPhoneNumber( String phoneNumber )
	{
		this.phoneNumber = phoneNumber;
		notifyToMainControl( PHONE_NUMBER );
	}
	
	public String getPhoneNumber( )
	{
		return this.phoneNumber;
	}
	
	public void setNickName( String nickName )
	{
		this.nickName = nickName;
		notifyToMainControl( NICK_NAME );
	}
	
	public String getNickName( )
	{
		return this.nickName;
	}
	
	public void setComment( String comment )
	{
		this.comment = comment;
		notifyToMainControl( COMMENT );
	}
	
	public String getComment( )
	{
		return this.comment;
	}
	
	public void setPictureAddress( String pictureAddress )
	{
		this.pictureAddress = pictureAddress;
		notifyToMainControl( PICTURE_ADDRESS );
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
