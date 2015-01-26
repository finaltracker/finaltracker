package com.zdn.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.zdn.R;


public class friendInformationDetailActivity extends Activity {
	
	ImageView deleteFriend;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.friend_information_detail );
	}

	private void findView()
	{
		deleteFriend = (ImageView) this.findViewById( R.id.friend_information_detail_delete_friend );
	}
}
