package com.zdn.basicStruct;

//define member struct

public class friendMemberDataBasic {
	protected String   teamName; 
	protected String 	memberName;
	protected String   phoneNumber;  // todo
	protected String   nickName;
	protected String   comment;
	protected String   pictureAddress;
	
	// register a data basic data change listener

	public void setTeamName( String teamName )
	{
		this.teamName = teamName;
	}
	
	public String getTeamName( )
	{
		return this.teamName;
	}

	public void setMemberName( String memberName )
	{
		this.memberName = memberName;
	}
	
	public String getMemberName( )
	{
		return this.memberName;
	}
	
	public void setPhoneNumber( String phoneNumber )
	{
		this.phoneNumber = phoneNumber;
	}
	
	public String getPhoneNumber( )
	{
		return this.phoneNumber;
	}
	
	public void setNickName( String nickName )
	{
		this.nickName = nickName;
	}
	
	public String getNickName( )
	{
		return this.nickName;
	}
	
	public void setComment( String comment )
	{
		this.comment = comment;
	}
	
	public String getComment( )
	{
		return this.comment;
	}
	
	public void setPictureAddress( String pictureAddress )
	{
		this.pictureAddress = pictureAddress;
	}
	
	public String getPictureAddress( )
	{
		return this.pictureAddress;
	}
}
