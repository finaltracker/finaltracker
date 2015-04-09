package com.zdn.basicStruct;

import com.zdn.logic.MainControl;
import com.zdn.data.dataManager;


//define member struct

public class friendMemberDataBasic {
	
	

	//for reflect: only string type to be define, all public field will be save to db

	// public for java reflect
	public String   tag;  // unique tag
	public String   teamName; 
	public String 	memberName;
	public String   phoneNumber;  // todo
	public String   nickName;
	public String   comment;
	public String   pictureAddress;
	
	
	public void setTag( String tag )
    {
        this.tag = tag;
    }

    public String getTag( )
	{
		return this.tag;
	}
    
	public void setTeamName( String teamName )
	{
		this.teamName = teamName;
		
		dataChangeNotifyToMainControl( friendMemberData.TEAM_NAME );
	}
	
	public String getTeamName( )
	{
		return this.teamName;
	}

	public void setMemberName( String memberName )
	{
		this.memberName = memberName;
		dataChangeNotifyToMainControl(friendMemberData.MEMBER_NAME );
	}
	
	public String getMemberName( )
	{
		return this.memberName;
	}
	
	public void setPhoneNumber( String phoneNumber )
	{
		this.phoneNumber = phoneNumber;
		dataChangeNotifyToMainControl( friendMemberData.PHONE_NUMBER );
	}
	
	public String getPhoneNumber( )
	{
		return this.phoneNumber;
	}
	
	public void setNickName( String nickName )
	{
		this.nickName = nickName;
		dataChangeNotifyToMainControl( friendMemberData.NICK_NAME );
	}
	
	public String getNickName( )
	{
		return this.nickName;
	}
	
	public void setComment( String comment )
	{
		this.comment = comment;
		dataChangeNotifyToMainControl( friendMemberData.COMMENT );
	}
	
	public String getComment( )
	{
		return this.comment;
	}
	
	public void setPictureAddress( String pictureAddress )
	{
		this.pictureAddress = pictureAddress;
		dataChangeNotifyToMainControl( friendMemberData.PICTURE_ADDRESS );
	}
	
	
	public String getPictureAddress( )
	{
		return this.pictureAddress;
	}
	
	private void dataChangeNotifyToMainControl( int mask )
	{
		MainControl mc = MainControl.getInstance();
		
		if(mc != null )
		{
			mc.FriendBasicInfoChange( this , mask );
		}
		dataManager.self.preferencesPara.saveFriendListVersion( dataManager.self.preferencesPara.getFriendListVersion()+1 );

		//rebuit friendTeam
		if( ( mask & friendMemberData.TEAM_NAME )!= 0 )
		{
			dataManager.getFrilendList().RebuiltTeam( getTeamName() );
		}

		
	}
	
	
	
	
	
}
