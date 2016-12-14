package com.zdn.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.LinearLayout;

import com.zdn.R;
import com.zdn.basicStruct.networkStatusEvent;
import com.zdn.com.headerCtrl;

import de.greenrobot.event.EventBus;

public class zdnBasicActivity extends FragmentActivity implements headerCtrl.menuStateChange {

	private LinearLayout zdnHeaderLayout = null;
	headerCtrl hc = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {


		EventBus.getDefault().register(this);


		super.onCreate(savedInstanceState);

		zdnHeaderLayout = (LinearLayout) findViewById(R.id.header);
		if( zdnHeaderLayout != null )
		{
			hc = new headerCtrl( zdnHeaderLayout , this );
		}

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

	@Override
	public void onMenuClick(int menuId) {
		switch( menuId )
		{

			case R.id.back_button:
				onBackPressed();
				break;

			default:
				break;
		}
	}

	@Override
	public void menuFragmentClick() {

	}
}