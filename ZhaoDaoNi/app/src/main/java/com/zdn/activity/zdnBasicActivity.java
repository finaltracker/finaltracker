package com.zdn.activity;

import com.zdn.R;
import com.zdn.basicStruct.networkStatusEvent;
import com.zdn.receiver.NetworkReceiver;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import de.greenrobot.event.EventBus;

public class zdnBasicActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		ImageView homeIcon = (ImageView) findViewById(android.R.id.home);

		if (null != homeIcon) {
			homeIcon.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					zdnBasicActivity.this.onBackPressed();
				}

			});
			homeIcon.setImageDrawable(getResources().getDrawable(R.drawable.back_off));
		}

		EventBus.getDefault().register(this);


		super.onCreate(savedInstanceState);

	}

	@Override
	protected void onDestroy() {

		EventBus.getDefault().unregister(this);
		super.onDestroy();
	}


	public void onEvent(Object event) {

		if( event instanceof networkStatusEvent)
		{
			networkStatusEvent e = (networkStatusEvent)event;

			if( (false == e.getwifiConnect()) &&  ( false == e.getGprsConnect() ))
			{

			}
				//Toast.makeText(this,"无网络连接",Toast.LENGTH_SHORT).show();
			}


	}
}