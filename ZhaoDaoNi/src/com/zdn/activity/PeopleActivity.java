package com.zdn.activity;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.adn.db.DBManager;
import com.adn.db.DBManager.MemberInfo;
import com.zdn.AsyncTaskBase;
import com.zdn.R;
import com.zdn.sort.ClearEditText;
import com.zdn.adapter.FriendListAdapter;
import com.zdn.basicStruct.friendMemberData;
import com.zdn.basicStruct.friendTeamDataManager;
import com.zdn.view.FriendListView;
import com.zdn.view.LoadingView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

public class PeopleActivity extends Activity {
	//private Context this;
	//private View mBaseView;
	static public int UPDATE_VIEW_FROM_REMOT		= 1 ;
	private FriendListView mIphoneTreeView;
	private ClearEditText mSearchView;
	private FriendListAdapter mFriendListAdapter;
	static public PeopleActivity me;
	//private DBManager dbm;
	friendTeamDataManager   allFriend = null;
	
	
	PeopleActivity()
	{
		me = this;
	}
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		me = null;
		super.finalize();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_constact);
		//mBaseView = inflate(R.layout.fragment_constact, null);
		findView();
		init();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	static public PeopleActivity getInstance() { return me; }
	
	private void findView() {
		mSearchView=(ClearEditText) findViewById( R.id.ll_constact_serach );
		mIphoneTreeView = (FriendListView) findViewById( R.id.iphone_tree_view );
		
	}

	private void init() {
		
		

		mIphoneTreeView.setGroupIndicator(null);//set icon in front of group

		mFriendListAdapter = new FriendListAdapter(this, mIphoneTreeView,null);
		updateAdapterInit();
		mIphoneTreeView.setAdapter(mFriendListAdapter);

		mSearchView.addTextChangedListener(new TextWatcher() {

					@Override
					public void onTextChanged(CharSequence s, int start,
							int before, int count) {
						// 瑜版捁绶崗銉︻攱闁插矂娼伴惃鍕舵嫹?娑撹櫣鈹栭敍灞炬纯閺傞璐熼崢鐔告降閻ㄥ嫬鍨悰顭掔礉閸氾箑鍨稉楦跨箖濠娿倖鏆熼幑顔煎灙閿燂拷
						
						if( View.VISIBLE == mIphoneTreeView.getVisibility() )
						{
							//mIphoneTreeView.filterData(s.toString());
						}
						
					}

					@Override
					public void beforeTextChanged(CharSequence s, int start,
							int count, int after) {

					}

					@Override
					public void afterTextChanged(Editable s) {
						// TODO Auto-generated method stub
						
					}
				});
		
		new AsyncTaskLoading(null).execute(0);
	}

	private class AsyncTaskLoading extends AsyncTaskBase {
		public AsyncTaskLoading(LoadingView loadingView) {
			super(loadingView);
		}

		@Override
		protected Integer doInBackground(Integer... params) {
			int result = -1;

			result = 1;
			return result;
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			
			
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

	}

	
	
	public void updateAdapterInit()
	{
	/*
		String[] groups = { "我的好友", "家人", "123456", "S2S73", "S1S24",
				"S1S5", "亲戚" };
		String[][] children = {
				{ "宋慧", "章泽", "宋茜", "韩孝", "景甜", "刘亦", "康", "邓紫" },
				{ "宋慧", "章泽", "宋茜", "韩孝", "景甜", "刘亦", "康", "邓紫" },
				{ "宋慧", "章泽", "宋茜", "韩孝", "景甜", "刘亦", "康", "邓紫" },
				{ "宋慧", "章泽", "宋茜", "韩孝", "景甜", "刘亦", "康", "邓紫" },
				{ "宋慧", "章泽", "宋茜", "韩孝", "景甜", "刘亦", "康", "邓紫" },
				{ "宋慧", "章泽", "宋茜", "韩孝", "景甜", "刘亦", "康", "邓紫" },
				{ "宋慧", "章泽", "宋茜", "韩孝", "景甜", "刘亦", "康", "邓紫" } };
		String dir = FileUtil.getRecentChatPath();
		String[][] childPath = {
				{ dir + "songhuiqiao.jpg", dir + "zhangzetian.jpg",
						dir + "songqian.jpg", dir + "hangxiaozhu.jpg",
						dir + "jingtian.jpg", dir + "liuyifei.jpg",
						dir + "kangyikun.jpg", dir + "dengziqi.jpg" },
				{ dir + "songhuiqiao.jpg", dir + "zhangzetian.jpg",
						dir + "songqian.jpg", dir + "hangxiaozhu.jpg",
						dir + "jingtian.jpg", dir + "liuyifei.jpg",
						dir + "kangyikun.jpg", dir + "dengziqi.jpg" },
				{ dir + "songhuiqiao.jpg", dir + "zhangzetian.jpg",
						dir + "songqian.jpg", dir + "hangxiaozhu.jpg",
						dir + "jingtian.jpg", dir + "liuyifei.jpg",
						dir + "kangyikun.jpg", dir + "dengziqi.jpg" },
				{ dir + "songhuiqiao.jpg", dir + "zhangzetian.jpg",
						dir + "songqian.jpg", dir + "hangxiaozhu.jpg",
						dir + "jingtian.jpg", dir + "liuyifei.jpg",
						dir + "kangyikun.jpg", dir + "dengziqi.jpg" },
				{ dir + "songhuiqiao.jpg", dir + "zhangzetian.jpg",
						dir + "songqian.jpg", dir + "hangxiaozhu.jpg",
						dir + "jingtian.jpg", dir + "liuyifei.jpg",
						dir + "kangyikun.jpg", dir + "dengziqi.jpg" },
				{ dir + "songhuiqiao.jpg", dir + "zhangzetian.jpg",
						dir + "songqian.jpg", dir + "hangxiaozhu.jpg",
						dir + "jingtian.jpg", dir + "liuyifei.jpg",
						dir + "kangyikun.jpg", dir + "dengziqi.jpg" },
				{ dir + "songhuiqiao.jpg", dir + "zhangzetian.jpg",
						dir + "songqian.jpg", dir + "hangxiaozhu.jpg",
						dir + "jingtian.jpg", dir + "liuyifei.jpg",
						dir + "kangyikun.jpg", dir + "dengziqi.jpg" }, };

		dbm.clearData();
		for( int i = 0 ; i < groups.length ; i++  )
		{
			
			for( int j = 0 ; j < children[i].length ; j++ )
			{
				dbm.add( 0 , groups[i] ,children[i][j],"", childPath[i][j] );
				
			}
			
			
		}
		*/
		 allFriend.constructTeamInfoFromDb(this);

		mFriendListAdapter.updateListView( allFriend );
	}
	
	// "update_type: " 	1  	update all
	// 					2 	part update 
	public void updateFriendListFromServer( int update_type , JSONArray jason_friendList )
	{
		if( 1 == update_type  || ( allFriend == null) )
		{
			allFriend = new friendTeamDataManager();
		}
		
			
		for( int i = 0 ; i < jason_friendList.length() ; i++ )
		{
			JSONObject obj;
			try {
				obj = (JSONObject)(jason_friendList.get(i));
			
				String teamName = obj.getString("team");
				String memberName = obj.getString("nickname");
				String phoneNumber = obj.getString("mobile");
				String pictureAddress = obj.getString("avatar_url");
				allFriend.addA_FriendMemberData( teamName, memberName, phoneNumber , pictureAddress );
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}				
		allFriend.updateDataToDb(this);
		mFriendListAdapter.updateListView( allFriend );

		
	}
	
	public Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if ( msg.what == UPDATE_VIEW_FROM_REMOT ) {
				//	
				if(PeopleActivity.this.isDestroyed() )
				{
					Log.e( PeopleActivity.this.getClass().getSimpleName() , "PeopleActivity is destroyed!" );
				}
				else
				{
					JSONObject jason_obj = (JSONObject)(msg.obj);
					
					int update_type = 0;
					JSONArray jason_friendList = null;
					JSONArray jason_CircleList = null;
					try {
	
						update_type = jason_obj.getInt("update_type");
						jason_friendList = jason_obj.getJSONArray("friends");
						jason_CircleList = jason_obj.getJSONArray("circle");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					updateFriendListFromServer( update_type, jason_friendList );
				}
			} 
		}
	};





}
