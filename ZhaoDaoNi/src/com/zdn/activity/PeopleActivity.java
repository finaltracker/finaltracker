package com.zdn.activity;

import java.util.ArrayList;

import com.adn.db.DBManager;
import com.adn.db.DBManager.MemberInfo;
import com.zdn.AsyncTaskBase;
import com.zdn.R;
import com.zdn.sort.ClearEditText;
import com.zdn.util.FileUtil;
import com.zdn.adapter.FriendListAdapter;
import com.zdn.basicStruct.friendTeamDataManager;
import com.zdn.view.FriendListView;
import com.zdn.view.LoadingView;

import android.app.Activity;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

public class PeopleActivity extends Activity {
	//private Context this;
	//private View mBaseView;
	private FriendListView mIphoneTreeView;
	private ClearEditText mSearchView;
	private FriendListAdapter mFriendListAdapter;
	
	private DBManager dbm;
	friendTeamDataManager   allFriend = null;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_constact);
		//mBaseView = inflate(R.layout.fragment_constact, null);
		findView();
		init();
	}
	

	private void findView() {
		mSearchView=(ClearEditText) findViewById( R.id.ll_constact_serach );
		mIphoneTreeView = (FriendListView) findViewById( R.id.iphone_tree_view );
		
	}

	private void init() {
		dbm = new DBManager( this );
		

		mIphoneTreeView.setGroupIndicator(null);//set icon in front of group

		mFriendListAdapter = new FriendListAdapter(this, mIphoneTreeView,null);
		updateAdapter();
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

	private friendTeamDataManager constructTeamInfoFromDb()
	{
		friendTeamDataManager updateTeams = new friendTeamDataManager();
		
		ArrayList<MemberInfo> miList = dbm.searchAllData();
		
		
		for( int i = 0 ; i < miList.size() ; i++ )
		{
			MemberInfo dbMi = miList.get(i);
			
			updateTeams.addA_FriendMemberData(dbMi.teamName, dbMi.memberName, "", dbMi.pictureAddress );
				
		}
		
		
		return updateTeams;
	}
	//only for test

	public void addA_FriendRsp( String teamName ,String name , String majiaUrl )
	{
		
		allFriend.addA_FriendMemberData(teamName, name, "", majiaUrl);
		dbm.add( 0 , teamName ,name, majiaUrl );
		mFriendListAdapter.updateListView( allFriend );
	}
	
	public void updateAdapter()
	{
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
				dbm.add( 0 , groups[i] ,children[i][j], childPath[i][j] );
				
			}
			
			
		}
		
		allFriend =  constructTeamInfoFromDb();

		mFriendListAdapter.updateListView( allFriend );
	}
	
}
