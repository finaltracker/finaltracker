package com.zdn.activity;


import com.zdn.AsyncTaskBase;
import com.zdn.R;
import com.zdn.sort.ClearEditText;
import com.zdn.adapter.FriendListAdapter;
import com.zdn.data.dataManager;
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
import android.widget.ExpandableListView;

public class PeopleActivity extends Activity implements ExpandableListView.OnChildClickListener  {
	//private Context this;
	//private View mBaseView;
	static public final int  UPDATE_VIEW_FROM_REMOT		= 1 ;
	private FriendListView mIphoneTreeView;
	private ClearEditText mSearchView;
	private FriendListAdapter mFriendListAdapter;
	static public PeopleActivity me;
	
	
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
		/*
		DBManager dbm = new DBManager( this );
		String[] groups = { "待验证好友","我的好友", "家人",  "S2S73", "S1S24",
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
		dbm.closeDB();
		*/
		

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

		Intent intent = new Intent(this, friendInformationDetailActivity.class);  
		// 创建Bundle对象用来存放数据,Bundle对象可以理解为数据的载体  
		Bundle b = new Bundle();  
		// 调用Bundle对象的putString方法,采用 key-value的形式保存数据  
		b.putInt("teamPosition", groupPosition );  
		b.putInt("memberPosition",childPosition  );  
		// 将数据载体BUndle对象放入Intent对象中.  
		intent.putExtras(b);  
		// 调用startActivityForResult方法  
		// startActivityForResult(intent,requestCode);  
		// intent,数据载体  
		// requestCode 请求的Code,这里一般 大于等于0的整型数据就可以.  
		startActivity(intent);  
		
		return false;
	}





}
