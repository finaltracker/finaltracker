package com.zdn.fragment;

import java.util.HashMap;
import java.util.List;

import com.zdn.AsyncTaskBase;
import com.zdn.R;
import com.zdn.test.TestData;
import com.zdn.adapter.ExpAdapter;
import com.zdn.bean.RecentChat;
import com.zdn.view.IphoneTreeView;
import com.zdn.view.LoadingView;
import com.zdn.view.PhoneConstactView;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class ConstactFragment extends Fragment {
	private Context mContext;
	private View mBaseView;
	private IphoneTreeView mIphoneTreeView;
	private PhoneConstactView mPhoneContract;
	private View mSearchView;
	private ExpAdapter mExpAdapter;
	private RelativeLayout constacts;
	private RelativeLayout mFriend;
	private HashMap<String, List<RecentChat>> maps = new HashMap<String, List<RecentChat>>();

	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private View mFragmentContainerView;
	
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
		mSearchView=mBaseView.findViewById( R.id.search );
		mIphoneTreeView = (IphoneTreeView) mBaseView.findViewById( R.id.iphone_tree_view );
		mFriend =(RelativeLayout) mBaseView.findViewById( R.id.rl_friend);
		constacts=(RelativeLayout) mBaseView.findViewById( R.id.rl_tonxunru );
		mPhoneContract = (PhoneConstactView) mBaseView.findViewById( R.id.iphone_phoneContract );
		
	}

	private void init() {
		mIphoneTreeView.setHeaderView(LayoutInflater.from(mContext).inflate(
				R.layout.fragment_constact_head_view, mIphoneTreeView, false));
		mIphoneTreeView.setGroupIndicator(null);
		mExpAdapter = new ExpAdapter(mContext, maps, mIphoneTreeView,mSearchView);
		mIphoneTreeView.setAdapter(mExpAdapter);
		
		mFriend.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mIphoneTreeView.setVisibility(View.VISIBLE);
				mPhoneContract.setVisibility(View.GONE);
			}
			
			});
		constacts.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mIphoneTreeView.setVisibility(View.GONE);
				mPhoneContract.setVisibility(View.VISIBLE);
				
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
			maps.put("我的同学", TestData.getRecentChats());
			maps.put("我的朋友", TestData.getRecentChats());
			maps.put("家人", TestData.getRecentChats());
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
		mFragmentContainerView = getActivity().findViewById(fragmentId);
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

}
