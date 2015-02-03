package com.zdn.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.zdn.R;
import com.zdn.view.EditTextWithDel;


public class friendInformationCommentActivity extends Activity {
	
	EditTextWithDel	commentView;

	static public final int FRIEND_INFORMATION_COMMENT_ACTIVITY		= 1;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.friend_information_comment );
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
			
			//������ʹ��Intent����
            Intent intent = new Intent();
            //Bundle extras = new Bundle();
            //extras.putString("newComment", newComment);
            //�ѷ������ݴ���Intent
            //intent.putExtras(extras);
            intent.putExtra("newComment", newComment );
            //���÷�������
            this.setResult( FRIEND_INFORMATION_COMMENT_ACTIVITY , intent);
            //�ر�Activity
            
			this.finish();
			
			
		}
		//super.onBackPressed();
	}
	
	
}
