package com.zdn.activity;

import com.zdn.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class zdnBasicActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		ImageView homeIcon = (ImageView)findViewById(android.R.id.home);
		
		if( null != homeIcon )
		{
			homeIcon.setOnClickListener( new View.OnClickListener() {
	
				@Override
				public void onClick(View v) {
					zdnBasicActivity.this.onBackPressed();
				}
				
			});
			homeIcon.setImageDrawable( getResources().getDrawable(R.drawable.back_off));
		}
		
		super.onCreate(savedInstanceState);
	}

}
