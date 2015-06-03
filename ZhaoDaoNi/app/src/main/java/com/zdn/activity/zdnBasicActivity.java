package com.zdn.activity;

import com.zdn.R;
import com.zdn.basicStruct.networkStatusEvent;
import com.zdn.receiver.NetworkReceiver;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//if (!mNavigationDrawerFragment.isDrawerOpen()) {
		// Only show items in the action bar relevant to this screen
		// if the drawer is not showing. Otherwise, let the drawer
		// decide what to show in the action bar.
		//

		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.main, menu);
		MenuItem it = menu.findItem(R.id.contact_friend);
		it.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		it =menu.findItem(R.id.action_add).setVisible(true);
		it.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		it =menu.findItem(R.id.action_settings).setVisible(true);
		it.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		//	restoreActionBar();


		setTitle(getResources().getString(R.string.titleName));

		return true;
		//}
		//return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.

		FragmentManager fragmentManager = this.getFragmentManager();
		FragmentTransaction ft = fragmentManager.beginTransaction();

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