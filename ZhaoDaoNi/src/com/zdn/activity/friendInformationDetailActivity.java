package com.zdn.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zdn.R;
import com.zdn.basicStruct.friendMemberData;
import com.zdn.data.dataManager;


public class friendInformationDetailActivity extends Activity {
	
	ImageView 	deleteFriend;
	View		commentLineView;
	View		groupLineView;
	TextView	userNameTextView;
	TextView	commentTextView;
	static friendInformationDetailActivity  instance = null;
	
	
	public friendInformationDetailActivity()
	{
		instance = this;
	}
	
	@Override
	protected void finalize() throws Throwable {
		instance = null;
		super.finalize();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.friend_information_detail );
		findView();
		init();
	}

	private void findView()
	{
		commentLineView = this.findViewById(R.id.commentLine);
		userNameTextView = (TextView) this.findViewById(R.id.userName );
		groupLineView = this.findViewById(R.id.groupLine);
		commentTextView = (TextView) this.findViewById(R.id.comment );
		
		deleteFriend = (ImageView) this.findViewById( R.id.friend_information_detail_delete_friend );
	}
	
	//update when some data has been updated
	public void update()
	{
		if( !isDestroyed() )
		{
			init();
		}
	}
	private void init()
	{
		Intent intent = this.getIntent();
		int teamPosition = intent.getIntExtra("teamPosition",0);
		int memberPosition = intent.getIntExtra("memberPosition", 0);
		
		friendMemberData fmd = dataManager.getFrilendList().getMemberData( teamPosition, memberPosition );
		if(fmd != null )
		{
			userNameTextView.setText( fmd.basic.getMemberName() + "-" + fmd.basic.getNickName());
			commentTextView.setText(fmd.basic.getComment() );
		}
		else
		{
			Log.d(this.getClass().getName() ,  "invalid friend member data in (" + teamPosition + "," + memberPosition + ")");
		}
		commentLineView.setOnClickListener( new View.OnClickListener() {
        	public void onClick(View v) {
        		//start comment edit activity
        	}
        	});

		
		groupLineView.setOnClickListener( new View.OnClickListener() {
        	public void onClick(View v) {
        		//start group select View activitys
        	}
        	});

		deleteFriend.setOnClickListener( new View.OnClickListener() {
        	public void onClick(View v) {
        		//send delete friend request
        		
        	}
        	});
	}

	
}
