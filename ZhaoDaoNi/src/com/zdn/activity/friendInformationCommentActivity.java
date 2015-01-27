package com.zdn.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.zdn.R;
import com.zdn.control.EditTextWithDel;


public class friendInformationCommentActivity extends Activity {
	
	EditTextWithDel	commentView;

	static public final int FRIEND_INFORMATION_COMMENT_ACTIVITY		= 1;
	
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

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		String newComment = commentView.getText().toString();
		
		if( newComment.equals(commentView.getHint()))
		{
			
		}
		else
		{
			//send a intent back to main friend information activity
			
			//数据是使用Intent返回
            Intent intent = new Intent();
            //把返回数据存入Intent
            intent.putExtra("newComment", newComment );
            //设置返回数据
            this.setResult( FRIEND_INFORMATION_COMMENT_ACTIVITY , intent);
            //关闭Activity
            
			this.finish();
			
			
		}
		super.onPause();
	}
	
	
}
