package com.zdn.basicStruct;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Context;

import com.adn.db.DBManager;

public class friendTeamDataManager {

	List<friendTeamData> Teams = new ArrayList<friendTeamData>();
	
	public List<friendTeamData> getFriendTeamDataList() { return this.Teams; }
	
	
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
	
	public void deleteA_FriendTeam( String teamName )
	{
		
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
	public void deleteA_FriendMemberData( String phoneNumber )
	{
		
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
		if(teamIndex <= Teams.size() )
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
	
		DBManager dbm = new DBManager( context );
		
		ArrayList<friendMemberData> miList = dbm.searchAllData();
		
		
		for( int i = 0 ; i < miList.size() ; i++ )
		{
			friendMemberData dbMi = miList.get(i);
			dbMi.rebuildFriendMemberData(); // first rebuilt it
			addA_FriendMemberData( dbMi );
				
		}
		dbm.closeDB();

	}
	
	public void updateDataToDb( Context context )
	{
		DBManager dbm = new DBManager( context );
		dbm.clearData();
		
		for( int i = 0 ; i < getTeamNum(); i++  )
		{
			dbm.add(  Teams.get(i).member );
			
		}
		dbm.closeDB();
	}
	
	public void cloneToAnother( friendTeamDataManager another )
	{
		another.Teams.clear();
		Collections.copy(this.Teams, another.Teams );;

	}
}
