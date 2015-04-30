package com.zdn.activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zdn.R;
import com.zdn.adapter.searchFriendResultForAddAdapter;
import com.zdn.basicStruct.friendTeamDataManager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ListView;

public class searchFriendResultForAddActivity extends zdnBasicActivity {
	
	public static final int UPDATE_VIEW_FROM_REMOT = 0;
	private searchFriendResultForAddAdapter sfafAdapter ;
	friendTeamDataManager teams = null;
	ListView         search_for_add_friend_list = null;
	
	static public searchFriendResultForAddActivity me;
	
	public searchFriendResultForAddActivity()
	{
		teams = new friendTeamDataManager();
		me = this;
	}
	
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		me = null;
		super.finalize();
	}

	static public searchFriendResultForAddActivity getInstance() { return me; }
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.search_for_add_friend );
		
		init();
		super.onCreate(savedInstanceState);
	}
	
	private void init() 
	{
		search_for_add_friend_list = (ListView)findViewById(R.id.search_for_add_friend_list );
		sfafAdapter = new searchFriendResultForAddAdapter( this ,teams );
		search_for_add_friend_list.setAdapter( sfafAdapter );
	}
 
	private void updateListFromServer( JSONArray jason_friendList )
	{
		teams = new friendTeamDataManager();

		teams.addA_FriendMemberData( "找到的好友", "找到的好友", null , null , null ,null  );
		
		for( int i = 0 ; i < jason_friendList.length() ; i++ )
		{
			JSONObject obj;
			String memberName="";
			String nickName="";
			String comment="";
			String phoneNumber="";
			String pictureAddress = null;
			try {
				obj = (JSONObject)(jason_friendList.get(i));
			
				memberName = obj.getString("nickname");
				nickName = memberName;
				phoneNumber = obj.getString("mobile");
				pictureAddress = obj.getString("avatar_url");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			teams.addA_FriendMemberData( "找到的好友", memberName, nickName , comment , phoneNumber , pictureAddress );
			
		}				
		sfafAdapter.updateListView(teams);

		
	}
	
	
	@SuppressLint("HandlerLeak")
	public Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if ( msg.what == UPDATE_VIEW_FROM_REMOT ) {
				//	
				if(searchFriendResultForAddActivity.this.isDestroyed() )
				{
					Log.e( searchFriendResultForAddActivity.this.getClass().getSimpleName() , "searchFriendResultForAddActivity is destroyed!" );
				}
				else
				{
					JSONObject jason_obj = (JSONObject)(msg.obj);
					
					JSONArray jason_friendList = null;
					JSONArray jason_CircleList = null;
					try {
	
						jason_friendList = jason_obj.getJSONArray("friends");
						jason_CircleList = jason_obj.getJSONArray("circle");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					if( jason_friendList != null )
					{
						updateListFromServer( jason_friendList );
					}
				}
			} 
		}
	};

}
