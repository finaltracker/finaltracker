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
		
		friendMemberData fmd = dataManager.getFrilendList().getMemberData( teamPosition, memberPosition );
		if(fmd != null )
		{
			userNameTextView.setText( fmd.basic.memberName + "-" + fmd.basic.nickName );
			commentTextView.setText(fmd.basic.comment      );
		}
		else
		{
			Log.d(this.getClass().getName() ,  "invalid friend member data in (" + teamPosition + "," + memberPosition + ")");
		}
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
			//update data to data manager
			
		}
		super.onPause();
	}
	
	
}
