package com.zdn.fragment;


import com.zdn.AsyncTaskBase;
import com.zdn.R;
import com.zdn.activity.chatActivity;
import com.zdn.basicStruct.commonEvent;
import com.zdn.com.headerCtrl;
import com.zdn.sort.ClearEditText;
import com.zdn.adapter.FriendListAdapter;
import com.zdn.basicStruct.SendMessageRspEvent;
import com.zdn.basicStruct.getMessageRspEvent;
import com.zdn.data.dataManager;
import com.zdn.view.FriendListView;
import com.zdn.view.LoadingView;

import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

public class PeopleFragment extends mainActivityFragmentBase implements ExpandableListView.OnChildClickListener  {
	//private Context this;
	//private View mBaseView;
	static public final int  UPDATE_VIEW_FROM_REMOT		= 1 ;
	private FriendListView mIphoneTreeView;
	private ClearEditText mSearchView;
	private FriendListAdapter mFriendListAdapter;
	View rootView = null;


	public PeopleFragment(  headerCtrl.menuStateChange msc  )
	{
		super(msc , mainActivityFragmentBase.FRIEND_LIST_FRAGMENT );
	}
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {

		rootView =  inflater.inflate( R.layout.fragment_constact, container, false);
		findView();
		init();

		super.onCreateView(inflater, container, savedInstanceState);

		return rootView;

	}


	
	public void onEvent(Object event)
	{
		
		if( event instanceof SendMessageRspEvent )
		{
			mFriendListAdapter.notifyDataSetChanged();
		}
		else if( event instanceof getMessageRspEvent )
		{
			mFriendListAdapter.notifyDataSetChanged();
		}
		else if( event instanceof commonEvent )
		{
			commonEvent ce = (commonEvent) event;
			if( ce.getCommonType() == commonEvent.UPDATE_FRIEND_LIST_VIEW_FROM_REMOT )
			{
				update();
			}
		}

        super.onEvent( event );
	}
	
	private void findView() {
		//mSearchView=(ClearEditText) rootView.findViewById(R.id.ll_constact_serach);
		mIphoneTreeView = (FriendListView) rootView.findViewById(R.id.iphone_tree_view);
		initCommonView(rootView);

		View navigationButton = rootView.findViewById(R.id.navigationButton );
		navigationButton.setVisibility(View.INVISIBLE);


		View friendList = rootView.findViewById(R.id.friendList );
		friendList.setVisibility(View.INVISIBLE);

		//rootView.getBackground().setAlpha( 200 );//0~255透


	}

	private void init() {

		mIphoneTreeView.setOnChildClickListener( this );

		mIphoneTreeView.setGroupIndicator(null);//set icon in front of group

		mFriendListAdapter = new FriendListAdapter(this.getActivity(), mIphoneTreeView,null);
		updateAdapterInit();
		mIphoneTreeView.setAdapter(mFriendListAdapter);
/*
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
		*/
		
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
		
		mFriendListAdapter.updateListView( dataManager.getFrilendList() );
	}
	
	public void update()
	{
		mFriendListAdapter.updateListView( dataManager.getFrilendList()  );

	}
	// "update_type: " 	1  	update all
	// 					2 	part update 


	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {

		//Intent intent = new Intent(this, friendInformationDetailActivity.class);  
		
		//for test
		
		
		Intent intent = new Intent(this.getActivity(), chatActivity.class);
		
		// 创建Bundle对象用来存放数据,Bundle对象可以理解为数据的载体  
		Bundle b = new Bundle();  
		
		b.putString("targetTo", dataManager.getFrilendList().getMemberData(groupPosition, childPosition).basic.getPhoneNumber() );
		b.putInt("teamPosition", groupPosition );
		b.putInt("memberPosition", childPosition );
		
		intent.putExtras(b);  
		
		startActivity(intent);  
		
		
		return false;
	}

	

}
