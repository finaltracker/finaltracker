package com.zdn.basicStruct;

import java.util.ArrayList;
import java.util.List;

import com.zdn.logic.MainControl;
import com.zdn.data.dataManager;

public class friendTeamData {
	public String   teamName;
	//private int 	index ;
	public List<friendMemberData>	member;
	//public List<friendMemberDataExt>	memberExt;


	public friendTeamData( )
	{
		//index = 0;
		member = new ArrayList<friendMemberData>();
	}


	public void addFriendMemberData( friendMemberData one )
	{
		member.add(one);
	}
	

    public int findFriendMember( String phoneNumber )
    {
        for( int i = 0 ; i < member.size() ; i++ )
        {
            if(member.get(i).basic.phoneNumber.equals( phoneNumber ))
            {
                return i;
            }
        }

        return -1;
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

	public void removeA_Frined( String phoneNumber  )
	{
		friendMemberData fmd = getFriendMemberData ( phoneNumber );

		if( fmd != null )
		{
			removeA_Frined(fmd);
		}
	}

	public void removeA_Frined( friendMemberData fmd )
	{
		for( int i = 0 ; i < member.size() ; i++ )
		{
			if(member.get(i).equals(fmd))
			{
				friendMemberData fmdRemove = member.get(i);
				member.remove(i);
				deletedNotifyToMainControl( fmdRemove );
				
				break;
			}
		}
		
		dataManager.getFrilendList().RebuiltTeam( fmd.basic.getTeamName() );
		
		return ;
	}
	
	private void deletedNotifyToMainControl( friendMemberData fmd )
	{
		MainControl mc = MainControl.getInstance();
		
		if(mc != null )
		{
			mc.FriendsHasBeenRemoved( fmd );
		}
	}
}