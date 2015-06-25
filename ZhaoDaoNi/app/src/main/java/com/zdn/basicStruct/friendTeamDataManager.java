package com.zdn.basicStruct;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.content.Context;

import com.zdn.chat.ZdnMessage;
import com.zdn.db.DBHelper;
import com.zdn.db.DBManager;

public class friendTeamDataManager {

	List<friendTeamData> Teams = new ArrayList<friendTeamData>();
	//regist a db name "friendMemberDataBasic"

	public List<friendTeamData> getFriendTeamDataList() { return this.Teams; }

	public List<friendMemberData>	allFriendMemberAccordChatTime = new ArrayList<friendMemberData>();;

    private List<friendsMemberChange> fmcList = new ArrayList();

    public void registFriendMemberChangeListener( friendsMemberChange fmc )
    {
        fmcList.add( fmc );

        //现有的朋友发送给listener
        for( friendMemberData fmd : allFriendMemberAccordChatTime )
        {
            fmc.addA_Friend( fmd );
        }


    }

    public void unRegistFriendMemberChangeListener( friendsMemberChange fmc )
    {

        for( int i = 0 ; i < fmcList.size();i++ ) {
            if (fmcList.get(i) == fmc)
            {
                fmcList.remove(i);
                break;
            }
        }
    }
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
		
		friendMemberData f = ftd.getFriendMemberData(fmd.basic.phoneNumber);
		if(f == null )
		{
			ftd.addFriendMemberData(fmd);
		}
		else
		{
			f.clone(fmd);
		}
        reConstructRecentChatTeamAccordingTime();

        //notify all of listener
        for( friendsMemberChange fmc :fmcList )
        {
            fmc.addA_Friend( fmd );
        }
	}
	
	public void removeA_Friend( String teamName , String phoneNumber )
	{
		friendTeamData ftd = getFriendTeamData(teamName);
        friendMemberData fmd = null;
        if(ftd!= null)
		{
            fmd = ftd.removeA_Frined(phoneNumber);

		}
        reConstructRecentChatTeamAccordingTime();

        //notify all of listener
        if( ftd != null )
        {
            for( friendsMemberChange fmc :fmcList )
            {
                fmc.removeA_Friend( fmd );
            }
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
        reConstructRecentChatTeamAccordingTime();
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
			fmd = new friendMemberData(PhoneNumber); // PhoneNumber as tag
			fmd.basic.memberName = memberName;
			ftd.member.add(fmd);
		
		}
		
		fmd.basic.teamName = teamName;
		fmd.basic.nickName = nickName;
		fmd.basic.comment  = comment;
		fmd.basic.phoneNumber = PhoneNumber;
		fmd.basic.pictureAddress = pictureAddress;
		
		//fmd.rebuildFriendMemberData();
        reConstructRecentChatTeamAccordingTime();
		
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
	
	public friendMemberData getMemberDataByPhoneNumber( String phoneNumber )
	{
		
		for( int i = 0 ; i < Teams.size() ; i++ )
		{
			friendMemberData fmd = Teams.get(i).getFriendMemberData(phoneNumber);
			
			if(fmd != null )
			{
				return fmd;
			}
		}
		
		return null;
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
			//fmd.rebuildFriendMemberData(); // first rebuilt it
			addA_FriendMemberData( fmd );
				
		}
		getDbHelper.closeDB();

	}
	
	public void updateDataToDb( Context context )
	{
		DBHelper getDbHelper = DBManager.GetDbHelper(friendMemberDataBasic.class);
		
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
		Collections.copy(this.Teams, another.Teams);

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

    public  List<friendMemberData> getAllRecnetChatList()
    {
        return reConstructRecentChatTeamAccordingTime();
    }
	public List<friendMemberData> reConstructRecentChatTeamAccordingTime(  )
	{
        if( allFriendMemberAccordChatTime.size() == 0 )
        {
            friendTeamData ftd = new friendTeamData();
            List<friendMemberData> allFriend = new ArrayList<friendMemberData>();

            int teamCount = this.getTeamNum();

            for (int i = 0; i < teamCount; i++) {
                allFriend.addAll(this.getTeamData(i).member);
            }
            Collections.sort(allFriend, new Comparator<friendMemberData>() {
                @Override
                public int compare(friendMemberData lhs, friendMemberData rhs) {
                    String date1 = "";

                    List<ZdnMessage> messageList = lhs.getMessageList();
                    if (messageList.size() > 0) {
                        date1 = messageList.get(messageList.size() - 1).getTimeString();
                    }

                    String date2 = "";

                    messageList = rhs.getMessageList();
                    if (messageList.size() > 0) {
                        date2 = messageList.get(messageList.size() - 1).getTimeString();
                    }

                    return date1.compareTo(date2);

                }


            });

            allFriendMemberAccordChatTime = allFriend;
        }

		return allFriendMemberAccordChatTime;
	}

    public void updateThelastChatMember(  friendMemberData fmd )
    {
        for( int i = 0 ; i < allFriendMemberAccordChatTime.size() ; i++ )
        {
            if( allFriendMemberAccordChatTime.get(i) == fmd )
            {
                allFriendMemberAccordChatTime.remove(i);
                break;
            }
        }
        allFriendMemberAccordChatTime.add( 0 , fmd );
    }

    public interface friendsMemberChange
    {
        public void addA_Friend(  friendMemberData fmd );
        public void removeA_Friend(  friendMemberData fmd );
    }
}
