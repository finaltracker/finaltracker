package com.zdn.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.adn.db.DBManager;
import com.adn.db.DBManager.MemberInfo;
import com.zdn.AsyncTaskBase;
import com.zdn.R;
import com.zdn.sort.ClearEditText;
import com.zdn.util.FileUtil;
import com.zdn.util.ImgUtil;
import com.zdn.util.ImgUtil.OnLoadBitmapListener;
import com.zdn.adapter.FriendListAdapter;
import com.zdn.view.FriendListView;
import com.zdn.view.LoadingView;
import com.zdn.view.PhoneConstactView;

import android.app.ActionBar;
import android.content.Context;
import android.os.Bundle;

import android.support.v4.app.ActionBarDrawerToggle;
import android.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.zdn.adapter.FriendListAdapter.teamData;
import com.zdn.adapter.FriendListAdapter.memberData;


public class PeopleFragment extends Fragment {
	private Context mContext;
	private View mBaseView;
	private FriendListView mIphoneTreeView;
	private ClearEditText mSearchView;
	private  String const_teamName_myFriend = "我的好友";
	private FriendListAdapter mFriendListAdapter;
	
	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private DBManager dbm;
	List<teamData>   allFriend = null;

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mContext = getActivity();
		mBaseView = inflater.inflate(R.layout.fragment_constact, null);
		findView();
		init();
		return mBaseView;
	}

	private void findView() {
		mSearchView=(ClearEditText) mBaseView.findViewById( R.id.ll_constact_serach );
		mIphoneTreeView = (FriendListView) mBaseView.findViewById( R.id.iphone_tree_view );
		
	}

	private void init() {
		dbm = new DBManager( mContext );
		

		mIphoneTreeView.setGroupIndicator(null);//set icon in front of group

		mFriendListAdapter = new FriendListAdapter(mContext, mIphoneTreeView,null);
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
	
	/**
	 * Users of this fragment must call this method to set up the navigation
	 * drawer interactions.
	 * 
	 * @param fragmentId
	 *            The android:id of this fragment in its activity's layout.
	 * @param drawerLayout
	 *            The DrawerLayout containing this fragment's UI.
	 */
	public void setUp(int fragmentId, DrawerLayout drawerLayout) {
		FragmentActivity fa = (FragmentActivity) getActivity();
		mDrawerLayout = drawerLayout;

		// set a custom shadow that overlays the main content when the drawer
		// opens
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);
		// set up the drawer's list view with items and click listener

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);

		// ActionBarDrawerToggle ties together the the proper interactions
		// between the navigation drawer and the action bar app icon.
		mDrawerToggle = new ActionBarDrawerToggle(getActivity(), /* host Activity */
				mDrawerLayout, /* DrawerLayout object */
				R.drawable.ic_drawer, /* nav drawer image to replace 'Up' caret */
				R.string.navigation_drawer_open, /*
												 * "open drawer" description for
												 * accessibility
												 */
				R.string.navigation_drawer_close /*
												 * "close drawer" description for
												 * accessibility
												 */
				) {
					@Override
					public void onDrawerClosed(View drawerView) {
						super.onDrawerClosed(drawerView);
						if (!isAdded()) {
							return;
						}
		
						getActivity().invalidateOptionsMenu(); // calls
																// onPrepareOptionsMenu()
					}
		
					@Override
					public void onDrawerOpened(View drawerView) {
						super.onDrawerOpened(drawerView);
						if (!isAdded()) {
							return;
						}
		
						
						getActivity().invalidateOptionsMenu(); // calls
																// onPrepareOptionsMenu()
					}
				};

		// If the user hasn't 'learned' about the drawer, open it to introduce
		// them to the drawer,
		// per the navigation drawer design guidelines.
				/*
		if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
			mDrawerLayout.openDrawer(mFragmentContainerView);
		}
*/
		// Defer code dependent on restoration of previous instance state.
		mDrawerLayout.post(new Runnable() {
			@Override
			public void run() {
				mDrawerToggle.syncState();
			}
		});

		mDrawerLayout.setDrawerListener(mDrawerToggle);
	}
	private ActionBar getActionBar() {
		return getActivity().getActionBar();
	}

	private List<teamData> constructTeamInfoFromDb()
	{
		List<teamData> updateTeams = new ArrayList<teamData>();
		
		ArrayList<MemberInfo> miList = dbm.searchAllData();
		
		Map< String ,List<memberData> > mapTeamData = new HashMap< String ,List<memberData> >();
		
		for( int i = 0 ; i < miList.size() ; i++ )
		{
			MemberInfo dbMi = miList.get(i);
			
			List<memberData> memberDataList = null;
			memberDataList = mapTeamData.get(dbMi.teamName);
			if( null == memberDataList )
			{
				memberDataList = new ArrayList<memberData>();
				mapTeamData.put(dbMi.teamName, memberDataList);
			}
			memberData md = mFriendListAdapter.new memberData();
			md.memberName = dbMi.memberName;
			md.pictureAddress = dbMi.pictureAddress;
			md.picture = ImgUtil.getInstance().loadBitmapFromCache(md.pictureAddress);
				
			memberDataList.add(md);
				
		}
		
		for (String key : mapTeamData.keySet()) 
		{

			teamData td =  mFriendListAdapter.new teamData();
			updateTeams.add(td);
			td.teamName = key;
			td.member = mapTeamData.get(key);
		}
		
		
		return updateTeams;
	}
	//only for test

	public void addA_FriendRsp( String teamName ,String name , String majiaUrl )
	{
		int i = 0 ;
		
		teamData const_my_friend = null;
		
		for( i = 0 ; i < allFriend.size(); i++ )
		{
			if( allFriend.get(i).teamName.equals(teamName) )
			{
				memberData md = mFriendListAdapter.new memberData();
				md.memberName = name;
				md.pictureAddress = majiaUrl;
				md.picture = ImgUtil.getInstance().loadBitmapFromCache(md.pictureAddress);
					
			
				allFriend.get(i).member.add( md );
				break;
			}
			else if(allFriend.get(i).teamName.equals( const_teamName_myFriend ) )
			{
				const_my_friend = allFriend.get(i);
			}
			
		}
		
		if( i == allFriend.size() )
		{	//not find 
			
			teamData team = null;
			
			if( const_my_friend != null )
			{ // 
				team = const_my_friend;
			}
			else
			{
				team =  mFriendListAdapter.new teamData();
				team.teamName = const_teamName_myFriend;
				allFriend.add( team );
			}
			
			memberData md = null;
			md = mFriendListAdapter.new memberData();
			md.memberName = name;
			md.pictureAddress = majiaUrl;
			md.picture = ImgUtil.getInstance().loadBitmapFromCache(md.pictureAddress);
				
		
			team.member.add( md );
		}
		
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
