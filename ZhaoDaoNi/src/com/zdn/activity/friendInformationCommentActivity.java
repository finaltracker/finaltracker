package com.zdn.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.zdn.R;
import com.zdn.control.EditTextWithDel;


public class friendInformationCommentActivity extends Activity {
	
	EditTextWithDel	commentView;

	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.friend_information_detail );
		findView();
		init();
	}

	private void findView()
	{
		commentView = (EditTextWithDel) this.findViewById(R.id.newComment );
	}
	
	private void init()
	{
		Intent intent = this.getIntent();
		String oldComment = intent.getStringExtra("comment");
		commentView.setHint(oldComment);
		
	}
	
}
