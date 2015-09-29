package com.zdn.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.zdn.basicStruct.SelfMobileData;
import com.zdn.basicStruct.friendTeamDataManager;
import com.zdn.basicStruct.timeSpaceBallManager;

public class dataManager {

	static friendTeamDataManager   allFriend = null;  //friend list
	static public Context mainContext = null;
	static public SelfMobileData self = null; // myself info
	static timeSpaceBallManager allBalls = null;
	
	public dataManager() {
		// TODO Auto-generated constructor stub
	}
	
	static public void init( Context context )
	{
		mainContext = context;
		allFriend = new friendTeamDataManager();
		allFriend.constructTeamInfoFromDb( context );
		self = new SelfMobileData();
		allBalls = new timeSpaceBallManager();

		self.init(context);
	}
	
	static public friendTeamDataManager getFrilendList()
	{
		return allFriend;
	}

	static public timeSpaceBallManager getAllBallsList() { return allBalls; }

	static public void updateFriendListFromServer( int update_type , JSONArray jason_friendList ,Context context )
	{
		if( jason_friendList == null ) return;
		
		if( 1 == update_type  || ( allFriend == null) )
		{
			allFriend = new friendTeamDataManager();
		}
		
			
		for( int i = 0 ; i < jason_friendList.length() ; i++ )
		{
			JSONObject obj;
			try {
				obj = (JSONObject)(jason_friendList.get(i));
			
				String teamName = obj.getString("group");
				String memberName = obj.getString("nickname");
				String phoneNumber = obj.getString("mobile");
				String pictureAddress = obj.getString("avatar_url");
				allFriend.addA_FriendMemberData( teamName, memberName, memberName , "" , phoneNumber , pictureAddress );
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}				
		allFriend.updateDataToDb( context );

	}
}
