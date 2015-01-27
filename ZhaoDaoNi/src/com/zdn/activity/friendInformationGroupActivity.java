package com.zdn.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.zdn.R;
import com.zdn.adapter.friendInformationGroupAdapter;
import com.zdn.control.EditTextWithDel;


public class friendInformationGroupActivity extends Activity {
	
	ListView	groupView;
	friendInformationGroupAdapter fimga;

	static public final int FRIEND_INFORMATION_GROUP_ACTIVITY		= 2;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.friend_information_group );
		findView();
		init();

	}

	private void findView()
	{
		groupView = (ListView) this.findViewById(R.id.groupList );
	}
	

	private void init()
	{
		Intent intent = this.getIntent();
		String group = intent.getStringExtra("group");
		fimga = new friendInformationGroupAdapter( this,group );
		groupView.setAdapter( fimga );
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub


		{
			//send a intent back to main friend information activity
			
			//数据是使用Intent返回
            Intent intent = new Intent();
            //把返回数据存入Intent
            intent.putExtra("newGroup", fimga.getSelectedGroup() );
            //设置返回数据
            this.setResult( FRIEND_INFORMATION_GROUP_ACTIVITY , intent);
            //关闭Activity
            
			this.finish();
			
			
		}
		super.onPause();
	}
	
	
}
