package com.zdn.activity;


import java.util.List;

import com.zdn.R;
import com.zdn.adapter.searchForAddFriendAdapter;
import com.zdn.basicStruct.friendTeamData;

import android.app.Activity;
import android.os.Bundle;

public class searchForAddFriendActivity extends Activity {
	
	private searchForAddFriendAdapter sfafAdapter ;
	List<friendTeamData> teams = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_for_add_friend );
		
		init();
	}
	
	private void init() 
	{
		sfafAdapter = new searchForAddFriendAdapter( this , null );
	}
	
	private void update()
	{
		sfafAdapter.updateListView(teams);
	}
}
