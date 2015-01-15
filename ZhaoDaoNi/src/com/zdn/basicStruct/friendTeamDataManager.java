package com.zdn.basicStruct;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Context;

import com.adn.db.DBManager;
import com.adn.db.DBManager.MemberInfo;
import com.zdn.util.ImgUtil;

public class friendTeamDataManager {

	List<friendTeamData> Teams = new ArrayList<friendTeamData>();
	
	public List<friendTeamData> getFriendTeamDataList() { return this.Teams; }
	
	public void addA_FriendTeam( friendTeamData ftd )
	{
		
	}
	
	public void deleteA_FriendTeam( String teamName )
	{
		
	}
	public void addA_FriendMemberData( String teamName , String memberName , String PhoneNumber , String pictureAddress )
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
			ftd.member = new ArrayList<friendMemberData>();
			Teams.add(ftd);
		}
		
		friendMemberData fmd = null;
		for( i = 0 ; i < ftd.member.size() ; i++ )
		{
			if(memberName.equals( ftd.member.get(i).memberName ) )
			{
				fmd = ftd.member.get(i);
				break;
			}
		}
		
		if( null == fmd )
		{
			fmd = new friendMemberData();
			fmd.memberName = memberName;
			ftd.member.add(fmd);
		
		}
		
		fmd.phoneNumber = PhoneNumber;
		fmd.pictureAddress = pictureAddress;
		
		fmd.picture = ImgUtil.getInstance().loadBitmapFromCache(fmd.pictureAddress);
		
	}
	public void deleteA_FriendMemberData( String friendMemberName )
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
		//friendTeamDataManager updateTeams = new friendTeamDataManager();
		
		DBManager dbm = new DBManager( context );
		
		ArrayList<MemberInfo> miList = dbm.searchAllData();
		
		
		for( int i = 0 ; i < miList.size() ; i++ )
		{
			MemberInfo dbMi = miList.get(i);
			
			addA_FriendMemberData(dbMi.teamName, dbMi.memberName, "", dbMi.pictureAddress );
				
		}
		dbm.closeDB();

	}
	
	public void updateDataToDb( Context context )
	{
		DBManager dbm = new DBManager( context );
		dbm.clearData();
		
		for( int i = 0 ; i < getTeamNum(); i++  )
		{
			
			for( int j = 0 ; j < getMemberNumInTeam(i) ; j++ )
			{
				friendMemberData fmd = getMemberData(i, j);
				//phone numner no handle
				dbm.add( 0 , getTeamData(i).teamName ,fmd.memberName, fmd.phoneNumber , fmd.pictureAddress );
				
			}
			
			
		}
		dbm.closeDB();
	}
	
	public void cloneToAnother( friendTeamDataManager another )
	{
		another.Teams.clear();
		Collections.copy(this.Teams, another.Teams );;

	}
}
