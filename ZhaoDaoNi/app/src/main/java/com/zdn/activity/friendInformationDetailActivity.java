package com.zdn.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zdn.R;
import com.zdn.basicStruct.friendMemberData;
import com.zdn.data.dataManager;
import com.zdn.activity.friendInformationCommentActivity;

public class friendInformationDetailActivity extends zdnBasicActivity {
	
	ImageView 	deleteFriend;
	View		commentLineView;
	View		groupLineView;
	TextView	userNameTextView;
	TextView	commentTextView;
	TextView    groupTextView;
	friendMemberData fmd ;
	static friendInformationDetailActivity  instance = null;
	
	
	public friendInformationDetailActivity()
	{
		instance = this;
		fmd = null;
	}
	
	@Override
	protected void finalize() throws Throwable {
		instance = null;
		super.finalize();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.friend_information_detail );
		findView();
		init();
		super.onCreate(savedInstanceState);
		
	}

	private void findView()
	{
		commentLineView = this.findViewById(R.id.commentLine);
		userNameTextView = (TextView) this.findViewById(R.id.userName );
		groupTextView = (TextView) this.findViewById( R.id.group );
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
		
		fmd = dataManager.getFrilendList().getMemberData( teamPosition, memberPosition );
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
        		Intent intent = new Intent( friendInformationDetailActivity.this, friendInformationCommentActivity.class );  
        		Bundle b = new Bundle();  
        		b.putString("comment", commentTextView.getText().toString() );  
        		intent.putExtras(b);  
        		friendInformationDetailActivity.this.startActivityForResult(intent, 1 );  

        	}
        	});

		
		groupLineView.setOnClickListener( new View.OnClickListener() {
        	public void onClick(View v) {
        		//start group select View activitys
        		Intent intent = new Intent( friendInformationDetailActivity.this, friendInformationGroupActivity.class );  
        		Bundle b = new Bundle(); 
        		b.putString("group", groupTextView.getText().toString()  );  
        		intent.putExtras(b);  
        		friendInformationDetailActivity.this.startActivityForResult(intent, 2 );  

        	}
        	});

		deleteFriend.setOnClickListener( new View.OnClickListener() {
        	public void onClick(View v) {
        		//send delete friend request
        		dataManager.getFrilendList().removeA_Friend( fmd.basic.getTeamName(), fmd.basic.getPhoneNumber() );
				Toast.makeText( friendInformationDetailActivity.this, "删除好友消息以发出" , Toast.LENGTH_SHORT).show();
        		friendInformationDetailActivity.this.finish();
        	}
        	});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		switch( resultCode)
		{
		case (friendInformationCommentActivity.FRIEND_INFORMATION_COMMENT_ACTIVITY):
			String newComment = data.getExtras().getString("newComment");//得到新Activity 关闭后返回的数据
			commentTextView.setText( newComment );
			Log.i(this.getClass().getName(), "user new comment " + newComment );
			
			fmd.basic.setComment( newComment );
			break;
		
		case (friendInformationGroupActivity.FRIEND_INFORMATION_GROUP_ACTIVITY):
			String newGroup = data.getExtras().getString("newGroup");//得到新Activity 关闭后返回的数据
			groupTextView.setText( newGroup );
			Log.i(this.getClass().getName(), "user new group " + newGroup );
			
			fmd.basic.setTeamName( newGroup );
			break;
			
		default:
			Log.i(this.getClass().getName(), "invalid resultCode" + resultCode );
			break;
		
			
		}
		
		
        
        
		super.onActivityResult(requestCode, resultCode, data);
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
