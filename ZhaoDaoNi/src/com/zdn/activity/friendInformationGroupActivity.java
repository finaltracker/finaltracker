package com.zdn.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.zdn.R;
import com.zdn.adapter.friendInformationGroupAdapter;
import com.zdn.view.EditTextWithDel;


public class friendInformationGroupActivity extends zdnBasicActivity {
	
	ListView	groupView;
	friendInformationGroupAdapter fimga;

	static public final int FRIEND_INFORMATION_GROUP_ACTIVITY		= 2;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.friend_information_group );
		findView();
		init();
		super.onCreate(savedInstanceState);
		
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
	public void onBackPressed() {

		//send a intent back to main friend information activity
		
		//������ʹ��Intent����
        Intent intent = new Intent();
        //�ѷ������ݴ���Intent
        intent.putExtra("newGroup", fimga.getSelectedGroup() );
        //���÷�������
        this.setResult( FRIEND_INFORMATION_GROUP_ACTIVITY , intent);
        //�ر�Activity
        
		this.finish();
	}
	
	
}
