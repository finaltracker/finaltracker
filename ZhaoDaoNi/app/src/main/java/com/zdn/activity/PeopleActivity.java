package com.zdn.activity;


import com.zdn.AsyncTaskBase;
import com.zdn.R;
import com.zdn.sort.ClearEditText;
import com.zdn.adapter.FriendListAdapter;
import com.zdn.basicStruct.SendMessageRspEvent;
import com.zdn.basicStruct.friendMemberData;
import com.zdn.basicStruct.getMessageRspEvent;
import com.zdn.data.dataManager;
import com.zdn.view.FriendListView;
import com.zdn.view.LoadingView;

import de.greenrobot.event.EventBus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;

public class PeopleActivity extends zdnBasicActivity implements ExpandableListView.OnChildClickListener  {
	//private Context this;
	//private View mBaseView;
	static public final int  UPDATE_VIEW_FROM_REMOT		= 1 ;
	private FriendListView mIphoneTreeView;
	private ClearEditText mSearchView;
	private FriendListAdapter mFriendListAdapter;
	static public PeopleActivity me;

	static public PeopleActivity getInstance() { return me; }
	
	public PeopleActivity()
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
		setContentView(R.layout.fragment_constact);
		//mBaseView = inflate(R.layout.fragment_constact, null);
		findView();
		init();
		
		//ImageView homeIcon = (ImageView)findViewById(android.R.id.);
		//TextView actionTitle = (TextView)findViewById(com.android.internal.R.id.action_bar_title);
		//homeIcon.setImageDrawable( getResources().getDrawable(R.drawable.add));
		super.onCreate(savedInstanceState);
		
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
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

        super.onEvent( event );
	}
	
	private void findView() {
		mSearchView=(ClearEditText) findViewById( R.id.ll_constact_serach );
		mIphoneTreeView = (FriendListView) findViewById( R.id.iphone_tree_view );
		
	}

	private void init() {
		
		mIphoneTreeView.setOnChildClickListener( this );

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
		
		mFriendListAdapter.updateListView( dataManager.getFrilendList() );
	}
	
	public void update()
	{
		if( !isDestroyed() )
		{
			mFriendListAdapter.updateListView( dataManager.getFrilendList()  );
		}
	}
	// "update_type: " 	1  	update all
	// 					2 	part update 
	
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
					update();
				}
			} 
		}
	};


	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {

		//Intent intent = new Intent(this, friendInformationDetailActivity.class);  
		
		//for test
		
		
		Intent intent = new Intent(this, chatActivity.class);  
		
		// 创建Bundle对象用来存放数据,Bundle对象可以理解为数据的载体  
		Bundle b = new Bundle();  
		
		b.putString("targetTo", dataManager.getFrilendList().getMemberData(groupPosition, childPosition).basic.getPhoneNumber() );
		b.putInt("teamPosition", groupPosition );
		b.putInt("memberPosition", childPosition );
		
		intent.putExtras(b);  
		
		startActivity(intent);  
		
		
		return false;
	}

	 @Override
	    public boolean onPrepareOptionsMenu(Menu menu){
	     
	     super.onPrepareOptionsMenu(menu);
	     Log.d(this.getClass().getName(), "R.id.home = " + android.R.id.home );
	     MenuItem menuItem=menu.findItem( android.R.id.home );
	     if( menuItem !=null )
	     {
	    	 menuItem.setIcon(R.drawable.add);
	     }
	     
	     //只有当在添加的状态下（addingNew=true）或者ListView被selected的情况下REMOVE_TODO菜单项才可见
	    // removeItem.setVisible(addingNew||idx>-1);
	     
	  	return true;
	     
	    }
	 
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		switch(id)
		{
		case R.id.action_settings:
			
			break;

		case R.id.action_add:
			startActivity( new Intent("com.zdn.activity.AddFriendActivity.ACTION") );
			break;
			
		case R.id.contact_friend:
			
			startActivity( new Intent("com.zdn.activity.PeopleActivity.ACTION") );
			
			break;
		default:
			
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	

}
