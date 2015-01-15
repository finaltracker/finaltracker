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

public class searchFriendResultForAddActivity extends Activity {
	
	public static final int UPDATE_VIEW_FROM_REMOT = 0;
	private searchFriendResultForAddAdapter sfafAdapter ;
	friendTeamDataManager teams = null;
	
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
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_for_add_friend );
		
		init();
	}
	
	private void init() 
	{
		sfafAdapter = new searchFriendResultForAddAdapter( this ,teams );
	}
 
	private void updateListFromServer( JSONArray jason_friendList )
	{
		teams = new friendTeamDataManager();

		for( int i = 0 ; i < jason_friendList.length() ; i++ )
		{
			JSONObject obj;
			try {
				obj = (JSONObject)(jason_friendList.get(i));
			
				String memberName = obj.getString("nickname");
				String phoneNumber = obj.getString("mobile");
				String pictureAddress = obj.getString("avatar_url");
				teams.addA_FriendMemberData( "ÕÒµ½µÄºÃÓÑ", memberName, phoneNumber , pictureAddress );
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
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
