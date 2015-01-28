package com.zdn.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zdn.R;
import com.zdn.basicStruct.friendMemberData;
import com.zdn.data.dataManager;
import com.zdn.activity.friendInformationCommentActivity;

public class friendInformationDetailActivity extends Activity {
	
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
		super.onCreate(savedInstanceState);
		setContentView(R.layout.friend_information_detail );
		findView();
		init();
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
        		b.putString("oldComment", commentTextView.getText().toString() );  
        		intent.putExtras(b);  
        		friendInformationDetailActivity.this.startActivityForResult(intent, 1 );  

        	}
        	});

		
		groupLineView.setOnClickListener( new View.OnClickListener() {
        	public void onClick(View v) {
        		//start group select View activitys
        		Intent intent = new Intent( friendInformationDetailActivity.this, friendInformationGroupActivity.class );  
        		Bundle b = new Bundle(); 
        		b.putInt( "groupIndex", dataManager.getFrilendList().findAfriendTeam(groupTextView.getText().toString() )  );  
        		intent.putExtras(b);  
        		friendInformationDetailActivity.this.startActivityForResult(intent, 2 );  

        	}
        	});

		deleteFriend.setOnClickListener( new View.OnClickListener() {
        	public void onClick(View v) {
        		//send delete friend request
        		MainControl.deleteA_Friend( fmd );
        		Toast.makeText( friendInformationDetailActivity.this, "ɾ��������Ϣ�Է���" , Toast.LENGTH_SHORT).show();
        		friendInformationDetailActivity.this.finish();
        	}
        	});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		switch( resultCode)
		{
		case (friendInformationCommentActivity.FRIEND_INFORMATION_COMMENT_ACTIVITY):
			String newComment = data.getExtras().getString("newComment");//�õ���Activity �رպ󷵻ص�����
			commentTextView.setText( newComment );
			Log.i(this.getClass().getName(), "user new comment " + newComment );
			
			fmd.basic.setComment( newComment );
			break;
		
		case (friendInformationGroupActivity.FRIEND_INFORMATION_GROUP_ACTIVITY):
			String newGroup = data.getExtras().getString("newGroup");//�õ���Activity �رպ󷵻ص�����
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

	
}
