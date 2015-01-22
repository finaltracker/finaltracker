package com.zdn.basicStruct;

import java.util.ArrayList;
import java.util.List;

public class friendTeamData {
	public String   teamName;
	public List<friendMemberData>	member;
	//public List<friendMemberDataExt>	memberExt;


	public friendTeamData()
	{
		member = new ArrayList<friendMemberData>();
	}
	
	public void addFriendMemberData( friendMemberData one )
	{
		member.add(one);
	}
	

	
	
	public friendMemberData getFriendMemberData ( String phoneNumber )
	{
		friendMemberData  fmd = null;
		
		for( int i = 0 ; i < member.size() ; i++ )
		{
			if(member.get(i).basic.phoneNumber.equals(phoneNumber))
			{
				return member.get(i);
			}
		}
		
		return fmd;
		
	}
}