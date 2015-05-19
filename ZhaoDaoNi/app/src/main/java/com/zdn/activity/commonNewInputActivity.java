package com.zdn.activity;

import android.content.Intent;
import android.os.Bundle;

import com.zdn.R;
import com.zdn.view.EditTextWithDel;


public class commonNewInputActivity extends zdnBasicActivity {
	
	EditTextWithDel	commentView;

	static public final int FRIEND_INFORMATION_COMMENT_ACTIVITY		= 1;

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.friend_information_comment );
		findView();
		init();
		super.onCreate(savedInstanceState);
		
	}

	private void findView()
	{
		commentView = (EditTextWithDel) this.findViewById(R.id.newInput);
	}
	
	private void init()
	{
		Intent intent = this.getIntent();
		String oldComment = intent.getStringExtra("comment");
		if( !oldComment.isEmpty() )
		{
			commentView.setHint(oldComment);
		}
		
		
	}



	@Override
	public void onBackPressed() {
		String newComment = commentView.getText().toString();
		
		if( newComment.equals(commentView.getHint()))
		{
			
		}
		else
		{
			//send a intent back to main friend information activity
			
			//数据是使用Intent返回
            Intent intent = new Intent();
            //Bundle extras = new Bundle();
            //extras.putString("newInput", newComment);
            //把返回数据存入Intent
            //intent.putExtras(extras);
            intent.putExtra("newInput", newComment );
            //设置返回数据
            this.setResult( FRIEND_INFORMATION_COMMENT_ACTIVITY , intent);
            //关闭Activity
            
			this.finish();
			
			
		}
		//super.onBackPressed();
	}
	
	
}
