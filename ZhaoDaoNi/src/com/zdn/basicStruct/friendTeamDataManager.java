package com.zdn.basicStruct;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Context;

import com.adn.db.DBHelper;
import com.adn.db.DBManager;
import com.zdn.logic.MainControl;

public class friendTeamDataManager {

	List<friendTeamData> Teams = new ArrayList<friendTeamData>();
	//regist a db name "friendMemberDataBasic"

	public List<friendTeamData> getFriendTeamDataList() { return this.Teams; }
	
	//return index , -1 not find
	public int findAfriendTeam( String teamName  )
	{
		for( int i = 0 ; i < Teams.size() ; i++ )
		{
			if(Teams.get(i).teamName.equals(teamName))
			{
				return i;
			}
		}
		

		return -1;
		
		
	}
	public friendTeamData getFriendTeamData( String teamName )
	{
		friendTeamData ftd = null;
		
		for( int i = 0 ; i < Teams.size() ; i++ )
		{
			if(Teams.get(i).teamName.equals(teamName))
			{
				return Teams.get(i);
			}
		}
		return ftd;
	}
	
	
	public void addA_FriendMemberData( friendMemberData fmd )
	{
		friendTeamData ftd = null;

		ftd = getFriendTeamData(fmd.basic.teamName);
		
		if(ftd == null )
		{
			ftd = new friendTeamData();
			ftd.teamName = 	fmd.basic.teamName;
			Teams.add(ftd);
		}
		
		friendMemberData f = ftd.getFriendMemberData( fmd.basic.phoneNumber );
		if(f == null )
		{
			ftd.addFriendMemberData(fmd);
		}
		else
		{
			f.clone(fmd);
		}
	}
	
	public void removeA_Friend( String teamName , String phoneNumber )
	{
		friendTeamData ftd = getFriendTeamData( teamName );

		if(ftd!= null)
		{
			ftd.removeA_Frined( phoneNumber );
		}
	}
	
	public void deleteA_FriendTeam( String teamName )
	{

		for( int i = 0 ; i < Teams.size() ; i++ )
		{
			if(Teams.get(i).teamName.equals(teamName))
			{
				Teams.remove(i);
				i--;
			}
		}
	}



	public void addA_FriendMemberData( String teamName , String memberName , String nickName , String comment , String PhoneNumber , String pictureAddress )
	{
		
		friendTeamData ftd = null;
		int i = 0;
		for( i = 0 ; i < Teams.size() ; i++ )
		{
			if(teamName.equals( Teams.get(i).teamName) )
			{
				ftd = Teams.get(i);
				break;
			}
		}
		
		if( null == ftd )
		{ 
			ftd = new friendTeamData();
			ftd.teamName = teamName;
			Teams.add(ftd);
		}
		
		friendMemberData fmd = null;
		for( i = 0 ; i < ftd.member.size() ; i++ )
		{
			if(PhoneNumber.equals( ftd.member.get(i).basic.phoneNumber ) )
			{
				fmd = ftd.member.get(i);
				break;
			}
		}
		
		if( null == fmd )
		{
			fmd = new friendMemberData();
			fmd.basic.memberName = memberName;
			ftd.member.add(fmd);
		
		}
		
		fmd.basic.teamName = teamName;
		fmd.basic.nickName = nickName;
		fmd.basic.comment  = comment;
		fmd.basic.phoneNumber = PhoneNumber;
		fmd.basic.pictureAddress = pictureAddress;
		
		fmd.rebuildFriendMemberData();
		
	}
	
	
	public int getTeamNum()
	{
		return Teams.size();
	}
	
	public int getMemberNumInTeam( int teamIndex )
	{
		return Teams.get(teamIndex).member.size();
	}
	
	public friendTeamData getTeamData( int teamIndex )
	{
		if(teamIndex < Teams.size() )
		{
			return Teams.get(teamIndex);
		}
		else
		{
			return null;
		}
	}
	public friendMemberData getMemberData( int teamIndex , int memberIndex )
	{
		if(teamIndex >= Teams.size() )
		{
			return null;
		}else
		{
			if(memberIndex >= Teams.get(teamIndex).member.size() )
			{
				return null;
			}
		}
		return Teams.get(teamIndex).member.get(memberIndex);
	}
	
	public void constructTeamInfoFromDb( Context context )
	{
	
		DBHelper getDbHelper = DBManager.GetDbHelper( friendMemberDataBasic.class );
		
		ArrayList<Object> miList = getDbHelper.searchAllData();
		
		
		for( int i = 0 ; i < miList.size() ; i++ )
		{
			friendMemberDataBasic dbMi = (friendMemberDataBasic)(miList.get(i));
			friendMemberData fmd = new friendMemberData(dbMi);
			fmd.rebuildFriendMemberData(); // first rebuilt it
			addA_FriendMemberData( fmd );
				
		}
		getDbHelper.closeDB();

	}
	
	public void updateDataToDb( Context context )
	{
		DBHelper getDbHelper = DBManager.GetDbHelper( friendMemberDataBasic.class );
		
		getDbHelper.clearData();
		
		for( int i = 0 ; i < getTeamNum(); i++  )
		{
			for( int j = 0 ; j < Teams.get(i).member.size() ; j++ )
			{
				getDbHelper.add(  Teams.get(i).member.get(j).basic );
			}
			
		}
		getDbHelper.closeDB();
	}
	
	public void cloneToAnother( friendTeamDataManager another )
	{
		another.Teams.clear();
		Collections.copy(this.Teams, another.Teams );;

	}
	
	public void RebuiltTeam( String teamName )
	{
		friendTeamData ftd = getFriendTeamData( teamName );
		for( int i = 0 ; i < ftd.member.size() ; i++ )
		{
			friendMemberData fmd = ftd.member.get(i);
			if( fmd.basic.teamName.equals(teamName ) )
			{
				
			}
			else
			{
				addA_FriendMemberData(fmd); 
				ftd.member.remove(i);
				i--;
			}
		}
		
		if(ftd.member.size() == 0 )
		{
			deleteA_FriendTeam( teamName );
		}
			
	}
}
